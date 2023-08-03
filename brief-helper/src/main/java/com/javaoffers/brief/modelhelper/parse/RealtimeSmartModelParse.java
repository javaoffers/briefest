package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.brief.modelhelper.exception.BaseException;
import com.javaoffers.brief.modelhelper.exception.ParseModelException;
import com.javaoffers.brief.modelhelper.jdbc.ResultSetExecutor;
import com.javaoffers.brief.modelhelper.util.HelperUtils;
import com.javaoffers.brief.modelhelper.util.Model;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfo;
import com.javaoffers.brief.modelhelper.utils.ModelInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: model解析器
 * @Auther: create by cmj on 2023/07/21 19:59
 */
public class RealtimeSmartModelParse implements RealtimeModelParse {

    private final static ConvertRegisterSelectorDelegate convert = ConvertRegisterSelectorDelegate.convert;

    private final static ThreadLocal<Map<String, String>> tl = new ThreadLocal<Map<String, String>>();


    /**
     * rs 不需要next(又上层进行next操作), 直接获取数据即可.
     *
     * @param clazz
     * @param rs
     * @param <E>
     */
    @Override
    public <E> List<E> converterResultSet2ModelForJoinSelect(Class<E> clazz, ResultSetExecutor rs) {
        try {
            return buildModelForJoinSelect(clazz, rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseModelException(e.getMessage());
        }

    }

    @Override
    public <E> List<E> converterResultSet2ModelForNormalSelect(Class<E> clazz, ResultSetExecutor rs) {
        return buildModelForNormalSelect(clazz, rs);
    }

    /**
     * 用于原始数据集转换Model数据集
     *
     * @param clazz Model的Class对象
     * @param rs    封装后的Model数据集
     * @throws Exception
     * @throws SecurityException
     */
    private static <E> List<E> buildModelForNormalSelect(Class<E> clazz, ResultSetExecutor rs) {
        ArrayList<E> result = new ArrayList<>();
        List<String> colNames = rs.getColNames();
        ModelInfo<E> modelInfo = TableHelper.getModelInfo(clazz);
        List<ModelFieldInfo> ones = modelInfo.getOnes(colNames);
        List<ModelFieldInfo> arrays = modelInfo.getArrays(colNames);
        List<ModelFieldInfo> list = modelInfo.getList(colNames);
        List<ModelFieldInfo> set = modelInfo.getSet(colNames);
        List<ModelFieldInfo> unique = modelInfo.getUnique(colNames);
        //read next row
        while (rs.nextRow()){
            Object o = modelInfo.newC();
            result.add((E)o);
            for(String colName : colNames){
                //rs.getColValueByColName(colName);
            }
            //buildDataForNormalSelect(rs, ones, arrays, list, set, o);
        }
        return result;
    }

    private static <E> void buildDataForNormalSelect(ResultSetExecutor rs,
                                                 List<ModelFieldInfo> ones,
                                                 List<ModelFieldInfo> arrays,
                                                 List<ModelFieldInfo> list_,
                                                 List<ModelFieldInfo> set_,
                                                 E model
                                                 ) {
        for (ModelFieldInfo one : ones) {
            if (!one.isModelClass()) {
                Object o = null;
                if ((o = rs.getColValueByColName(one.getAliasName())) != null
                        || (o = rs.getColValueByColName(one.getFieldName())) != null) {
                    Object o1 = convert.converterObject(one.getFieldGenericClass(), o);
                    one.getSetter().setter(model, o1);
                }
            }
        }

        for (ModelFieldInfo arrayField : arrays) {
            if (!arrayField.isModelClass()) {
                Class fieldClass = arrayField.getFieldGenericClass();
                String aliasName = arrayField.getAliasName();
                String fieldName = arrayField.getFieldName();
                Object o = null;
                if ((o = rs.getColValueByColName(aliasName)) != null
                        || (o = rs.getColValueByColName(fieldName)) != null) {
                    o = convert.converterObject(arrayField.getFieldGenericClass(), o);
                    Object arrayObj = arrayField.getGetter().getter(model);
                    if (arrayObj == null) {
                        arrayObj = Array.newInstance(fieldClass, 1);
                        Array.set(arrayObj, 0, o);
                        arrayField.getSetter().setter(model, arrayObj);
                    } else {
                        int len = Array.getLength(arrayObj);
                        boolean isSame = false;
                        for (int i = 0; i < len; i++) {
                            if (isSame = o.equals(Array.get(arrayObj, i))) {
                                break;
                            }
                        }
                        if (isSame) {
                            Object newArrayObj = Array.newInstance(fieldClass, len + 1);
                            System.arraycopy(arrayObj, 0, newArrayObj, 0, len);
                            Array.set(newArrayObj, len, o);
                            arrayField.getSetter().setter(model, newArrayObj);
                        }
                    }
                }
            }
        }

        for (ModelFieldInfo listField : list_) {
            List listFieldValue = (List) listField.getGetter().getter(model);
            if (listFieldValue == null) {
                listFieldValue = (List) listField.getNewc();
                listField.getSetter().setter(model, listFieldValue);
            }
            if (!listField.isModelClass())  {
                String aliasName = listField.getAliasName();
                String fieldName = listField.getFieldName();
                Object o = null;
                if ((o = rs.getColValueByColName(aliasName)) != null || (o = rs.getColValueByColName(fieldName)) != null) {
                    o = convert.converterObject(listField.getFieldGenericClass(), o);
                    if (!listFieldValue.contains(o)) {
                        listFieldValue.add(o);
                    }
                }
            }
        }

        for (ModelFieldInfo setField : set_) {
            Set setFieldValue = (Set) setField.getGetter().getter(model);
            if (setFieldValue == null) {
                setFieldValue = (Set) setField.getNewc();
                setField.getSetter().setter(model, setFieldValue);
            }

            if (!setField.isModelClass()) {
                String aliasName = setField.getAliasName();
                String fieldName = setField.getFieldName();
                Object o = null;
                if ((o = rs.getColValueByColName(aliasName)) != null || (o = rs.getColValueByColName(fieldName)) != null) {
                    o = convert.converterObject(setField.getFieldGenericClass(), o);
                    if (!setFieldValue.contains(o)) {
                        setFieldValue.add(o);
                    }
                }
            }

        }
    }


    /**
     * 用于原始数据集转换Model数据集
     *
     * @param clazz Model的Class对象
     * @param rs    封装后的Model数据集
     * @throws Exception
     * @throws SecurityException
     */
    private static <E> List<E> buildModelForJoinSelect(Class<E> clazz, ResultSetExecutor rs) throws Exception {
        Map<String, Object> tmpCache = new HashMap<>();//
        ArrayList<E> result = new ArrayList<>();
        List<String> colNames = rs.getColNames();
        ModelInfo<E> modelInfo = TableHelper.getModelInfo(clazz);
        List<ModelFieldInfo> ones = modelInfo.getOnes(colNames);
        List<ModelFieldInfo> arrays = modelInfo.getArrays(colNames);
        List<ModelFieldInfo> list = modelInfo.getList(colNames);
        List<ModelFieldInfo> set = modelInfo.getSet(colNames);
        List<ModelFieldInfo> unique = modelInfo.getUnique(colNames);
        //read next row
        while (rs.nextRow()){

            String keyStr = getUniqueKey(rs, unique);
            Object o = tmpCache.get(keyStr);
            if(o==null){
                o = modelInfo.newC();
                tmpCache.put(keyStr, o);
                result.add((E)o);
                buildData(rs,tmpCache, ones, arrays, list, set, o, true);
            }else{
                buildData(rs,tmpCache, ones, arrays, list, set, o, false);
            }
        }
        return result;
    }

    private static String getUniqueKey(ResultSetExecutor rs, List<ModelFieldInfo> unique) {
        StringBuilder key = new StringBuilder();
        unique.forEach(modelFieldInfo -> {
            String aliasName = modelFieldInfo.getAliasName();
            Object o = rs.getColValueByColName(aliasName);
            if(o==null){
                o = rs.getColValueByColName(modelFieldInfo.getFieldName());
            }
            key.append(aliasName);
            key.append(":");
            key.append(String.valueOf(o));
        });
        return key.toString();
    }


    @SuppressWarnings("unchecked")
    private static <E> void buildData(
            ResultSetExecutor rs,
            Map<String, Object> tmpCache,
            List<ModelFieldInfo> ones,
            List<ModelFieldInfo> arrays,
            List<ModelFieldInfo> list_,
            List<ModelFieldInfo> set_,
            E model,
            boolean processNoneModelField
            ) throws Exception {

        for (ModelFieldInfo one : ones) {
            if (one.isModelClass()) {
                Class modelClassOfField = one.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(rs, modelInfo.getUnique(colNames));
                Object o = tmpCache.get(uniqueKey);
                if (o == null) {
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    one.getSetter().setter(model, o);

                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
                } else {
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            } else if (processNoneModelField) {
                Object o = null;
                if ((o = rs.getColValueByColName(one.getAliasName())) != null
                        || (o = rs.getColValueByColName(one.getFieldName())) != null) {
                    Object o1 = convert.converterObject(one.getFieldGenericClass(), o);
                    one.getSetter().setter(model, o1);
                }
            }
        }

        for (ModelFieldInfo arrayField : arrays) {
            if (arrayField.isModelClass()) {
                Class modelClassOfField = arrayField.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(rs, modelInfo.getUnique(colNames));
                Object o = tmpCache.get(uniqueKey);
                if (o == null) {
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    Object arrayObj = arrayField.getGetter().getter(model);
                    if (arrayObj == null) {
                        arrayObj = Array.newInstance(modelClassOfField, 1);
                        Array.set(arrayObj, 0, o);
                        arrayField.getSetter().setter(model, arrayObj);
                    } else {
                        int len = Array.getLength(arrayObj);
                        Object newArrayObj = Array.newInstance(modelClassOfField, len + 1);
                        System.arraycopy(arrayObj, 0, newArrayObj, 0, len);
                        Array.set(newArrayObj, len, o);
                        arrayField.getSetter().setter(model, newArrayObj);
                    }
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
                } else {
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            } else {
                Class fieldClass = arrayField.getFieldGenericClass();
                String aliasName = arrayField.getAliasName();
                String fieldName = arrayField.getFieldName();
                Object o = null;
                if ((o = rs.getColValueByColName(aliasName)) != null
                        || (o = rs.getColValueByColName(fieldName)) != null) {
                    o = convert.converterObject(arrayField.getFieldGenericClass(), o);
                    Object arrayObj = arrayField.getGetter().getter(model);
                    if (arrayObj == null) {
                        arrayObj = Array.newInstance(fieldClass, 1);
                        Array.set(arrayObj, 0, o);
                        arrayField.getSetter().setter(model, arrayObj);
                    } else {
                        int len = Array.getLength(arrayObj);
                        boolean isSame = false;
                        for (int i = 0; i < len; i++) {
                            if (isSame = o.equals(Array.get(arrayObj, i))) {
                                break;
                            }
                        }
                        if (isSame) {
                            Object newArrayObj = Array.newInstance(fieldClass, len + 1);
                            System.arraycopy(arrayObj, 0, newArrayObj, 0, len);
                            Array.set(newArrayObj, len, o);
                            arrayField.getSetter().setter(model, newArrayObj);
                        }
                    }
                }
            }
        }

        for (ModelFieldInfo listField : list_) {
            List listFieldValue = (List) listField.getGetter().getter(model);
            if (listFieldValue == null) {
                listFieldValue = (List) listField.getNewc();
                listField.getSetter().setter(model, listFieldValue);
            }
            if (listField.isModelClass()) {
                Class modelClassOfField = listField.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(rs, modelInfo.getUnique(colNames));
                Object o = tmpCache.get(uniqueKey);

                if (o == null) {
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
                    listFieldValue.add(o);

                } else {
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            } else {
                String aliasName = listField.getAliasName();
                String fieldName = listField.getFieldName();
                Object o = null;
                if ((o = rs.getColValueByColName(aliasName)) != null || (o = rs.getColValueByColName(fieldName)) != null) {
                    o = convert.converterObject(listField.getFieldGenericClass(), o);
                    if (!listFieldValue.contains(o)) {
                        listFieldValue.add(o);
                    }
                }
            }
        }

        for (ModelFieldInfo setField : set_) {
            Set setFieldValue = (Set) setField.getGetter().getter(model);
            if (setFieldValue == null) {
                setFieldValue = (Set) setField.getNewc();
                setField.getSetter().setter(model, setFieldValue);
            }

            if (setField.isModelClass()) {
                Class modelClassOfField = setField.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(rs, modelInfo.getUnique(colNames));
                Object o = tmpCache.get(uniqueKey);

                if (o == null) {
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);

                } else {
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            } else {
                String aliasName = setField.getAliasName();
                String fieldName = setField.getFieldName();
                Object o = null;
                if ((o = rs.getColValueByColName(aliasName)) != null || (o = rs.getColValueByColName(fieldName)) != null) {
                    o = convert.converterObject(setField.getFieldGenericClass(), o);
                    if (!setFieldValue.contains(o)) {
                        setFieldValue.add(o);
                    }
                }
            }

        }
    }

}
