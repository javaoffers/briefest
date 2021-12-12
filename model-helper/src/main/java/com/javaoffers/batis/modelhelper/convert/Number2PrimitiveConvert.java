package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.consistant.ModelConsistants;
import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.Register;

import java.lang.reflect.Method;

public class Number2PrimitiveConvert extends AbstractConver<Number,Number> {

    Method method;

    @Override
    public Number convert(Number number) {
        try {
            return (Number)method.invoke(number);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void register(Register register) {
        Class[] baseNumberClass = ModelConsistants.baseNumberClass;
        for(Class des: baseNumberClass){
            try {
                Number2PrimitiveConvert convert = new Number2PrimitiveConvert();
                Class pclass = ModelConsistants.numberPrimitivesMapping.get(des);
                Method method = Number.class.getDeclaredMethod(pclass.getSimpleName() + "Value");
                method.setAccessible(true);
                convert.method = method;
                register.registerConvert(new ConverDescriptor(Number.class,des),convert);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
