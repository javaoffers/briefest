package com.javaoffers.brief.modelhelper.utils;


import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author mingJie
 */
public class LambdaCreateUtils {

    static final MethodHandles.Lookup lookup = MethodHandles.lookup();

    /**
     * setter方法
     * @param field
     * @param <C>
     * @param <V>
     * @return
     * @throws Throwable
     */
    public static <C, V> Setter<C, V> createSetter(Field field) throws Throwable {
        final MethodHandle setter = lookup.unreflectSetter(field);
        MethodType type = setter.type();
        if(field.getType().isPrimitive())
            type = type.wrap().changeReturnType(void.class);
        final CallSite site = LambdaMetafactory.metafactory(lookup,
                "setter", MethodType.methodType(Setter.class, MethodHandle.class),
                type.erase(), MethodHandles.exactInvoker(setter.type()), type);
        return (Setter<C, V>)site.getTarget().invokeExact(setter);
    }

    /**
     * 无参构造函数
     * @param modelClass
     * @param <T>
     * @return
     * @throws Throwable
     */
    public static <T> Newc<T> createConstructor( Class<T> modelClass ) throws Throwable {
        Constructor<T> constructor = modelClass.getConstructor();
        MethodHandle cmh = lookup.unreflectConstructor(constructor);
        MethodType type = cmh.type();
        final CallSite site = LambdaMetafactory.metafactory(lookup,
                "newc", MethodType.methodType(Newc.class, MethodHandle.class),
                type.erase(), MethodHandles.exactInvoker(cmh.type()), type);

        return (Newc<T>) site.getTarget().invokeExact(cmh);

    }

    /**
     * getter方法
     */
    public static <C, V> Getter<C, V> createGetter(
             Field field) throws Throwable {
        final MethodHandle getter = lookup.unreflectGetter(field);
        MethodType type = getter.type();
        if(field.getType().isPrimitive())
            type = type.wrap().changeReturnType(void.class);
        final CallSite site = LambdaMetafactory.metafactory(lookup,
                "getter", MethodType.methodType(Getter.class, MethodHandle.class),
                type.erase(), MethodHandles.exactInvoker(getter.type()), type);
        return (Getter<C, V>)site.getTarget().invokeExact(getter);
    }


}
