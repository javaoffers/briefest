package com.javaoffers.batis.modelhelper.util;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.exception.BaseException;
import com.javaoffers.batis.modelhelper.exception.ParseModelException;
import com.javaoffers.batis.modelhelper.utils.SoftCache;
import com.javaoffers.batis.modelhelper.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: Tools
 * @Auther: create by cmj on 2021/12/9 11:23
 */
public class HelperUtils {

    static Logger logger = LoggerFactory.getLogger(HelperUtils.class);

    static final String modelSeparation = "__";

    private static final ConvertRegisterSelectorDelegate convert = ConvertRegisterSelectorDelegate.convert;

    /**
     * Convert underscore to camel case
     *
     * @param list_map
     * @return
     */
    @Deprecated
    public static List<Map<String, Object>> initData(List<Map<String, Object>> list_map) {

        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (list_map != null && list_map.size() > 0) {
            for (Map<String, Object> data : list_map) {
                Map<String, Object> newData = new HashMap<String, Object>();

                final Set<Map.Entry<String, Object>> entries = data.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (StringUtils.isNotBlank(key)) {
                        String[] split = key.split("_");
                        final StringBuilder sb = new StringBuilder("");
                        for (int i = 0; split != null && i < split.length; i++) {
                            if (i == 0) {
                                String firstChar = split[i];
                                sb.append(firstChar);
                            } else {
                                String nextChar = split[i];
                                final String upperCase = nextChar.substring(0, 1).toUpperCase();
                                sb.append(upperCase);
                                if (nextChar.length() > 1) {
                                    final String substring = nextChar.substring(1);
                                    sb.append(substring);
                                }
                            }
                        }
                        final String newKey = sb.toString();//驼峰Key
                        newData.put(newKey, value);
                    }
                }
                if (newData != null && !newData.isEmpty()) {
                    list.add(newData);
                }
            }
        }
        return list;
    }

    /**
     * Resolve generics Get the class object of Model
     * Usually used in combination with {@code Utils.isBaseModel(fd))
     * @param fd
     * @return
     * @throws Exception
     */
    public static Class getModelClass(Field fd) throws Exception {
        return Utils.getModelClass(fd);
    }

    public static Class getGenericityClass(Field fd) throws Exception {
        return Utils.getGenericityClass(fd);
    }

    /**
     * Get the generic class of the collection
     *
     * @param fd
     * @return
     * @throws ClassNotFoundException
     */
    public static Class getGenericityClassOfCollect(Field fd) throws ClassNotFoundException {
       return Utils.getGenericityClassOfCollect(fd);
    }


    /**
     * Get all fields of this class including parent classes
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> Set<Field> getFields(Class<E> clazz) {
        //primitive type has no parent， so is null
        return Utils.getFields(clazz);
    }

    /**
     * Get the unique field name that can determine the outermost Model
     *
     * @param ones
     * @param uniqueFieldNameList_
     * @throws Exception
     * @throws SecurityException
     */
    public static void getUniqueFieldNames(Class mClass, String tableName, ArrayList<Field> ones, ArrayList<String> uniqueFieldNameList_, Map<String, Object> standardClumMap) throws SecurityException, Exception {
        //String prefix = modelClassName + modelSeparation;
        Set<String> hashSet = new HashSet<String>();
        //The framework does not allow to query only the subtable fields without querying the main table fields.
        // This situation will not occur
        for (Field fd : ones) {// one 2 one
            if (isBaseModel(fd)) {
                Field[] bmFs = fd.getType().getDeclaredFields();//field of model
                for (int i = 0; bmFs != null && i < bmFs.length; i++) {
                    Field bmf = bmFs[i];
                    BaseUnique[] uniques = bmf.getAnnotationsByType(BaseUnique.class);
                    if (uniques != null && uniques.length > 0) {
                        String name = bmf.getName();
                        String uniqueFieldName = getSpecialColName(tableName, name);
                        if (standardClumMap.containsKey(uniqueFieldName)) {
                            hashSet.add( uniqueFieldName);
                        }else if(standardClumMap.containsKey(name)){
                            hashSet.add( name);
                        }else if(standardClumMap.containsKey(uniqueFieldName = getSpecialColName(mClass, bmf))){
                            hashSet.add( uniqueFieldName);
                        }
                    }
                }

            }
            if (isBaseUnique(fd)) {
                String name = fd.getName();
                String uniqueFieldName = getSpecialColName(tableName, name);
                if (standardClumMap.containsKey(uniqueFieldName)) {
                    hashSet.add(uniqueFieldName);
                }else if(standardClumMap.containsKey(name)){
                    hashSet.add( name);
                }else if(standardClumMap.containsKey(uniqueFieldName = getSpecialColName(mClass, fd))){
                    hashSet.add( uniqueFieldName);
                }
            }
        }

        //If not, treat all fields  as unique
        if(hashSet.size() == 0){
            hashSet = ones.stream()
                    .map(field -> {
                                String name = field.getName();
                                String uniqueFieldName = getSpecialColName(tableName, name);
                                if (standardClumMap.containsKey(uniqueFieldName)) {
                                    return uniqueFieldName;
                                } else if (standardClumMap.containsKey(name)) {
                                    return name;
                                }else if(standardClumMap.containsKey(uniqueFieldName = getSpecialColName(mClass, field))){
                                    return uniqueFieldName;
                                }
                                return null;
                            }
                    ).filter(Objects::nonNull).collect(Collectors.toSet());
        }

        uniqueFieldNameList_.addAll(hashSet);
    }

    public static String getSpecialColName(String tableName, String colName){
        return tableName + "__" + colName;
    }

    public static String getSpecialColName(Class mClass, Field fd){
        return mClass.getSimpleName()+fd.getName();
    }

    public static boolean isInstanceOfCharSequenceOrNumber(Class c){
       return CharSequence.class.isAssignableFrom(c)
                || Number.class.isAssignableFrom(c);
    }

    public static boolean isBaseModel(Field fd) throws Exception {
        return Utils.isBaseModel(fd);
    }

    public static boolean isBaseModel(Class fd) {
        return Utils.isBaseModel(fd);
    }

    public static boolean isBaseUnique(Field fd) {
        BaseUnique[] bm = fd.getAnnotationsByType(BaseUnique.class);
        if (bm != null && bm.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * Cutting data
     *
     * @param list_map source data
     * @param map_ cut data of result
     * @param uniqueFieldNameList Cut according to this key
     */
    public static void inciseData(List<Map<String, Object>> list_map, Map<String, List<Map<String, Object>>> map_,
                                  ArrayList<String> uniqueFieldNameList) {
        for (Map<String, Object> m : list_map) {
            StringBuilder sb = new StringBuilder("_");
            for (int i = 0; uniqueFieldNameList != null && i < uniqueFieldNameList.size(); i++) {
                String uniqueKey = uniqueFieldNameList.get(i);
                String uniqueValue = m.get(uniqueKey) == null ? "" : convert.converterObject(String.class,m.get(uniqueKey));
                sb.append(uniqueValue);
            }
            List<Map<String, Object>> dataList = map_.get(sb.toString());
            if (dataList == null) {
                dataList = new LinkedList<Map<String, Object>>();
                map_.put(sb.toString(), dataList);
            }
            dataList.add(m);
        }
    }

    /**
     * Parse out all model classes
     * @param clazz class， this not list ,set ,array .
     * @return all model classes
     */
    public static List<Class> parseAllModelClass(Class clazz){
        return Utils.parseAllModelClass(clazz);
    }

}
