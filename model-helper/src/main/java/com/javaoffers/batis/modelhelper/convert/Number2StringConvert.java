package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.consistant.ModelConsistants;
import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.Register;

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
