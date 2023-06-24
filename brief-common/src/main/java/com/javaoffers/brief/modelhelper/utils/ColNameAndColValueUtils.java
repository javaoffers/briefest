package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.exception.GetColValueException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                        if (o instanceof Number && colInfo.isAutoincrement() && ((Number) o).longValue() == 0L) {
                            continue;
                        }
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
     * parse ColName and ColValue for model
     * @param <T> model (May is the proxy model where open auto update )
     * @param modelClass The real model class
     * @return  ColName and ColValue
     */
    public static <T> Map<String, Object> parseColNameAndColValue(T model, Class modelClass) {
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        Map<String, ColumnInfo> originalColNames = tableInfo.getColNames();
        Map<String, List<Field>> colNameOfModelField = tableInfo.getColNameAndFieldOfModel();
        AtomicBoolean status = new AtomicBoolean(false);
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
                        if (o != null) {
                            //Self-incrementing elements are not allowed to be 0
                            if (originalColNames.get(colName) != null
                                    && originalColNames.get(colName).isAutoincrement()
                                    && o instanceof Number
                                    && ((Number) o).longValue() == 0L) {
                                return;
                            }
                            break;
                        }
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
}
