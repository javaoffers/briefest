package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.consistant.ModelConsistants;
import com.javaoffers.brief.modelhelper.core.ConverDescriptor;
import com.javaoffers.brief.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.brief.modelhelper.core.Register;
import com.javaoffers.brief.modelhelper.utils.EnumValueUtils;

import java.lang.reflect.Field;

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
            Field enumValueField = EnumValueUtils.getEnumValueFields(desClass);

            if (enumValueField == null) {
                if (enumConstants.length > number.intValue()) {
                    return (Enum) enumConstants[number.intValue()];
                }
            } else {
                    for (Object o : enumConstants) {
                        try {
                            Object enumValue = enumValueField.get(o);
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
