package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import com.javaoffers.batis.modelhelper.anno.ColName;
import com.javaoffers.batis.modelhelper.anno.fun.parse.FunAnnoParser;
import com.javaoffers.batis.modelhelper.anno.fun.parse.ParseSqlFunResult;
import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;
import com.javaoffers.batis.modelhelper.exception.FindColException;
import com.javaoffers.batis.modelhelper.exception.ParseTableException;
import com.javaoffers.batis.modelhelper.fun.ConstructorFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: Table Information Auxiliary Class
 * @Auther: create by cmj on 2022/5/2 02:05
 */
public class TableHelper {

    private final static Map<Class, TableInfo> tableInfoMap = new ConcurrentHashMap<>();

    private final static Map<String, Class> modelClass = new ConcurrentHashMap<>();

    private final static Map<Class, Boolean> modelIsParse = new ConcurrentHashMap<>();

    /**
     * Get all fields corresponding to Model. for select().colAll() parse
     *
     * @param modelClss
     * @return
     */
    public static List<String> getColAll(Class<?> modelClss) {
        List<String> colAll = new LinkedList<>();
        TableInfo tableInfo = tableInfoMap.get(modelClss);
        String tableName = tableInfo.getTableName();
        tableInfo.getFieldNameColNameOfModel().forEach((fieldName, colName) -> {
            if (tableInfo.isSqlFun(colName)) {
                if(!tableInfo.isGroupFun(colName)){
                    colAll.add(colName + " as " + tableName + ModelHelpperConstants.SPLIT_LINE + fieldName);
                }
            } else {
                colAll.add(tableName + "." + colName + " as " + fieldName);
            }

        });
        return colAll;
    }

    public static List<SqlColInfo> getColAllAndAliasNameOnly(Class<?> modelClss) {
        List<SqlColInfo> colAll = new LinkedList<>();
        TableInfo tableInfo = tableInfoMap.get(modelClss);
        tableInfo.getFieldNameColNameOfModel().forEach((colName, fieldName) -> {
            colAll.add(new SqlColInfo(tableInfo.getTableName(), colName, fieldName, tableInfo.isSqlFun(colName)));
        });
        return colAll;
    }

    public static Map<String, List<Field>> getColAllAndFieldOnly(Class<?> modelClss) {
        TableInfo tableInfo = tableInfoMap.get(modelClss);
        Map<String, List<Field>> colNameOfModelField = tableInfo.getColNameAndFieldOfModel();
        return colNameOfModelField;
    }

    public static Map<String, List<Field>> getOriginalColAllAndFieldOnly(Class<?> modelClss) {
        TableInfo tableInfo = tableInfoMap.get(modelClss);
        Map<String, List<Field>> colNameOfModelField = tableInfo.getOriginalColNameOfModelField();
        return colNameOfModelField;
    }

    /**
     * for select col
     * @param myFun
     * @return col info
     */
    public static String getColName(GetterFun myFun) {
        SqlColInfo sqlColInfo = getSqlColInfo(myFun);
        String tableName = sqlColInfo.getTableName();
        String colName = sqlColInfo.getColNameNotBlank();
        String aliasName = sqlColInfo.getAliasName();
        if(sqlColInfo.isSqlFun()){
            colName = colName + " as " + tableName + ModelHelpperConstants.SPLIT_LINE + aliasName;
        }else{
            // dont append  ModelHelpperConstants.SPLIT_LINE.
            colName = tableName + "." + colName + " as " + aliasName;
        }
        return colName;
    }

    public static Pair<String, String> getSelectAggrColStatement(GetterFun myFun){
        SqlColInfo sqlColInfo = getSqlColInfo(myFun);
        String tableName = sqlColInfo.getTableName();
        String colName = sqlColInfo.getColNameNotBlank();
        String aliasName = tableName + ModelHelpperConstants.SPLIT_LINE + sqlColInfo.getAliasName();
        Pair<String, String> pair = null;
        if(sqlColInfo.isSqlFun()){
            pair = Pair.of(colName, aliasName);
        }else{
            pair = Pair.of(tableName + "." + colName, aliasName);
        }
        return pair;
    }

    public static String getColNameNotAs(GetterFun myFun) {
        SqlColInfo sqlColInfo = getSqlColInfo(myFun);
        String colName = sqlColInfo.getColNameNotBlank();
        String tableName = sqlColInfo.getTableName();
        if(!sqlColInfo.isSqlFun()){
            colName = tableName + "." + colName;
        }
        return colName;
    }

