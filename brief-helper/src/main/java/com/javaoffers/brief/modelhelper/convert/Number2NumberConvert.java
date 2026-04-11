package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.consistant.ModelConsistants;
import com.javaoffers.brief.modelhelper.core.ConverDescriptor;
import com.javaoffers.brief.modelhelper.core.Register;
import com.javaoffers.brief.modelhelper.utils.Getter;
import com.javaoffers.brief.modelhelper.utils.LambdaCreateUtils;
import com.javaoffers.brief.modelhelper.utils.Utils;

import java.lang.reflect.Method;

public class Number2NumberConvert extends AbstractConver<Number,Number> {

    Getter<Object, Object> getter;

    @Override
    public Number convert(Number number) {
        try {
            return (Number)getter.getter(number);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void register(Register register) {
        Class[] baseNumberClass = ModelConsistants.baseNumberClass;
        for(Class src: baseNumberClass){
            for (Class pclass : baseNumberClass){
                try {
                    if(src.equals(pclass)){
                        continue;
                    }
                    Number2NumberConvert convert = new Number2NumberConvert();
                    Class pclassP = ModelConsistants.numberPrimitivesMapping.get(pclass);
                    Method method = Number.class.getDeclaredMethod(pclassP.getSimpleName() + "Value");
                    Getter<Object, Object> getter = LambdaCreateUtils.createGetter(method);
                    convert.getter = getter;
                    register.registerConvert(new ConverDescriptor(src,pclass),convert);
                }catch (Exception e){
                    e.printStackTrace();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
