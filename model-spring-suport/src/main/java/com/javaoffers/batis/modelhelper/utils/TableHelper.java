package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.anno.AliasName;
import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import com.javaoffers.batis.modelhelper.fun.ConstructorFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import org.apache.commons.lang3.StringUtils;
import javax.sql.DataSource;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Arrays;
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

    public static String getColName(GetterFun myFun){
        String colName = StringUtils.EMPTY;
        try {
            // 直接调用writeReplace
            Method writeReplace = myFun.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object sl = writeReplace.invoke(myFun);
            SerializedLambda serializedLambda = (SerializedLambda) sl;
            colName = serializedLambda.getImplMethodName();
            String implClass = serializedLambda.getImplClass();
            parseTableInfo(implClass);
            TableInfo tableInfo = tableInfoMap.get(modelClass.get(implClass));
            Map<String, String> colNameOfGetter = tableInfo.getColNameOfGetter();
            colNameOfGetter.computeIfAbsent(colName, k->{
                k = k.startsWith("get")?k.substring(3): k.startsWith("is")?k.substring(2):k;
                k = k.substring(0,1).toLowerCase()+k.substring(1);
                k = tableInfo.getColNameOfModel().get(k);
                return k;
            });
            colName = colNameOfGetter.get(colName);
            colName = tableInfo.getTableName()+"."+colName+" as "+tableInfo.getCloNameAsAlias().get(colName);
            return colName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return colName;
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
        try {
            TableInfo tableInfo = new TableInfo(tableName);
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            ResultSet tableResultSet = metaData.getTables(null,null,tableName,null);
            while (tableResultSet.next()) {
                // 获取表字段结构
                ResultSet columnResultSet = metaData.getColumns(null, "%", tableName, "%");
                while (columnResultSet.next()) {
                    // 字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    // 数据类型
                    String columnType = columnResultSet.getString("TYPE_NAME");
                    ColumnInfo columnInfo = new ColumnInfo(columnName, columnType);
                    tableInfo.getColumnInfos().add(columnInfo);
                    tableInfo.getColName().put(columnName,columnInfo);
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
                }else{
                    AliasName aliasName = colF.getDeclaredAnnotation(AliasName.class);
                    if(aliasName != null && StringUtils.isNotBlank(aliasName.value())){
                        colName = aliasName.value();
                    }
                }
                colName = conLine(colName);
                String fName = colF.getName();
                tableInfo.getColNameOfModel().put(fName, colName);
                tableInfo.getCloNameAsAlias().put(colName,fName);
            }
        }catch (Exception e){
            e.printStackTrace();
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
                if(tableInfo == null){
                    parseModelClass(m2c);
                }
            }
        }
        tableInfo = tableInfoMap.get(m2c);
        return tableInfo.getTableName();
    }
}
