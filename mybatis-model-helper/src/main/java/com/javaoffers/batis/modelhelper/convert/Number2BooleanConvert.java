package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.consistant.ModelConsistants;
import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.Register;

import java.sql.Date;

public class Number2BooleanConvert extends AbstractConver<Number, Boolean> {
    @Override
    public Boolean convert(Number number) {
        return number==null || number.longValue()<1 ? false : true;
    }

    @Override
    public void register(Register register) {
        super.register(register);
        Class[] baseNumberClass = ModelConsistants.baseNumberClass;
        for(Class numClazz : baseNumberClass){
            ConverDescriptor converDescriptor = new ConverDescriptor(numClazz, Boolean.class);
            register.registerConvert(converDescriptor,this);
        }

    }
}
