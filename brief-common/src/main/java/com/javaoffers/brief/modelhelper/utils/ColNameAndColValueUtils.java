package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.brief.modelhelper.anno.derive.flag.ShardingStrategyMark;
import com.javaoffers.brief.modelhelper.exception.GetColValueException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * create by cmj
 */
public class ColNameAndColValueUtils {

    /**
     * Parsing value of unique key .
     * @param <T> model (May is the proxy model where open auto update )
     * @param modelClass The real model class
     * @return key: colName of uniqueKey, value : value of colName
     */
    @Deprecated
    public static <T> Map<String, Object> parseUniqueCoNameAndUniqueColValue(T model, Class modelClass) {
        //key: colName, value: colValue
        HashMap<String, Object> colNameAndColValues = new HashMap<>();
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        Map<String, List<Field>> colNameOfModelField = tableInfo.getColNameAndFieldOfModel();
        primaryColNames.forEach((colName, colInfo) -> {
            List<Field> fields = colNameOfModelField.get(colName);
            Object o = null;
            for (Field field : fields) {
                try {
                    if (BlurUtils.containsBlurAnno(field)) {
                        continue;
                    }
                    o = field.get(model);
                    if (o != null) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new GetColValueException(e.getMessage());
                }
            }
            if (o != null) {
                colNameAndColValues.put(colName, o);
            }
        });
        return colNameAndColValues;
    }

    /**
     *  replace parseColNameAndColValue
     * @param model
     * @param modelClass
     * @return
     * @param <T>
     */
    public static <T> Map<Getter, Object> parseUniqueCoGetterAndUniqueColValue(T model, Class modelClass) {
        HashMap<Getter, Object> result = new HashMap<>();
        ModelInfo modelInfo = TableHelper.getModelInfo(modelClass);
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        Set<String> colNames = new HashSet<>(primaryColNames.keySet());
        //parse sharding
        //tip:  value of sharding col name that should not be modified. Will as where condtion
        DeriveInfo deriveColName = tableInfo.getDeriveColName(ShardingStrategyMark.SHARDING_TABLE_STRATEGY);
        if(deriveColName != null){
            colNames.add(deriveColName.getColName());
        }
        ArrayList<String> colName = new ArrayList<>(colNames);
        List<ModelFieldInfoPosition> onesCol = modelInfo.getOnesCol(colName);
        onesCol.forEach(modelFieldInfoPosition -> {
            Getter getter = modelFieldInfoPosition.getModelFieldInfo().getGetter();
            Object getterValue = getter.getter(model);
            if (getterValue!=null) {
                result.put(getter, getterValue);
            }
        });
        return result;
    }


    /**
     * parse ColName and ColValue for model. fill where condition
     * @param <T> model (May is the proxy model where open auto update )
     * @param modelClass The real model class
     * @return  ColName and ColValue
     */
    @Deprecated
    public static <T> Map<String, Object> parseColNameAndColValue(T model, Class modelClass) {
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        Map<String, List<Field>> colNameOfModelField = tableInfo.getColNameAndFieldOfModel();
        //key: colName, value: colValue
        HashMap<String, Object> colNameAndColValues = new HashMap<>();
        if (colNameOfModelField != null && colNameOfModelField.size() > 0) {
            colNameOfModelField.forEach((colName, fields) -> {
                Object o = null;
                try {
                    for (int i = 0; fields != null && i < fields.size(); i++) {
                        Field field = fields.get(i);
                        if (BlurUtils.containsBlurAnno(field)) {
                            continue;
                        }
                        o = field.get(model);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (o != null) {
                    colNameAndColValues.put(colName, o);
                }
            });
        }
        return colNameAndColValues;
    }

    /**
     * replace parseColNameAndColValue
     * @param model
     * @param modelClass
     * @return
     * @param <T>
     */
    public static <T> Map<Getter, Object> parseColGetterAndColValue(T model, Class modelClass) {
        HashMap<Getter, Object> result = new HashMap<>();
        ModelInfo modelInfo = TableHelper.getModelInfo(modelClass);
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        Map<String, String> fieldNameColNameOfModel = tableInfo.getFieldNameColNameOfModel();
        HashSet<String> colNames = new HashSet<>();
        colNames.addAll(fieldNameColNameOfModel.keySet());
        ArrayList<String> colNameList = new ArrayList<>(colNames);
        List<ModelFieldInfoPosition> onesCol = modelInfo.getOnesCol(colNameList);
        onesCol.forEach(modelFieldInfoPosition -> {
            Getter getter = modelFieldInfoPosition.getModelFieldInfo().getGetter();
            Object getterValue = getter.getter(model);
            if (getterValue != null) {
                result.put(getter, getterValue);
            }
        });
        return result;
    }
}
