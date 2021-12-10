package com.javaoffers.base.batis.convert;

import com.javaoffers.base.batis.consistant.ModelConsistants;
import com.javaoffers.base.batis.core.ConverDescriptor;
import com.javaoffers.base.batis.core.Register;

/**
 * NumberOne2NumberTwo
 */
public class NumberOne2NumberTwo extends AbstractConver<Number,Number> {


    @Override
    public Number convert(Number number) {
        double src = (double) number;
        return src;
    }

    @Override
    public void register(Register register) {
        Class[] baseNumberClass = ModelConsistants.baseNumberClass;
        for(Class src: baseNumberClass){
            for(Class des: baseNumberClass){
                register.registerConvert(new ConverDescriptor(src,des),this);
            }
        }
    }
}
