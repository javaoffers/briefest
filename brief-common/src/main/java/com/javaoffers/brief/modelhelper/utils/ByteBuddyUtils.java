package com.javaoffers.brief.modelhelper.utils;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.List;

/**
 * byteBuddy tool. This is only allowed for architects.
 * @author create by cmj on 2022-06-22 00:24:42
 */
public class ByteBuddyUtils {

    /**
     * Generate subclasses based on class，
     * @param clazz Can be interface class
     * @return Subclass
     */
    public static Object makeObject(Class clazz){
        Class<?> dynamicType = new ByteBuddy()
                .subclass(clazz) //define whose subclass
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
     * Generates subclasses based on class and executes the provided executor. This method is only allowed for architects.
     * @param clazz Can be interface class
     * @return Subclass
     */
    public static Object makeObject(Class clazz, List<DefaultClass> methodDefault){
        try {
            String name = clazz.getName()+"Jql";
            DynamicType.Builder subclass = new ByteBuddy().with(new NamingStrategy() {
                @Override
                public String subclass(TypeDescription.Generic generic) {
                    return name;
                }

                @Override
                public String redefine(TypeDescription typeDescription) {
                    return name+"Redefine";
                }

                @Override
                public String rebase(TypeDescription typeDescription) {
                    return name+"Rebase";
                }
            }).subclass(clazz);
            for(DefaultClass p : methodDefault){
                subclass = subclass.method(ElementMatchers.named(p.getMethodName()))
                        .intercept(MethodDelegation.to(p.getExecutorClass()));
            }
            //subclass.name(name);
            Class dynamicType = subclass.make()
                    .load(ByteBuddyUtils.class.getClassLoader())
                    .getLoaded();
            return dynamicType.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static class DefaultClass{
        private String methodName;
        private Class executorClass;

        public DefaultClass(String methodName, Class  executorClass) {
            this.methodName = methodName;
            this.executorClass = executorClass;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Class getExecutorClass() {
            return executorClass;
        }

        public void setExecutorClass(Class executorClass) {
            this.executorClass = executorClass;
        }
    }

    public static DefaultClass buildDefaultClass(String methodName, Class  executorClass){
        return new DefaultClass(methodName, executorClass);
    }
}
