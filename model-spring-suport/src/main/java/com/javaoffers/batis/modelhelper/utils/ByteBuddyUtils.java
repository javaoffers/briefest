package com.javaoffers.batis.modelhelper.utils;

import lombok.Data;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.StubMethod;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Constructor;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * byteBuddy工具
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
     * 根据class生成子类，
     * @param clazz 可以为接口class
     * @return 子类
     */
    public static Object makeObject(Class clazz, List<DefaultClass> methodDefault){
        try {
            DynamicType.Builder subclass = new ByteBuddy().subclass(clazz);
            for(DefaultClass p : methodDefault){
                subclass = subclass.method(ElementMatchers.named(p.getMethodName()))
                        .intercept(MethodCall
                                .invoke(p.constructor)
                                .with(p.param));
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
        private Constructor constructor;
        private Object param;

        public DefaultClass(String methodName, Constructor constructor, Object param) {
            this.methodName = methodName;
            this.constructor = constructor;
            this.param = param;
        }
    }

    public static DefaultClass buildDefaultClass(String methodName, Constructor constructor, Object param){
        return new DefaultClass(methodName, constructor, param);
    }
}
