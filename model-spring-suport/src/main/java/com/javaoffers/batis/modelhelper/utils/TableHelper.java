package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.anno.ColName;
import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import com.javaoffers.batis.modelhelper.exception.FindColException;
import com.javaoffers.batis.modelhelper.fun.ConstructorFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.sql.DataSource;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 表信息辅助类
 * @Auther: create by cmj on 2022/5/2 02:05
 */
public class TableHelper {

    /**
     * 数据库
     */
    private static DataSource dataSource;

    private static Map<Class, TableInfo> tableInfoMap = new ConcurrentHashMap<>();

    private static Map<String, Class> modelClass = new ConcurrentHashMap<>();

    public TableHelper(DataSource dataSource) {
        TableHelper.dataSource = dataSource;
    }

    /**
     * 获取 Model 对应的全部字段
     * @param modelClss
     * @return
     */
    public static List<String> getColAll(Class<?> modelClss){
        String name = modelClss.getName();
        String implClass = name.replaceAll("\\.","/");
        parseTableInfo(implClass);
        List<String> colAll = new LinkedList<>();
        TableInfo tableInfo = tableInfoMap.get(modelClss);
        String tableName = tableInfo.getTableName();
        tableInfo.getColNameOfModel().forEach((k,v)->{
            colAll.add(tableName+"."+v+" as "+k);
        });
        return colAll;
    }

    public static List<Pair<String, String>> getColAllAndAliasNameOnly(Class<?> modelClss){
        String name = modelClss.getName();
        String implClass = name.replaceAll("\\.","/");
        parseTableInfo(implClass);
        List<Pair<String, String>> colAll = new LinkedList<>();
        TableInfo tableInfo = tableInfoMap.get(modelClss);
        String tableName = tableInfo.getTableName();
        tableInfo.getColNameOfModel().forEach((colName,fieldName)->{
            colAll.add(Pair.of(colName,fieldName));
        });
        return colAll;
    }

    public static Map<String, Field> getColAllAndFieldOnly(Class<?> modelClss){
        String name = modelClss.getName();
        String implClass = name.replaceAll("\\.","/");
        parseTableInfo(implClass);
        List<Pair<String, String>> colAll = new LinkedList<>();
        TableInfo tableInfo = tableInfoMap.get(modelClss);
        Map<String, Field> colNameOfModelField = tableInfo.getColNameOfModelField();
        return colNameOfModelField;
    }

