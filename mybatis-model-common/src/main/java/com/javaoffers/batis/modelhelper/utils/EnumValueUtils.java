package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.anno.EnumValue;
import com.javaoffers.batis.modelhelper.exception.EnumValueException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: EnumValueUtils
 * @author: create by cmj on 2023/1/2 12:34
 */
public class EnumValueUtils {

    private static final SoftCache<Class, List<Field>> SOFT_CACHE = SoftCache.getInstance();

    /**
     * Get marked @ Enum Value annotation properties. And there can be only one.
     * @param desClass enum class
     * @return field
     */
    public static List<Field> getEnumValueFields(Class desClass) {
        List<Field> fields = SOFT_CACHE.get(desClass);
        //Allow the size of 0, but fields cannot be null
        if(fields != null){
            return fields;
        }
        Field[] declaredFields = desClass.getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) {
            return new ArrayList<>();
        }
        fields = new LinkedList<>();
        for (Field f : declaredFields) {
            EnumValue enumValueAnno = f.getDeclaredAnnotation(EnumValue.class);
            if (enumValueAnno != null) {
                fields.add(f);
            }
        }
        if(fields.size() > 1){
            throw new EnumValueException("@ Enum Value cannot be in the same enumeration class " +
                    "on multiple attributes used at the same time. Only mark in one of " +
                    "the attributes and have unique characteristics");
        }
        SOFT_CACHE.put(desClass, fields);
        return fields;
    }

    /**
     * No @EnumValue attribute will be ordinal as default values
     * @param eun enum
     * @return enumValue
     */
    public static Object getEnumValue(Enum eun){
        List<Field> enumValueFields = getEnumValueFields(eun.getClass());
        if(enumValueFields == null || enumValueFields.size() == 0){
            //No @EnumValue attribute will be ordinal as default values
            return eun.ordinal();
        }else{
            Field field = enumValueFields.get(0);
            field.setAccessible(true);
            try {
                return field.get(eun);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
