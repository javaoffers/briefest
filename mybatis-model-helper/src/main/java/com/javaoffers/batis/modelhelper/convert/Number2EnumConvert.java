package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.anno.EnumValue;
import com.javaoffers.batis.modelhelper.consistant.ModelConsistants;
import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.core.Register;
import com.javaoffers.batis.modelhelper.utils.EnumValueUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * create by cmj.
 *
 */
public class Number2EnumConvert extends AbstractConver<Number, Enum> {
    @Override
    public Enum convert(Number number) {
        Class desClass = ConvertRegisterSelectorDelegate.getProcessingConvertDesClass();
        Object[] enumConstants = desClass.getEnumConstants();
        if (number != null && enumConstants != null) {
            List<Field> declaredFields = EnumValueUtils.getEnumValueFields(desClass);

            if (declaredFields.size() == 0) {
                if (enumConstants.length > number.intValue()) {
                    return (Enum) enumConstants[number.intValue()];
                }
            } else {
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    for (Object o : enumConstants) {
                        try {
                            Object enumValue = field.get(o);
                            if (number.equals(enumValue)) {
                                return (Enum) o;
                            } else if (enumValue != null
                                    && String.valueOf(enumValue).equals(number.toString())) {
                                return (Enum) o;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void register(Register register) {
        super.register(register);
        Class[] baseNumberClass = ModelConsistants.baseNumberClass;
        for (Class numClazz : baseNumberClass) {
            ConverDescriptor converDescriptor = new ConverDescriptor(numClazz, Enum.class);
            register.registerConvert(converDescriptor, this);
        }
    }

}
