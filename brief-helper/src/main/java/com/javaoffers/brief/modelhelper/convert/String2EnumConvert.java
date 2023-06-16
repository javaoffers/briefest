package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.brief.modelhelper.utils.EnumValueUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * create by cmj.
 *
 */
public class String2EnumConvert extends AbstractConver<String, Enum> {
    @Override
    public Enum convert(String str) {
        Class desClass = ConvertRegisterSelectorDelegate.getProcessingConvertDesClass();
        Object[] enumConstants = desClass.getEnumConstants();
        if (StringUtils.isNotBlank(str) && enumConstants != null) {
            Field enumValueField = EnumValueUtils.getEnumValueFields(desClass);
            if (enumValueField == null) {
                for (Object o : enumConstants) {
                    Enum enu = (Enum) o;
                    //Can't take the name here, No @EnumValue attribute will be ordinal as default values
                    if (str.equals(String.valueOf(enu.ordinal()))) {
                        return enu;
                    }
                }
            } else {
                    for (Object o : enumConstants) {
                        try {
                            Object enumValue = enumValueField.get(o);
                            if (str.equals(valueOf(enumValue))) {
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

    private String valueOf(Object obj){
        return (obj == null) ? "" : obj.toString();
    }

}
