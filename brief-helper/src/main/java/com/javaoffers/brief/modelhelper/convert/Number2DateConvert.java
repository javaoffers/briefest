package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.ConverDescriptor;
import com.javaoffers.brief.modelhelper.core.Register;
import com.javaoffers.brief.modelhelper.exception.BaseException;
import com.javaoffers.brief.modelhelper.consistant.ModelConsistants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * create by cmj
 *  number 2
 *       Date (java.util)
 *       Time (java.sql)
 *       Timestamp (java.sql)
 *       ate (java.sql)
 */
public class Number2DateConvert extends AbstractConver<Number, Date> {

    Class targetClass;
    Constructor declaredConstructor;

    @Override
    public Date convert(Number number) {
        try {
            return (Date) declaredConstructor.newInstance(number.longValue());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new BaseException(number.getClass().getName()+"  cant convert to "+targetClass.getName());
    }

    @Override
    public void register(Register register) {
        Class[] utilDate = ModelConsistants.UTIL_DATE;
        for(Class d : utilDate){
            Number2DateConvert number2DateConvert = new Number2DateConvert();
            number2DateConvert.setTargetClass(d);
            try {
                Constructor declaredConstructor = d.getDeclaredConstructor(long.class);
                declaredConstructor.setAccessible(true);
                number2DateConvert.setDeclaredConstructor(declaredConstructor);
            }catch (Exception e){
                e.printStackTrace();
            }
            register.registerConvert(new ConverDescriptor(Number.class,d),number2DateConvert);
        }
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public void setDeclaredConstructor(Constructor declaredConstructor) {
        this.declaredConstructor = declaredConstructor;
    }
}
