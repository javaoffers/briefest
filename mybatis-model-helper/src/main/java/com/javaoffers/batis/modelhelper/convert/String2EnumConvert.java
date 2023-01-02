package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.anno.EnumValue;
import com.javaoffers.batis.modelhelper.consistant.ModelConsistants;
import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.core.Register;
import com.javaoffers.batis.modelhelper.utils.EnumValueUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * create by cmj.
 * Note that enum conversion can only support one type. It cannot support multiple types.
 */
public class String2EnumConvert extends AbstractConver<String, Enum> {
    @Override
    public Enum convert(String str) {
        Class desClass = ConvertRegisterSelectorDelegate.getProcessingConvertDesClass();
        Object[] enumConstants = desClass.getEnumConstants();
        if (StringUtils.isNotBlank(str) && enumConstants != null) {
            List<Field> enumValueFields = EnumValueUtils.getEnumValueFields(desClass);
            if (enumValueFields == null || enumValueFields.size() == 0) {
                for (Object o : enumConstants) {
                    Enum enu = (Enum) o;
                    //Can't take the name here, No @EnumValue attribute will be ordinal as default values
                    if (str.equals(String.valueOf(enu.ordinal()))) {
                        return enu;
                    }
                }
            } else {
                for (Field field : enumValueFields) {
                    field.setAccessible(true);
                    for (Object o : enumConstants) {
                        try {
                            Object enumValue = field.get(o);
                            if (str.equals(valueOf(enumValue))) {
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

    private String valueOf(Object obj){
        return (obj == null) ? "" : obj.toString();
    }

}