    public static String getColName(GetterFun myFun){
        String methodName = StringUtils.EMPTY;
        String colName = StringUtils.EMPTY;
        try {
            // 直接调用writeReplace
            Method writeReplace = myFun.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object sl = writeReplace.invoke(myFun);
            SerializedLambda serializedLambda = (SerializedLambda) sl;
            methodName = serializedLambda.getImplMethodName();
            String implClass = serializedLambda.getImplClass();
            parseTableInfo(implClass);
            TableInfo tableInfo = tableInfoMap.get(modelClass.get(implClass));
            Map<String, String> fieldNameOfGetter = tableInfo.getMethodNameMappingFieldNameOfGetter();
            fieldNameOfGetter.computeIfAbsent(methodName, k->{
                k = k.startsWith("get")?k.substring(3): k.startsWith("is")?k.substring(2):k;
                k = k.substring(0,1).toLowerCase()+k.substring(1);
                return k;
            });
            String fieldName = fieldNameOfGetter.get(methodName);
            colName = tableInfo.getColNameOfModel().get(fieldName);
            colName = tableInfo.getTableName()+"."+colName+" as "+ fieldName;
            return colName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return colName;
    }

    public static String getColNameNotAs(GetterFun myFun){
        String methodName = StringUtils.EMPTY;
        String colName = StringUtils.EMPTY;
        try {
            // 直接调用writeReplace
            Method writeReplace = myFun.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object sl = writeReplace.invoke(myFun);
            SerializedLambda serializedLambda = (SerializedLambda) sl;
            methodName = serializedLambda.getImplMethodName();
            String implClass = serializedLambda.getImplClass();
            parseTableInfo(implClass);
            TableInfo tableInfo = tableInfoMap.get(modelClass.get(implClass));
            Map<String, String> fieldNameOfGetter = tableInfo.getMethodNameMappingFieldNameOfGetter();
            fieldNameOfGetter.computeIfAbsent(methodName, k->{
                k = k.startsWith("get")?k.substring(3): k.startsWith("is")?k.substring(2):k;
                k = k.substring(0,1).toLowerCase()+k.substring(1);
                return k;
            });
            String fieldName = fieldNameOfGetter.get(methodName);
            colName = tableInfo.getColNameOfModel().get(fieldName);
            colName = tableInfo.getTableName()+"."+colName;
            return colName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return colName;
    }


    public static Pair<String,String> getColNameAndAliasName(GetterFun myFun){
        String methodName = StringUtils.EMPTY;
        try {
            // 直接调用writeReplace
            Method writeReplace = myFun.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object sl = writeReplace.invoke(myFun);
            SerializedLambda serializedLambda = (SerializedLambda) sl;
            methodName = serializedLambda.getImplMethodName();
            String implClass = serializedLambda.getImplClass();
            parseTableInfo(implClass);
            TableInfo tableInfo = tableInfoMap.get(modelClass.get(implClass));
            Map<String, String> colNameOfGetter = tableInfo.getMethodNameMappingFieldNameOfGetter();
            colNameOfGetter.computeIfAbsent(methodName, k->{
                k = k.startsWith("get")?k.substring(3): k.startsWith("is")?k.substring(2):k;
                k = k.substring(0,1).toLowerCase()+k.substring(1);
                return k;
            });
            String fieldName = colNameOfGetter.get(methodName);;
            String colName =  tableInfo.getTableName()+"."+tableInfo.getColNameOfModel().get(fieldName);
            return Pair.of(colName, fieldName);
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new FindColException("解析sql字段出错 ： "+myFun.toString());
    }

    public static String getColNameOnly(GetterFun myFun){
        String methodName = StringUtils.EMPTY;
        try {
            // 直接调用writeReplace
            Method writeReplace = myFun.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object sl = writeReplace.invoke(myFun);
            SerializedLambda serializedLambda = (SerializedLambda) sl;
            methodName = serializedLambda.getImplMethodName();
            String implClass = serializedLambda.getImplClass();
            parseTableInfo(implClass);
            TableInfo tableInfo = tableInfoMap.get(modelClass.get(implClass));
            Map<String, String> colNameOfGetter = tableInfo.getMethodNameMappingFieldNameOfGetter();
            colNameOfGetter.computeIfAbsent(methodName, k->{
                k = k.startsWith("get")?k.substring(3): k.startsWith("is")?k.substring(2):k;
                k = k.substring(0,1).toLowerCase()+k.substring(1);
                return k;
            });
            String fieldName = colNameOfGetter.get(methodName);;
            String colName =  tableInfo.getColNameOfModel().get(fieldName);
            return colName;
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new FindColException("解析sql字段出错 ： "+myFun.toString());
    }

    /**
     * 解析表信息
     * @param implClass
     */
    private static void parseTableInfo(String implClass) {
        modelClass.computeIfAbsent(implClass, k->{
            try {
                Class<?> modelClazz = Class.forName(implClass.replaceAll("/","\\."));
                //解析model class
                parseModelClass(modelClazz);
                return modelClazz;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * 解析model
     * @param modelClazz
     */
    private static void parseModelClass(Class<?> modelClazz) {
        BaseModel table = modelClazz.getDeclaredAnnotation(BaseModel.class);
        String tableName = table.value();
        if(StringUtils.isBlank(tableName)){
            String simpleName = modelClazz.getSimpleName();
            tableName = conLine(simpleName);
        }
        Connection connection = null;
        try {
            TableInfo tableInfo = new TableInfo(tableName);
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tableResultSet = metaData.getTables(connection.getCatalog(),connection.getSchema(),tableName,null);
            while (tableResultSet.next()) {
                // 获取表字段结构
                ResultSet columnResultSet = metaData.getColumns(dataSource.getConnection().getCatalog(), "", tableName, "%");
                while (columnResultSet.next()) {
                    // 字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    // 数据类型
                    String columnType = columnResultSet.getString("TYPE_NAME");
                    ColumnInfo columnInfo = new ColumnInfo(columnName, columnType);
                    tableInfo.getColumnInfos().add(columnInfo);
                    tableInfo.getColNames().put(columnName,columnInfo);
                }
            }
            tableInfoMap.put(modelClazz,tableInfo);

            Field[] colFs= modelClazz.getDeclaredFields();
            for(Field colF : colFs){
                colF.setAccessible(true);
                String colName = colF.getName();
                BaseUnique baseUnique = colF.getDeclaredAnnotation(BaseUnique.class);
                if(baseUnique != null ){
                    if(StringUtils.isNotBlank(baseUnique.value())){
                        colName = baseUnique.value();
                    }
                }
                ColName colNameAnno = colF.getDeclaredAnnotation(ColName.class);
                if(colNameAnno != null && StringUtils.isNotBlank(colNameAnno.value())){
                    colName = colNameAnno.value();
                }
                colName = conLine(colName);
                String fName = colF.getName();
                //说明存在与表中的字段名称对应
                if(tableInfo.getColNames().get(colName) != null){
                    tableInfo.getColNameOfModel().put(fName, colName);
                    tableInfo.getColNameOfModelField().put(colName, colF);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 转下划线
     * @param info
     * @return
     */
    private static String conLine(String info) {

        String[] split = info.split("");
        StringBuilder builder = new StringBuilder();
        for(String s : split){
            if(StringUtils.isAllUpperCase(s)){
                s = s.toLowerCase();
               if(builder.length()!=0){
                   s="_"+s;
               }
            }
            builder.append(s);
        }
        info = builder.toString();
        return info;
    }

    /**
     * 解析 构造函数 所对应的Class
     * @param constructorFun
     * @param <M2>
     * @return
     */
    public static <M2> Class<M2> getClassFromConstructorFun(ConstructorFun<M2> constructorFun) {
        // 直接调用writeReplace
        String implClass = StringUtils.EMPTY;
        try {
            Method method = constructorFun.getClass().getDeclaredMethods()[0];
            method.setAccessible(true);
            Object sl = method.invoke(constructorFun);
            String lamdaName = sl.getClass().getName();
            implClass = lamdaName.replaceAll("\\.","/");
            parseTableInfo(implClass);

        }catch (Exception e){
            e.printStackTrace();
        }
        return modelClass.get(implClass);
    }

    /**
     * 更加Class 获取 tableName
     * @param m2c
     * @param <M2>
     * @return
     */
    public static <M2> String getTableName(Class<M2> m2c) {
        TableInfo tableInfo = tableInfoMap.get(m2c);
        if(tableInfo == null){
            synchronized (TableHelper.class){
                tableInfo = tableInfoMap.get(m2c);
                if(tableInfo == null){
                    parseModelClass(m2c);
                    tableInfo = tableInfoMap.get(m2c);
                }

            }
        }
        return tableInfo.getTableName();
    }
}
