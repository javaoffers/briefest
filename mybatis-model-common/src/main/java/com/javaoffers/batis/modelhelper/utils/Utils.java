package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.exception.BaseException;
import com.javaoffers.batis.modelhelper.exception.ParseModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author mingJie
 */
public class Utils {

    static Logger logger = LoggerFactory.getLogger(Utils.class);

    private static final SoftCache<Class, Set<Field>> SOFT_CACHE_CLASS_FIELDS = SoftCache.getInstance();

    private static final SoftCache<Field, Boolean> SOFT_CACHE_FIELDS_IS_MODEL = SoftCache.getInstance();

    private static final SoftCache<Class, Boolean> SOFT_CACHE_ClASS_IS_MODEL = SoftCache.getInstance();

    /**
     * Get all fields of this class including parent classes
     *
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> Set<Field> getFields(Class<E> clazz) {
        if (clazz == null || clazz.isPrimitive() || clazz.isInterface()) {
            return Collections.EMPTY_SET;
        }
        Set<Field> result = SOFT_CACHE_CLASS_FIELDS.get(clazz);
        if (result == null) {
            Set<Field> list = new HashSet<Field>();
            if (!clazz.getName().equals("java.lang.Object")) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    list.add(f);
                }
                list.addAll(getFields(clazz.getSuperclass()));
            }
            result = list;
            SOFT_CACHE_CLASS_FIELDS.put(clazz, result);
        }
        return result;
    }

    public static boolean isBaseModel(Field fd) throws Exception {
        Boolean isBaseModel = SOFT_CACHE_FIELDS_IS_MODEL.get(fd);
        if (isBaseModel != null) {
            return isBaseModel;
        }
        isBaseModel = false;
        Class<?> type = fd.getType();
        if (type.isArray()) {
            String typeName = fd.getGenericType().getTypeName();
            try {
                Class<?> class1 = Class.forName(typeName.substring(0, typeName.length() - 2));
                if (isBaseModel(class1)) {
                    isBaseModel = true;
                }
            } catch (Exception e) {
                // Primitive types, no class
            }

        } else if (List.class.isAssignableFrom(type)) {
            isBaseModel = isModelForListAndSet(fd);
        } else if (Set.class.isAssignableFrom(type)) {
            isBaseModel = isModelForListAndSet(fd);
        } else {
            BaseModel[] bm = type.getAnnotationsByType(BaseModel.class);
            if (bm != null && bm.length > 0) {
                isBaseModel = true;
            }
        }

        SOFT_CACHE_FIELDS_IS_MODEL.put(fd, isBaseModel);
        return isBaseModel;
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
            new BaseException(e2.getMessage() + "One-to-many relationship." +
                    " Note that the reference collection class must be added with a generic class." +
                    " For example: List<Model>, Model cannot be omitted").printStackTrace();
        }
        return false;
    }

    public static boolean isBaseModel(Class clazz) {
        Boolean isBaseModel = SOFT_CACHE_ClASS_IS_MODEL.get(clazz);
        if (isBaseModel != null) {
            return isBaseModel;
        }
        isBaseModel = false;
        BaseModel[] bm = (BaseModel[]) clazz.getAnnotationsByType(BaseModel.class);
        if (bm != null && bm.length > 0) {
            isBaseModel = true;
        }
        SOFT_CACHE_ClASS_IS_MODEL.put(clazz, isBaseModel);
        return isBaseModel;
    }

    /**
     * Parse out all model classes
     *
     * @param clazz class， this not list ,set ,array .
     * @return all model classes
     */
    public static List<Class> parseAllModelClass(Class clazz) {
        LinkedList<Class> modelClassList = new LinkedList<>();
        LinkedList<Class> noneModelClassList = new LinkedList<>();
        try {
            parseAllModelClass(clazz, modelClassList, noneModelClassList);
        } catch (Exception e) {
            throw new ParseModelException("parse model class is error", e);
        }
        return modelClassList;
    }

    private static void parseAllModelClass(Class clazz, List<Class> modelClassList, List<Class> noneModelClass) throws Exception {

        boolean isModelClass = isBaseModel(clazz);
        if (isModelClass) {
            if (!modelClassList.contains(clazz)) {
                modelClassList.add(clazz);
                parseModelClassFromFields(clazz, modelClassList, noneModelClass);
            }
        } else {
            parseModelClassFromFields(clazz, modelClassList, noneModelClass);
        }
    }

    private static void parseModelClassFromFields(Class clazz, List<Class> modelClassList, List<Class> noneModelClass) throws Exception {
        Set<Field> fields = getFields(clazz);
        for (Field field : fields) {
            if (isPrimitive(field)) {
                continue;
            }
            boolean fieldIsModelClass = isBaseModel(field);
            if (fieldIsModelClass) {
                Class modelClass = getModelClass(field);
                parseAllModelClass(modelClass, modelClassList, noneModelClass);
            } else {
                Class genericityClass = getGenericityClass(field);
                if (!noneModelClass.contains(genericityClass)) {
                    noneModelClass.add(genericityClass);
                    parseAllModelClass(genericityClass, modelClassList, noneModelClass);
                }
            }
        }
    }

    public static boolean isPrimitive(Field field) {
        Class<?> type = field.getType();
        if (type.isPrimitive()) {
            return true;
        } else {
            try {
                getGenericityClass(field);
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    public static Class getGenericityClass(Field fd) throws Exception {
        return getModelClass(fd);
    }


    /**
     * Resolve generics Get the class object of Model
     * Usually used in combination with {@code Utils.isBaseModel(fd))
     *
     * @param fd
     * @return
     * @throws Exception
     */
    public static Class getModelClass(Field fd) throws Exception {

        Class<?> type2 = fd.getType();
        if (type2.isArray()) {
            String typeName = fd.getGenericType().getTypeName();
            //If the typeName is a primitive type, an error will be reported here
            type2 = Class.forName(typeName.substring(0, typeName.length() - 2));
        } else if (List.class.isAssignableFrom(type2)) {
            type2 = getGenericityClassOfCollect(fd);
        } else if (Set.class.isAssignableFrom(type2)) {
            type2 = getGenericityClassOfCollect(fd);
        }

        return type2;
    }

    /**
     * Get the generic class of the collection
     *
     * @param fd
     * @return
     * @throws ClassNotFoundException
     */
    public static Class getGenericityClassOfCollect(Field fd) throws ClassNotFoundException {
        try {
            ParameterizedType listGenericType = (ParameterizedType) fd.getGenericType();
            Type listActualTypeArguments = listGenericType.getActualTypeArguments()[0];
            return Class.forName(listActualTypeArguments.getTypeName());
        } catch (Exception e) {
            logger.debug(e.getMessage() + "One-to-many relationship. Note that the reference collection class must be added with a generic class. For example: List<Model>, Model cannot be omitted");
            throw e;
        }

    }

}
