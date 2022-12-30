package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.consistant.ModelConsistants;
import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.core.Register;

/**
 * create by cmj.
 * Note that enum conversion can only support one type. It cannot support multiple types.
 */
public class Number2EnumConvert extends AbstractConver<Number, Enum> {
    @Override
    public Enum convert(Number number) {
        Class desClass = ConvertRegisterSelectorDelegate.getProcessingConvertDesClass();
        Object[] enumConstants = desClass.getEnumConstants();
        if(number != null && enumConstants != null && enumConstants.length > number.intValue()){
            return (Enum) enumConstants[number.intValue()];
        }
        return null;
    }

    @Override
    public void register(Register register) {

        super.register(register);
        Class[] baseNumberClass = ModelConsistants.baseNumberClass;
        for(Class numClazz : baseNumberClass){
            ConverDescriptor converDescriptor = new ConverDescriptor(numClazz, Enum.class);
            register.registerConvert(converDescriptor,this);
        }

    }

}
