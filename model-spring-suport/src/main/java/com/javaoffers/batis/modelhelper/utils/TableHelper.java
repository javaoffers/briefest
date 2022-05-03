package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import org.apache.commons.lang3.StringUtils;
import javax.sql.DataSource;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Arrays;
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

        return Arrays.asList("");
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






        }catch (Exception e){
            e.printStackTrace();
        }


        return colName;
    }

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
                }
            }
            tableInfoMap.put(modelClazz,tableInfo);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

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
}
