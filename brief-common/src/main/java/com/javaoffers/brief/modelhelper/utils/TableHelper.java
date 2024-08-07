package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.anno.NoneCol;
import com.javaoffers.brief.modelhelper.anno.fun.parse.FunAnnoParser;
import com.javaoffers.brief.modelhelper.anno.fun.parse.ParseSqlFunResult;
import com.javaoffers.brief.modelhelper.constants.ModelHelpperConstants;
import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextAware;
import com.javaoffers.brief.modelhelper.exception.BaseException;
import com.javaoffers.brief.modelhelper.exception.FindColException;
import com.javaoffers.brief.modelhelper.exception.ParseTableException;
import com.javaoffers.brief.modelhelper.filter.JqlFunFilter;
import com.javaoffers.brief.modelhelper.filter.impl.AsSqlFunFilterImpl;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConstructorFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.sql.DataSource;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @Description: Table Information Auxiliary Class
 * @Auther: create by cmj on 2022/5/2 02:05
 */
public class TableHelper implements BriefContextAware {

    private final  static Map<Class, ModelInfo> modelInfoMap = new ConcurrentHashMap<>();

    private final  static Map<Class, TableInfo> tableInfoMap = new ConcurrentHashMap<>();

    private final static Map<Class, List<Condition>> colAllMap = new ConcurrentHashMap<>();

    private final  static Map<String, Class> modelClass = new ConcurrentHashMap<>();

    private final static Map<Class, Boolean> modelIsParse = new ConcurrentHashMap<>();

    private final static List<JqlFunFilter> TABLE_HELPER_FILTER = new ArrayList<>();

    private static BriefContext briefContext ;

    static {
        TABLE_HELPER_FILTER.add(new AsSqlFunFilterImpl());
    }

    /**
     * Get all fields corresponding to Model. for select().colAll() parse
     *
     * @param modelClss
     * @return
     */
    public static List<String> getColAllForSelect(Class<?> modelClss) {
        List<String> colAll = new LinkedList<>();
        TableInfo tableInfo = tableInfoMap.get(modelClss);
        String tableName = tableInfo.getTableName();
        tableInfo.getFieldNameColNameOfModel().forEach((fieldName, colName) -> {
            if (tableInfo.isSqlFun(colName)) {
                if(!tableInfo.colNameIsExcludeColAll(colName)){
                    //Because function fields in the query result resolution can't identify which belongs to the table name.
                    // So you can't to map the Class, so to be like this
                    colAll.add(colName + " as " + tableName + ModelHelpperConstants.SPLIT_LINE + fieldName);
                }
            } else if(!tableInfo.fieldNameIsExcludeColAll(fieldName)){
                colAll.add(tableName + "." + colName + " as " + tableName + ModelHelpperConstants.SPLIT_LINE + fieldName);
            }

        });
        return colAll;
    }