    public static SqlColInfo getSqlColInfo(GetterFun myFun){
        String methodName = StringUtils.EMPTY;
        try {
            // 直接调用writeReplace
            Method writeReplace = myFun.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object sl = writeReplace.invoke(myFun);
            SerializedLambda serializedLambda = (SerializedLambda) sl;
            methodName = serializedLambda.getImplMethodName();
            String implClass = serializedLambda.getImplClass();
            TableInfo tableInfo = tableInfoMap.get(modelClass.get(implClass));
            Map<String, String> colNameOfGetter = tableInfo.getMethodNameMappingFieldNameOfGetter();
            colNameOfGetter.computeIfAbsent(methodName, k -> {
                k = k.startsWith("get") ? k.substring(3) : k.startsWith("is") ? k.substring(2) : k;
                k = k.substring(0, 1).toLowerCase() + k.substring(1);
                return k;
            });

            String fieldName = colNameOfGetter.get(methodName);
            String colName = tableInfo.getFieldNameColNameOfModel().get(fieldName);
            return new SqlColInfo(tableInfo.getTableName(), colName, fieldName, tableInfo.isSqlFun(colName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new FindColException("Error parsing sql field ： " + myFun.toString());
    }


    public static Pair<String, String> getColNameAndAliasName(GetterFun myFun) {
        SqlColInfo selectColInfo = getSqlColInfo(myFun);
        String tableName = selectColInfo.getTableName();
        String colName = selectColInfo.getColName();
        String aliasName = selectColInfo.getAliasName();
        if(!selectColInfo.isSqlFun()){
            colName = tableName + "." + colName;
        }
        return Pair.of(colName, aliasName);
    }

    public static String getColNameOnly(GetterFun myFun) {
        SqlColInfo sqlColInfo = getSqlColInfo(myFun);
        return sqlColInfo.getColNameNotBlank();
    }

    /**
     * Parse table information.
     *
     * @param clazz
     */
    public static void parseTableInfo(Class clazz, Connection connection) {
        String name = clazz.getName().replaceAll("\\.", "/");
        modelClass.computeIfAbsent(name, k -> {
            try {
                //解析model class
                parseModelClass(clazz, connection);
                return clazz;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * Parse the model
     *
     * @param modelClazz Classes marked with the annotation @BaseModel
     */
    private static void parseModelClass(Class<?> modelClazz, Connection connection) {
        Boolean isParse = modelIsParse.getOrDefault(modelClazz, false);
        if (!isParse) {
            synchronized (modelClazz) {
                isParse = modelIsParse.getOrDefault(modelClazz, false);
                if (!isParse) {
                    BaseModel table = modelClazz.getDeclaredAnnotation(BaseModel.class);
                    Assert.isTrue(table != null, " base model is null. please use @BaseModel on class");
                    String tableName = table.value();
                    if (StringUtils.isBlank(tableName)) {
                        String simpleName = modelClazz.getSimpleName();
                        tableName = conLine(simpleName);
                    }

                    try {
                        TableInfo tableInfo = new TableInfo(tableName);
                        DatabaseMetaData metaData = connection.getMetaData();

                        ResultSet tableResultSet = metaData.getTables(connection.getCatalog(), connection.getSchema(), tableName, null);
                        ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableName);
                        LinkedList<String> primaryKeyList = new LinkedList<>();
                        while (primaryKeys.next()) {
                            String primaryColName = primaryKeys.getString(ColumnLabel.COLUMN_NAME);
                            primaryKeyList.add(primaryColName);
                        }
                        while (tableResultSet.next()) {
                            // Get table field structure
                            ResultSet columnResultSet = metaData.getColumns(connection.getCatalog(), "", tableName, "%");
                            while (columnResultSet.next()) {
                                // Field Name
                                String columnName = columnResultSet.getString(ColumnLabel.COLUMN_NAME);
                                // type of data
                                String columnType = columnResultSet.getString(ColumnLabel.TYPE_NAME);
                                //the default value of the field
                                Object defaultValue = columnResultSet.getString(ColumnLabel.COLUMN_DEF);
                                //Whether to auto increment
                                boolean isAutoincrement = "YES".equalsIgnoreCase(columnResultSet.getString(ColumnLabel.IS_AUTOINCREMENT));
                                ColumnInfo columnInfo = new ColumnInfo(columnName, columnType, isAutoincrement, defaultValue);
                                tableInfo.getColumnInfos().add(columnInfo);
                                tableInfo.putColNames(columnName, columnInfo);
                                if (primaryKeyList.contains(columnName)) {
                                    tableInfo.putPrimaryColNames(columnName, columnInfo);
                                }
                            }
                        }
                        tableInfoMap.put(modelClazz, tableInfo);

                        Field[] colFs = Utils.getFields(modelClazz).toArray(new Field[]{});
                        for (Field colF : colFs) {
                            colF.setAccessible(true);
                            String colName = conLine(colF.getName());
                            BaseUnique baseUnique = colF.getDeclaredAnnotation(BaseUnique.class);
                            if (baseUnique != null) {
                                if (StringUtils.isNotBlank(baseUnique.value())) {
                                    colName = baseUnique.value();
                                }
                            }
                            //parse @ColName and @funAnno
                            ParseSqlFunResult parseColName = FunAnnoParser.parse(tableInfo, modelClazz, colF, colName);
                            if (parseColName != null) {
                                colName = parseColName.getSqlFun();
                                //If it is not the original table field, set isFun to true
                                if (!tableInfo.getColNames().containsKey(colName)) {
                                    parseColName.setFun(true);
                                }
                                tableInfo.putSqlFun(colName, parseColName.isFun());
                                tableInfo.putGroupFun(colName, parseColName.isGroup());
                            } else {
                                // Indicates that this field does not have any annotation information.
                                // then the field must belong to a field in the original table
                                // Otherwise skip parsing of this field
                                if (!tableInfo.getColNames().containsKey(colName)) {
                                    continue;
                                }
                            }

                            String fieldName = colF.getName();
                            // original table fields and sql-fun fields
                            tableInfo.putFieldNameColNameOfModel(fieldName, colName);
                            tableInfo.putColNameAndFieldOfModel(colName, colF);

                            //fieldName and field
                            tableInfo.putFieldNameAndField(fieldName, colF);

                            if (tableInfo.getColNames().containsKey(colName)) {
                                //original table fields
                                tableInfo.putOriginalColNameAndFieldOfModelField(colName, colF);
                            }
                        }
                        tableInfo.unmodifiable();
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ParseTableException(e.getMessage(), e);
                    }
                    modelIsParse.put(modelClazz, true);
                }
            }
        }
    }

    /**
     * turn underscore
     *
     * @param info
     * @return
     */
    private static String conLine(String info) {
        if (info.contains("_")) {
            return info;
        }

        String[] split = info.split("");
        StringBuilder builder = new StringBuilder();
        String last = null;
        for (String s : split) {
            if (StringUtils.isAllUpperCase(s)) {
                if (builder.length() != 0 && StringUtils.isAllLowerCase(last)) {
                    s = s.toLowerCase();
                    s = "_" + s;
                }
            }
            builder.append(s);
            last = s;
        }
        info = builder.toString();
        String defaultIgnoreCase = "YES";
        String ignoreCase = System.getProperty("IgnoreCase", defaultIgnoreCase);
        if (defaultIgnoreCase.equalsIgnoreCase(ignoreCase)) {
            info = info.toLowerCase();
        }
        return info;
    }

    /**
     * Parse the class corresponding to the constructor
     *
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
            implClass = lamdaName.replaceAll("\\.", "/");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelClass.get(implClass);
    }

    /**
     * Parse the class corresponding to the constructor
     *
     * @param constructorFun
     * @param <M2>
     * @return
     */
    public static <M2> Class<M2> getClassFromConstructorFunForJoin(ConstructorFun<M2> constructorFun, DataSource dataSource) {

        // 直接调用writeReplace
        String implClass = StringUtils.EMPTY;
        Class clazz = null;
        Class aClass = null;
        Connection connection = null;
        try {
            try {
                Method method = constructorFun.getClass().getDeclaredMethods()[0];
                method.setAccessible(true);
                Object sl = method.invoke(constructorFun);
                clazz = sl.getClass();
                String lamdaName = clazz.getName();
                implClass = lamdaName.replaceAll("\\.", "/");

            } catch (Exception e) {
                e.printStackTrace();
            }
            aClass = modelClass.get(implClass);
            // if null, The description has not been parsed.
            // Maybe there is no corresponding Mapper for this class, we need to parse it again
            if (aClass == null) {
                try {
                    connection = dataSource.getConnection();
                    parseTableInfo(clazz, connection);
                    aClass = modelClass.get(implClass);
                    if (aClass == null) {
                        throw new ParseTableException("There was an error parsing the table, the table may not exist");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return aClass;
    }

    /**
     * get tableName by Class
     *
     * @param m2c
     * @param <M2>
     * @return
     */
    public static <M2> String getTableName(Class<M2> m2c) {
        return getTableInfo(m2c).getTableName();
    }

    /**
     * get TableInfo by class
     *
     * @param m2c
     * @return
     */
    public static TableInfo getTableInfo(Class<?> m2c) {
        TableInfo tableInfo = tableInfoMap.get(m2c);
        return tableInfo;
    }
}
