package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.anno.EnumValue;
import com.javaoffers.brief.modelhelper.exception.EnumValueException;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: EnumValueUtils
 * @author: create by cmj on 2023/1/2 12:34
 */
public class EnumValueUtils {

    private static final SoftCache<Class, Field> SOFT_CACHE = SoftCache.getInstance();

    /**
     * Get marked @ Enum Value annotation properties. And there can be only one.
     * @param desClass enum class
     * @return field
     */
    public static Field getEnumValueFields(Class desClass) {
        Field field = SOFT_CACHE.get(desClass);
        //Allow the size of 0, but fields cannot be null
        if(field != null){
            return field;
        }
        Field[] declaredFields = desClass.getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) {
            return null;
        }
        List<Field> fields = new LinkedList<>();
        for (Field f : declaredFields) {
            EnumValue enumValueAnno = f.getDeclaredAnnotation(EnumValue.class);
            if (enumValueAnno != null) {
                fields.add(f);
            }
        }
        if(fields.size() == 0){
            return null;
        }
        if(fields.size() > 1){
            throw new EnumValueException("@ Enum Value cannot be in the same enumeration class " +
                    "on multiple attributes used at the same time. Only mark in one of " +
                    "the attributes and have unique characteristics");
        }

        Field enumValueField = fields.get(0);
        enumValueField.setAccessible(true);
        SOFT_CACHE.put(desClass,enumValueField);
        return enumValueField;
    }

    /**
     * No @EnumValue attribute will be ordinal as default values
     * @param eun enum
     * @return enumValue
     */
    public static Object getEnumValue(Enum eun){
        Field enumValueField = getEnumValueFields(eun.getClass());
        if(enumValueField == null){
            //No @EnumValue attribute will be ordinal as default values
            return eun.ordinal();
        }else{
            try {
                return enumValueField.get(eun);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
