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
    public <E> List<E> converterResultSet2Model(Class<E> clazz, ResultSetExecutor rs) {
        try {
            return buildModel(clazz, rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseModelException(e.getMessage());
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
    private static <E> List<E> buildModel(Class<E> clazz, ResultSetExecutor rs) throws Exception {
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


//        List<E> resultList = new ArrayList<>();
//        Map<String, List<Map<String, Object>>> map_ = new LinkedHashMap<>();//存放切分后的数据（根据唯一字段的值分组）
//        Set<Field> fields = HelperUtils.getFields(clazz);
//        //checkIsExistsSameSuperModel(clazz);
//        ArrayList<Field> ones = new ArrayList<Field>();//非数组或集合model,一对一
//        ArrayList<Field> arrays = new ArrayList<Field>();//存放数组model
//        ArrayList<Field> list_ = new ArrayList<Field>();//存放list集合model
//        ArrayList<Field> set_ = new ArrayList<Field>();//存放set集合model
//        for(Field fd : fields) {
//            if(fd.getType().isArray()) {
//                arrays.add(fd);
//            }else if(List.class.isAssignableFrom(fd.getType())) {
//                list_.add(fd);
//            }else if(Set.class.isAssignableFrom(fd.getType())) {
//                set_.add(fd);
//            }else {
//                ones.add(fd);
//            }
//        }
//        TableInfo tableInfo = TableHelper.getTableInfo(clazz);
//        String tableName = tableInfo.getTableName();
//        DBType dbType = tableInfo.getDbType();
//        ArrayList<String> uniqueFieldNameList = new ArrayList<String>();//Stores fields that can determine a piece of main table data (main model)
//        HelperUtils.getUniqueFieldNames(clazz, tableName,  dbType, ones, uniqueFieldNameList,list_map.get(0));//Get the fields that can determine a piece of main table data (main model)
//        if(uniqueFieldNameList==null || uniqueFieldNameList.size()==0){
//            return resultList;
//        }
//        HelperUtils.inciseData(list_map, map_, uniqueFieldNameList);//Cutting data
//        List<E> list = buildData(tableName, dbType, clazz,map_,ones,arrays,list_,set_);
//        resultList.addAll(list);
//        return resultList;
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

        for(ModelFieldInfo one: ones){
            if(one.isModelClass()){
                Class modelClassOfField = one.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(rs, modelInfo.getUnique(colNames));
                Object o = tmpCache.get(uniqueKey);
                if(o==null){
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    one.getSetter().setter(model, o);

                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
                }else{
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            }else if(processNoneModelField){
                Object o = null;
                if((o=rs.getColValueByColName(one.getAliasName())) != null
                        || (o=rs.getColValueByColName(one.getFieldName()))!=null){
                    Object o1 = convert.converterObject(one.getFieldGenericClass(), o);
                    one.getSetter().setter(model, o1);
                }
            }
        }

        for(ModelFieldInfo arrayField : arrays){
            if(arrayField.isModelClass()){
                Class modelClassOfField = arrayField.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(rs, modelInfo.getUnique(colNames));
                Object o = tmpCache.get(uniqueKey);
                if(o == null){
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    Object arrayObj = arrayField.getGetter().getter(model);
                    if(arrayObj == null){
                        arrayObj = Array.newInstance(modelClassOfField, 1);
                        Array.set(arrayObj, 0, o);
                        arrayField.getSetter().setter(model, arrayObj);
                    }else{
                        int len = Array.getLength(arrayObj);
                        Object newArrayObj = Array.newInstance(modelClassOfField, len + 1 );
                        System.arraycopy(arrayObj,0, newArrayObj, 0, len);
                        Array.set(newArrayObj, len, o);
                        arrayField.getSetter().setter(model, newArrayObj);
                    }
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
                }else {
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            }else{
                Class fieldClass = arrayField.getFieldGenericClass();
                String aliasName = arrayField.getAliasName();
                String fieldName = arrayField.getFieldName();
                Object o = null;
                if((o = rs.getColValueByColName(aliasName)) != null
                        ||(o = rs.getColValueByColName(fieldName)) != null){
                    o = convert.converterObject(arrayField.getFieldGenericClass(), o);
                    Object arrayObj = arrayField.getGetter().getter(model);
                    if(arrayObj == null){
                        arrayObj = Array.newInstance(fieldClass, 1);
                        Array.set(arrayObj, 0, o);
                        arrayField.getSetter().setter(model, arrayObj);
                    }else{
                        int len = Array.getLength(arrayObj);
                        boolean isSame = false;
                        for(int i=0; i < len; i++){
                            if(isSame = o.equals(Array.get(arrayObj, i))){
                                break;
                            }
                        }
                        if(isSame){
                            Object newArrayObj = Array.newInstance(fieldClass, len + 1 );
                            System.arraycopy(arrayObj,0, newArrayObj, 0, len);
                            Array.set(newArrayObj, len, o);
                            arrayField.getSetter().setter(model, newArrayObj);
                        }
                    }
                }
            }
        }

        for(ModelFieldInfo listField : list_){
            List listFieldValue = (List) listField.getGetter().getter(model);
            if(listFieldValue == null){
                listFieldValue = (List) listField.getNewc();
                listField.getSetter().setter(model, listFieldValue);
            }
            if(listField.isModelClass()){
                Class modelClassOfField = listField.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(rs, modelInfo.getUnique(colNames));
                Object o = tmpCache.get(uniqueKey);

                if(o == null){
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);
                    listFieldValue.add(o);

                }else{
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            }else {
                String aliasName = listField.getAliasName();
                String fieldName = listField.getFieldName();
                Object o = null;
                if((o=rs.getColValueByColName(aliasName)) != null || (o=rs.getColValueByColName(fieldName))!=null){
                    o = convert.converterObject(listField.getFieldGenericClass(), o);
                    if(!listFieldValue.contains(o)){
                        listFieldValue.add(o);
                    }
                }
            }
        }

        for(ModelFieldInfo setField : set_){
            Set setFieldValue = (Set) setField.getGetter().getter(model);
            if(setFieldValue == null){
                setFieldValue = (Set) setField.getNewc();
                setField.getSetter().setter(model, setFieldValue);
            }

            if(setField.isModelClass()){
                Class modelClassOfField = setField.getModelClassOfField();
                ModelInfo modelInfo = TableHelper.getModelInfo(modelClassOfField);
                List<String> colNames = rs.getColNames();
                String uniqueKey = getUniqueKey(rs, modelInfo.getUnique(colNames));
                Object o = tmpCache.get(uniqueKey);

                if(o == null){
                    o = modelInfo.newC();
                    tmpCache.put(uniqueKey, o);
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, true);

                }else{
                    buildData(rs, tmpCache, modelInfo.getOnes(colNames), modelInfo.getArrays(colNames),
                            modelInfo.getList(colNames), modelInfo.getSet(colNames), o, false);
                }
            }else {
                String aliasName = setField.getAliasName();
                String fieldName = setField.getFieldName();
                Object o = null;
                if((o=rs.getColValueByColName(aliasName)) != null || (o=rs.getColValueByColName(fieldName))!=null){
                    o = convert.converterObject(setField.getFieldGenericClass(), o);
                    if(!setFieldValue.contains(o)){
                        setFieldValue.add(o);
                    }
                }
            }

        }


//
//
//
//
//        //---------------------------------------------------
//        List<E> resultList = new ArrayList<>();
//
//        try {
//            Set<Map.Entry<String, List<Map<String, Object>>>> entrySet = map_.entrySet();
//            for (Map.Entry<String, List<Map<String, Object>>> entry : entrySet) {
//
//                List<Map<String, Object>> list = entry.getValue();//Get a cut data
//                if (list == null || list.size() == 0) {
//                    continue;
//                }
//                Map<String, Object> mp = list.get(0);//Get the unique cutting data, just take 0, which refers to the main model main data
//                Model<E> model = Model.getModel(clazz.newInstance());
//
//                for (Field fd : ones) {
//                    if (HelperUtils.isBaseModel(fd)) {
//                        List ls = buildModel(HelperUtils.getModelClass(fd), list);
//                        if (ls != null && ls.size() == 1) {
//                            Object object = ls.get(0);
//                            fd.set(model.getModelAndSetStatusIsTrue(), object);
//                        }
//                        continue;
//                    }
//                    String name = HelperUtils.getSpecialColName(tableName, dbType, fd.getName());
//                    Object o = null;
//                    if ((o = mp.get(name)) != null || (o = mp.get(fd.getName())) != null
//                            || (o = mp.get(HelperUtils.getSpecialColName(clazz, fd))) != null) {
//                        Class<?> type = fd.getType();
//                        Object object2 = convert.converterObject(type, o, fd);
//                        fd.set(model.getModelAndSetStatusIsTrue(), object2);
//                    }
//
//                    continue;
//                }
//                for (Field fd : arrays) {
//                    if (HelperUtils.isBaseModel(fd)) {
//                        List<E> ls = buildModel(HelperUtils.getModelClass(fd), list);
//                        if (ls != null && !ls.isEmpty() && ls.size() > 0) {
//                            Class<? extends Object> class1 = ls.get(0).getClass();
//                            Object object = Array.newInstance(class1, ls.size());
//                            for (int i = 0; i < ls.size(); i++) {
//                                Array.set(object, i, ls.get(i));
//                            }
//                            if (object != null) {
//                                fd.set(model.getModelAndSetStatusIsTrue(), object);
//                            }
//                        }
//                    } else {
//                        String name = HelperUtils.getSpecialColName(tableName, dbType, fd.getName());
//                        ArrayList<Object> arrayData = new ArrayList<Object>();
//                        for (Map<String, Object> map : list) {
//                            Object object = map.get(name);
//                            if (object == null) {
//                                object = map.get(fd.getName());
//                            }
//                            if (object == null) {
//                                object = map.get(HelperUtils.getSpecialColName(clazz, fd));
//                            }
//                            if (object != null) {
//                                arrayData.add(object);
//                            }
//                        }
//                        String typeName = fd.getGenericType().getTypeName();
//                        Class<?> originClass = Class.forName(typeName.substring(0, typeName.length() - 2));
//                        Object object = Array.newInstance(originClass, arrayData.size());
//                        if (arrayData != null && arrayData.size() > 0) {
//                            for (int i = 0; i < arrayData.size(); i++) {
//                                Object object2 = convert.converterObject(originClass.getClass(), arrayData.get(i), fd);
//                                Array.set(object, i, object2);
//                            }
//                            fd.set(model.getModelAndSetStatusIsTrue(), object);
//                        }
//                    }
//                }
//                for (Field fd : list_) {
//                    Class<?> type = fd.getType();
//                    List ls = Collections.EMPTY_LIST;
//                    if (type.isInterface()) {
//                        if (HelperUtils.isBaseModel(fd)) {
//                            ls = buildModel(HelperUtils.getModelClass(fd), list);
//                        } else {
//                            ls = getList(clazz, tableName, dbType, list, fd);
//                        }
//                    } else {
//                        if (HelperUtils.isBaseModel(fd)) {
//                            ls = buildModel(HelperUtils.getModelClass(fd), list);
//                        } else {
//                            ls = getList(clazz, tableName, dbType, list, fd);
//                        }
//                        List newInstance = (List) fd.get(model.getModelAndSetStatusIsTrue());
//                        if (newInstance == null) {
//                            newInstance = (List) type.newInstance();
//                        }
//                        newInstance.addAll(ls);
//                        ls = newInstance;
//                    }
//
//                    if (ls != null && ls.size() > 0) {
//                        fd.set(model.getModelAndSetStatusIsTrue(), ls);
//                    }
//
//                }
//                for (Field fd : set_) {
//                    Class<?> type = fd.getType();
//                    List ls = Collections.EMPTY_LIST;
//                    Set newInstance = Collections.EMPTY_SET;
//                    if (type.isInterface()) {
//
//                        if (HelperUtils.isBaseModel(fd)) {
//                            ls = buildModel(HelperUtils.getModelClass(fd), list);
//                        } else {
//                            ls = getList(clazz, tableName, dbType, list, fd);
//                        }
//                        newInstance = new HashSet();
//                        newInstance.addAll(ls);
//                    } else {
//                        if (HelperUtils.isBaseModel(fd)) {
//                            ls = buildModel(HelperUtils.getModelClass(fd), list);
//                        } else {
//                            ls = getList(clazz, tableName, dbType, list, fd);
//                        }
//                        newInstance = (Set) fd.get(model.getModelAndSetStatusIsTrue());
//                        if (newInstance == null) {
//                            newInstance = (Set) type.newInstance();
//                        }
//                        newInstance.addAll(ls);
//                    }
//                    if (newInstance != null && newInstance.size() > 0) {
//                        fd.set(model.getModelAndSetStatusIsTrue(), newInstance);
//                    }
//                }
//                if (model.isStatus()) {
//                    resultList.add(model.getModel());
//                }
//
//            }
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return resultList;
    }

    private static List getList(Class mClass, String tableName, DBType dbType, List<Map<String, Object>> list, Field fd) throws ClassNotFoundException {
        List ls;
        Class gClass = HelperUtils.getGenericityClassOfCollect(fd);//泛型类型
        ls = new ArrayList();
        List finalLs = ls;
        list.forEach(map -> {
            Object o = map.get(HelperUtils.getSpecialColName(tableName, dbType, fd.getName()));
            if (o == null) {
                o = map.get(fd.getName());
            }
            if (o == null) {
                o = map.get(HelperUtils.getSpecialColName(mClass, fd));
            }
            finalLs.add(convert.converterObject(gClass, o, fd));
        });
        return ls;
    }

    /**
     * string： mianmodel class name
     * String ： Save the name of the parent class of the current Model. If the same exists, it will prompt that the Model has the same parent class.
     */
    @Deprecated
    private static <E> void checkIsExistsSameSuperModel(Class<E> clazz) {
        Map<String, String> map = tl.get();
        final String name = clazz.getSuperclass().getName();

        if (map == null) {
            map = new HashMap<>();
        }
        if ("java.lang.Object".equals(name)) {
            return;
        } else {
            final String superName = map.get(name);
            if (StringUtils.isNotBlank(superName)) {
                throw new BaseException("Model models cannot have the same parent class to prevent field duplication! !" + clazz.getName());
            } else {
                map.put(name, name);
            }
        }

    }

}
