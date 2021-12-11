package com.javaoffers.base.batis.convert;

import com.javaoffers.base.batis.consistant.ModelConsistants;
import com.javaoffers.base.batis.core.ConverDescriptor;
import com.javaoffers.base.batis.core.Register;

public class Number2StringConvert extends AbstractConver<Number,String> {

    @Override
    public String convert(Number number) {
        return number.toString();
    }

    @Override
    public void register(Register register) {
        Class[] baseNumberClass = ModelConsistants.baseNumberClass;
        for(Class src : baseNumberClass){
            register.registerConvert(new ConverDescriptor(src,String.class),this);
        }
    }
}
