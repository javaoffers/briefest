package com.javaoffers.base.batis.util;

import com.javaoffers.base.batis.anno.BaseModel;
import com.javaoffers.base.batis.anno.BaseUnique;
import com.javaoffers.base.batis.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/9 11:23
 */
public class Utils {

    /**
     * 下划线转换为驼峰
     *
     * @param list_map
     * @return
     */
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
     * 解析泛型 获取 Model 的 class 对象
     * @param fd
     * @return
     * @throws Exception
     */
    public static Class getModelClass(Field fd) throws Exception {
        Class<?> type2 = fd.getType();
        if (type2.isArray()) {
            String typeName = fd.getGenericType().getTypeName();
            return Class.forName(typeName.substring(0, typeName.length() - 2));
        } else if (List.class.isAssignableFrom(type2)) {
            return getGenericityClassOfCollect(fd);
        } else if (Set.class.isAssignableFrom(type2)) {
            return getGenericityClassOfCollect(fd);
        }
        return type2;
    }

    /**
     * 获取集合的泛型类
     *
     * @param fd
     * @return
     * @throws ClassNotFoundException
     */
    private static Class getGenericityClassOfCollect(Field fd) throws ClassNotFoundException {
        try {
            ParameterizedType listGenericType = (ParameterizedType) fd.getGenericType();
            Type listActualTypeArguments = listGenericType.getActualTypeArguments()[0];
            return Class.forName(listActualTypeArguments.getTypeName());
        } catch (Exception e) {
            new BaseException(e.getMessage() + "\n一对多关系注意引用集合类一定要加上泛型类例如 ：List<Model>,Model不能省去").printStackTrace();
            throw e;
        }

    }

    public static <E> Set<Field> getFields(Class<E> clazz) {
        Set<Field> list = new HashSet<Field>();
        if (!clazz.getName().equals("java.lang.Object")) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                list.add(f);
            }
            list.addAll(getFields(clazz.getSuperclass()));
        }
        return list;
    }

    /**
     * 获取能确定最外层Model的唯一字段名称
     *
     * @param ones
     * @param uniqueFieldNameList_
     * @throws Exception
     * @throws SecurityException
     */
    public static void getUniqueFieldNames(ArrayList<Field> ones, ArrayList<String> uniqueFieldNameList_, Map<String, Object> standardClumMap) throws SecurityException, Exception {
        HashSet<String> hashSet = new HashSet<String>();
        for (Field fd : ones) {//一对一
            if (isBaseModel(fd)) {//
                Field[] bmFs = fd.getType().getDeclaredFields();//model中field
                for (int i = 0; bmFs != null && i < bmFs.length; i++) {
                    Field bmf = bmFs[i];
                    BaseUnique[] uniques = bmf.getAnnotationsByType(BaseUnique.class);
                    if (uniques != null && uniques.length > 0) {
                        String uniqueFieldName = bmf.getName();
                        if (standardClumMap.containsKey(uniqueFieldName)) {
                            hashSet.add(uniqueFieldName);
                        }
                    }
                }

            }
            if (isBaseUnique(fd)) {
                String uniqueFieldName = fd.getName();
                if (standardClumMap.containsKey(uniqueFieldName)) {
                    hashSet.add(uniqueFieldName);
                }
            }
        }
        uniqueFieldNameList_.addAll(hashSet);
    }

    public static boolean isBaseModel(Field fd) throws Exception {
        Class<?> type = fd.getType();
        if (type.isArray()) {
            String typeName = fd.getGenericType().getTypeName();
            Class<?> class1 = Class.forName(typeName.substring(0, typeName.length() - 2));
            if (isBaseModel(class1)) {
                return true;
            }
        }
        if (List.class.isAssignableFrom(type)) {
            return isModelForListAndSet(fd);
        }
        if (Set.class.isAssignableFrom(type)) {
            return isModelForListAndSet(fd);
        }

        BaseModel[] bm = type.getAnnotationsByType(BaseModel.class);
        if (bm != null && bm.length > 0) {
            return true;
        }
        return false;
    }

    public static boolean isBaseModel(Class fd) {
        BaseModel[] bm = (BaseModel[]) fd.getAnnotationsByType(BaseModel.class);
        if (bm != null && bm.length > 0) {
            return true;
        }
        return false;
    }

    public static boolean isModelForListAndSet(Field fd) {
        try {
            ParameterizedType listGenericType = (ParameterizedType) fd.getGenericType();
            Type listActualTypeArguments = listGenericType.getActualTypeArguments()[0];
            Class<?> forName = Class.forName(listActualTypeArguments.getTypeName());
            if (isBaseModel(forName)) {
                return true;
            }
        } catch (Exception e2) {
            new BaseException(e2.getMessage() + "\n一对多关系注意引用集合类一定要加上泛型类例如 ：List<Model>,Model不能省去").printStackTrace();
        }
        return false;
    }

    public static boolean isBaseUnique(Field fd) {
        BaseUnique[] bm = fd.getAnnotationsByType(BaseUnique.class);
        if (bm != null && bm.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * 切割数据
     *
     * @param list_map
     * @param map_
     * @param uniqueFieldNameList
     */
    public static void inciseData(List<Map<String, Object>> list_map, Map<String, List<Map<String, Object>>> map_,
                                  ArrayList<String> uniqueFieldNameList) {
        for (Map<String, Object> m : list_map) {
            StringBuilder sb = new StringBuilder("_");
            for (int i = 0; uniqueFieldNameList != null && i < uniqueFieldNameList.size(); i++) {
                String uniqueKey = uniqueFieldNameList.get(i);
                String uniqueValue = m.get(uniqueKey) == null ? "" : m.get(uniqueKey).toString();
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
}
