package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.consistant.ModelConsistants;
import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.Register;

import java.lang.reflect.Method;

/**
 *
 * PrimitiveType 2 :
 *             Byte.class,
 *             Short.class,
 *             Integer.class,
 *             Long.class,
 *             Float.class,
 *             Double.class
 *
 *             byte.class,
 *             short.class,
 *             int.class,
 *             long.class,
 *             float.class,
 *             double.class
 *
 */
public class PrimitiveNumber2PrimitiveNumberConvert extends AbstractConver<Number,Number> {

    ConverDescriptor descriptor;

    Method convertMethod;

    @Override
    public Number convert(Number number) {
        try {
           return (Number)convertMethod.invoke(number);
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public void register(Register register) {
        Class[] basePrimitiveClass = ModelConsistants.basePrimitiveClass;
        for (Class src : basePrimitiveClass) {
            for (Class des : basePrimitiveClass) {
                try {
                    ConverDescriptor descriptor = new ConverDescriptor(ModelConsistants.numberPrimitivesMapping.get(src), ModelConsistants.numberPrimitivesMapping.get(des));
                    final PrimitiveNumber2PrimitiveNumberConvert n2n = new PrimitiveNumber2PrimitiveNumberConvert();
                    n2n.setDescriptor(descriptor);
                    Method method = Number.class.getDeclaredMethod(des.getSimpleName() + "Value");
                    n2n.setConvertMethod(method);
                    method.setAccessible(true);
                    register.registerConvert(descriptor, n2n);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void setDescriptor(ConverDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public void setConvertMethod(Method convertMethod) {
        this.convertMethod = convertMethod;
    }

}