    /**
     * Get all fields corresponding to Model. for select().colAll() parse
     *
     * @param modelClss
     * @return
     */
    public static <T extends Condition> List<T> getColAllForSelect(Class<?> modelClss, Function<String, T> createCondition) {
        List conditions = colAllMap.get(modelClss);
        if(conditions == null){
            List<String> colAllForSelect = getColAllForSelect(modelClss);
            conditions = new ArrayList<>();
            for(String colName : colAllForSelect){
                conditions.add(createCondition.apply(colName));
            }
            conditions = Collections.unmodifiableList(conditions);
            colAllMap.putIfAbsent(modelClss, conditions);
        }
        return conditions;
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
    public static String getColNameForSelect(GetterFun myFun) {
        SqlColInfo sqlColInfo = getSqlColInfo(myFun);
        String tableName = sqlColInfo.getTableName();
        String colName = sqlColInfo.getColNameNotBlank();
        String aliasName = sqlColInfo.getAliasName();
        if(sqlColInfo.isSqlFun()){
            colName = colName + " as " + tableName + ModelHelpperConstants.SPLIT_LINE + aliasName;
        }else{
            // append  ModelHelpperConstants.SPLIT_LINE.
            colName = tableName + "." + colName + " as " + tableName + ModelHelpperConstants.SPLIT_LINE + aliasName;
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
                throw e;
            }
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
                    Assert.isTrue(table != null, "please use @BaseModel on class " + modelClazz.getName());
                    parseTableInfo(modelClazz, connection, table);
                    parseModelInfo(modelClazz);
                }
            }
        }
    }

    private static void parseModelInfo(Class<?> modelClazz) {
        modelInfoMap.put(modelClazz, new ModelInfo(modelClazz));

    }

    //解析table info. 和数据库表做关联关系.
    private static void parseTableInfo(Class<?> modelClazz, Connection connection, BaseModel table) {
        String tableName = table.value();
        if (StringUtils.isBlank(tableName)) {
            String simpleName = modelClazz.getSimpleName();
            tableName = Utils.conLine(simpleName);
        }

        try {
            TableInfo tableInfo = new TableInfo(tableName).setModelClass(modelClazz);
            tableInfo.setBaseModel(table);
            briefContext.getTableInfoParser().parseTableInfo(connection, tableInfo);

            tableInfoMap.put(modelClazz, tableInfo);
            Field[] colFs = Utils.getFields(modelClazz).toArray(new Field[]{});
            boolean uniqueStatus = false;
            for (Field colF : colFs) {

                //Ignore fields
                if(colF.getDeclaredAnnotation(NoneCol.class) != null) {
                    continue;
                }

                String colName = Utils.conLine(colF.getName());
                BaseUnique baseUnique = colF.getDeclaredAnnotation(BaseUnique.class);
                if (baseUnique != null) {
                    uniqueStatus = true;
                    if (StringUtils.isNotBlank(baseUnique.value())) {
                        colName = baseUnique.value();
                    }
                }
                //parse @ColName and @funAnno
                ParseSqlFunResult parseColName = FunAnnoParser.parse(tableInfo, modelClazz, colF, colName);
                String fieldName = colF.getName();
                boolean isFunSql = false;
                boolean isExcludeColAll = false;
                if (parseColName != null) {
                    isExcludeColAll = parseColName.isExcludeColAll();
                    colName = parseColName.getSqlFun();
                    // In select.colAll , insert.colAll, update.colAll will ignore this field.
                    if(isExcludeColAll){
                        tableInfo.putFieldNameExcludeColAll(fieldName, isExcludeColAll);
                    }
                    isFunSql = parseColName.isFun();
                    tableInfo.putSqlFun(colName, isFunSql);
                    tableInfo.putColNameExcludeColAll(colName, isExcludeColAll);
                } else {
                    // Indicates that this field does not have any annotation information (not a sqlFun).
                    // then the field must belong to a colName in the original table
                    // Otherwise skip parsing of this field
                    if (!tableInfo.getColNames().containsKey(colName)) {
                        continue;
                    }
                }

                //Avoid save or update operations that affect the database record,
                // so we see it as SQL fun
                if(!isFunSql){
                    for(JqlFunFilter fieldFilter : TABLE_HELPER_FILTER){
                        if(fieldFilter.filter(colF)){
                            colName = tableName+"."+colName;
                            isFunSql = true;
                            tableInfo.putSqlFun(colName, true);
                            break;
                        }
                    }
                }

                //derive flag process
                DeriveProcessFire.processDerive(tableInfo, colF, colName);

                // original table fields and sql-fun fields
                tableInfo.putFieldNameColNameOfModel(fieldName, colName);
                tableInfo.putColNameAndFieldOfModel(colName, colF);

                //fieldName and field
                tableInfo.putFieldNameAndField(fieldName, colF);

                //not funSql and not excludeColAll and is originalColName
                if (!isFunSql && !isExcludeColAll && tableInfo.getColNames().containsKey(colName)) {
                    //original table fields
                    tableInfo.putOriginalColNameAndFieldOfModelField(colName, colF);
                }
            }
            Assert.isTrue(uniqueStatus, "Please declare @BaseUnique field in the model "+ modelClazz.getName());
            tableInfo.unmodifiable();



        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseTableException(e.getMessage(), e);
        }
        modelIsParse.put(modelClazz, true);
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
            throw new BaseException(e.getMessage());
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
                clazz = constructorFun.f().getClass();
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
                    throw new ParseTableException(e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseTableException(e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ParseTableException(e.getMessage());
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

    /**
     * getModelInfo
     * @param m2c
     * @param <T>
     * @return
     */
    public static <T> ModelInfo<T> getModelInfo(Class<T> m2c){
        return modelInfoMap.get(m2c);
    }

    @Override
    public void setBriefContext(BriefContext briefContext) {
        TableHelper.briefContext = briefContext;
    }
}
