package com.javaoffers.batis.modelhelper.utils;

import lombok.Data;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.List;

/**
 * byteBuddy工具。此只允许给架构设计者使用。
 * @author create by cmj on 2022-06-22 00:24:42
 */
public class ByteBuddyUtils {

    /**
     * 根据class生成子类，
     * @param clazz 可以为接口class
     * @return 子类
     */
    public static Object makeObject(Class clazz){
        Class<?> dynamicType = new ByteBuddy()
                .subclass(clazz) //定义谁的子类
                .make()
                .load(clazz.getClassLoader())
                .getLoaded();
        try {
            return dynamicType.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据class生成子类，并执行提供的executor. 此方法只允许给架构设计者使用。
     * @param clazz 可以为接口class
     * @return 子类
     */
    public static Object makeObject(Class clazz, List<DefaultClass> methodDefault){
        try {
            DynamicType.Builder subclass = new ByteBuddy().subclass(clazz);
            for(DefaultClass p : methodDefault){
                subclass = subclass.method(ElementMatchers.named(p.getMethodName()))
                        .intercept(MethodDelegation.to(p.getExecutorClass()));
            }
            Class dynamicType = subclass.make()
                    .load(ByteBuddyUtils.class.getClassLoader())
                    .getLoaded();
            return dynamicType.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Data
    public static class DefaultClass{
        private String methodName;
        private Class executorClass;

        public DefaultClass(String methodName, Class  executorClass) {
            this.methodName = methodName;
            this.executorClass = executorClass;
        }
    }

    public static DefaultClass buildDefaultClass(String methodName, Class  executorClass){
        return new DefaultClass(methodName, executorClass);
    }
}
