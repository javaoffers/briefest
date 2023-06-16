package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.ConverDescriptor;
import com.javaoffers.brief.modelhelper.core.Register;
import com.javaoffers.brief.modelhelper.exception.BaseException;
import com.javaoffers.brief.modelhelper.consistant.ModelConsistants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * @Description: Date 以及Date 子类互相转换
 *  Date (java.util)
 *     Time (java.sql)
 *     Timestamp (java.sql)
 *     Date (java.sql)
 * @Auther: create by cmj on 2021/12/16 10:43
 */
public class DateOne2DateTwoConvert extends AbstractConver<Date, Date>{

    Class targetClass;
    Constructor declaredConstructor;

    @Override
    public Date convert(Date date) {
        try {
            return (Date)declaredConstructor.newInstance(date.getTime());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new BaseException(date.getClass().getName()+"  cant convert to "+targetClass.getName());
    }

    @Override
    public void register(Register register) {
        Class[] utilDate = ModelConsistants.UTIL_DATE;
        Class[] utilDate2 = ModelConsistants.UTIL_DATE;
        for(Class d : utilDate){
            for(Class d2 : utilDate2){
                DateOne2DateTwoConvert convert = new DateOne2DateTwoConvert();
                convert.setTargetClass(d2);
                try {
                    Constructor declaredConstructor = d2.getDeclaredConstructor(long.class);
                    convert.setDeclaredConstructor(declaredConstructor);
                    declaredConstructor.setAccessible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
                register.registerConvert(new ConverDescriptor(d,d2),convert);
            }
        }
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public void setDeclaredConstructor(Constructor declaredConstructor) {
        this.declaredConstructor = declaredConstructor;
    }
}
