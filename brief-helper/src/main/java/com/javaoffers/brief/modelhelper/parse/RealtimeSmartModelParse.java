package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.core.ConvertDelegate;
import com.javaoffers.brief.modelhelper.core.ConvertProxy;
import com.javaoffers.brief.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.brief.modelhelper.exception.BaseException;
import com.javaoffers.brief.modelhelper.exception.ParseModelException;
import com.javaoffers.brief.modelhelper.jdbc.ResultSetExecutor;
import com.javaoffers.brief.modelhelper.util.HelperUtils;
import com.javaoffers.brief.modelhelper.util.Model;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfo;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfoPosition;
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @Description: model解析器
 * @Auther: create by cmj on 2023/07/21 19:59
 */
public class RealtimeSmartModelParse implements RealtimeModelParse {

    private final static ConvertRegisterSelectorDelegate convert = ConvertRegisterSelectorDelegate.convert;

    private final static ThreadLocal<Map<String, String>> tl = new ThreadLocal<Map<String, String>>();

    private final static AtomicLong nextKey = new AtomicLong();

    private final static String ROOT_KEY = "";

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
        List<ModelFieldInfoPosition> ones = modelInfo.getOnesCol(colNames);
        //read next row
        int size = ones.size();
        while (rs.nextRow()) {
            Object o = modelInfo.newC();
            result.add((E) o);
            buildDataForNormalSelect(rs, ones, o);
        }
        return result;
    }

    private static <E> void buildDataForNormalSelect(ResultSetExecutor rs,
                                                     List<ModelFieldInfoPosition> ones,
                                                     E model
    ) {
        for (ModelFieldInfoPosition one : ones) {
            if (!one.isModelClass()) {
                Object o = null;
                if ((o = rs.getColValueByColPosition(one.getPosition())) != null) {
                    ModelFieldInfo mfi = one.getModelFieldInfo();
                    Object o1 = convert(mfi, o);
                    mfi.getSetter().setter(model, o1);
                }
            }
        }
    }

    private static Object convert(ModelFieldInfo one, Object o) {
        ConvertProxy convertProxy = one.getConvertProxy();
        if (convertProxy == null) {
            convertProxy = convert.choseConverter(one.getFieldGenericClass(), o, one.getField());
            one.setConvertProxy(convertProxy);
        }
        Object o1 = convertProxy.convert(o);
        return o1;
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
        List<ModelFieldInfoPosition> ones = modelInfo.getOnesColWithOneModel(colNames);
        List<ModelFieldInfo> arrays = modelInfo.getArrays(colNames);
        List<ModelFieldInfo> list = modelInfo.getList(colNames);
        List<ModelFieldInfo> set = modelInfo.getSet(colNames);
        List<ModelFieldInfoPosition> unique = modelInfo.getUniqueCol(colNames);
        //read next row
        while (rs.nextRow()) {

            String keyStr = getUniqueKey(ROOT_KEY, rs, unique);
            Object o = tmpCache.get(keyStr);
            if (o == null) {
                o = modelInfo.newC();
                tmpCache.put(keyStr, o);
                result.add((E) o);
                buildData(keyStr,rs, tmpCache, ones, arrays, list, set, o, true);
            } else {
                buildData(keyStr, rs, tmpCache, ones, arrays, list, set, o, false);
            }
        }
        return result;
    }

    private static String getUniqueKey(String parentKey, ResultSetExecutor rs, List<ModelFieldInfoPosition> unique) {
        StringBuilder key = new StringBuilder(parentKey);
        if(unique.size()==0){
            return String.valueOf(nextKey.getAndIncrement());
        }
        unique.forEach(modelFieldInfoPosition -> {
            int position = modelFieldInfoPosition.getPosition();
            Object o = rs.getColValueByColPosition(position);
            key.append(modelFieldInfoPosition.getModelFieldInfo().getAliasName());
            key.append(":");
            key.append(Objects.hashCode(o));
        });
        return key.toString();
    }

    @SuppressWarnings("unchecked")
    private static <E> void buildData(
            String parentKey,
            ResultSetExecutor rs,
            Map<String, Object> tmpCache,
            List<ModelFieldInfoPosition> ones,
            List<ModelFieldInfo> arrays,
            List<ModelFieldInfo> list_,
            List<ModelFieldInfo> set_,
            E model,
            boolean processNoneModelField
    ) throws Exception {

        for (ModelFieldInfoPosition mif : ones) {
            ModelFieldInfo one = mif.getModelFieldInfo();
            if (one.isModelClass()) {
                Class modelClassOfField = one.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(parentKey, rs, modelInfo.getUniqueCol(colNames));
                Object o = tmpCache.get(uniqueKey);
                if (o == null) {
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    one.getSetter().setter(model, o);

                    buildData(uniqueKey,rs, tmpCache, modelInfo.getOnesColWithOneModel(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
                } else {
                    buildData(uniqueKey, rs, tmpCache, modelInfo.getOnesColWithOneModel(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            } else if (processNoneModelField) {
                Object o = null;
                if ((o = rs.getColValueByColPosition(mif.getPosition())) != null) {
                    o = convert(one, o);
                    one.getSetter().setter(model, o);
                }
            }
        }

        for (ModelFieldInfo arrayField : arrays) {

            Class modelClassOfField = arrayField.getModelClassOfField();
            ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
            List<String> colNames = rs.getColNames();
            String uniqueKey = getUniqueKey(parentKey, rs, modelInfo.getUniqueCol(colNames));
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
                buildData(uniqueKey, rs, tmpCache, modelInfo.getOnesColWithOneModel(colNames), modelInfo.getArrays(colNames),
                        modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
            } else {
                buildData(uniqueKey, rs, tmpCache, modelInfo.getOnesColWithOneModel(colNames), modelInfo.getArrays(colNames),
                        modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
            }
        }

        for (ModelFieldInfo listField : list_) {
            List listFieldValue = (List) listField.getGetter().getter(model);
            if (listFieldValue == null) {
                listFieldValue = (List) listField.getNewc();
                listField.getSetter().setter(model, listFieldValue);
            }

            Class modelClassOfField = listField.getModelClassOfField();
            ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
            List<String> colNames = rs.getColNames();
            String uniqueKey = getUniqueKey(parentKey, rs, modelInfo.getUniqueCol(colNames));
            Object o = tmpCache.get(uniqueKey);

            if (o == null) {
                o = modelInfo.newC();
                tmpCache.put(uniqueKey, o);
                buildData(uniqueKey, rs, tmpCache, modelInfo.getOnesColWithOneModel(colNames), modelInfo.getArrays(colNames),
                        modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
                listFieldValue.add(o);

            } else {
                buildData(uniqueKey, rs, tmpCache, modelInfo.getOnesColWithOneModel(colNames), modelInfo.getArrays(colNames),
                        modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
            }

        }

        for (ModelFieldInfo setField : set_) {
            Set setFieldValue = (Set) setField.getGetter().getter(model);
            if (setFieldValue == null) {
                setFieldValue = (Set) setField.getNewc();
                setField.getSetter().setter(model, setFieldValue);
            }
            Class modelClassOfField = setField.getModelClassOfField();
            ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
            List<String> colNames = rs.getColNames();
            String uniqueKey = getUniqueKey(parentKey, rs, modelInfo.getUniqueCol(colNames));
            Object o = tmpCache.get(uniqueKey);

            if (o == null) {
                o = modelInfo.newC();
                tmpCache.put(uniqueKey, o);
                buildData(uniqueKey, rs, tmpCache, modelInfo.getOnesColWithOneModel(colNames), modelInfo.getArrays(colNames),
                        modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);

            } else {
                buildData(uniqueKey, rs, tmpCache, modelInfo.getOnesColWithOneModel(colNames), modelInfo.getArrays(colNames),
                        modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
            }
        }
    }

}
