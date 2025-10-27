package com.javaoffers.brief.modelhelper.utils;


import java.io.Serializable;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        // 创建一个实际的方法来访问字段，然后使用方法引用
        MethodHandle getter;
        String getterName = getGetterName(field);
        try {
            // 为字段生成一个getter方法
            Method getterMethod = field.getDeclaringClass().getMethod(getterName);
            getter = lookup.unreflect(getterMethod);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(getterName);
        }

        // 然后使用这个方法的MethodHandle创建lambda
        final CallSite site = LambdaMetafactory.altMetafactory(
                lookup,
                "getter",
                MethodType.methodType(Getter.class),
                MethodType.methodType(field.getType().isPrimitive() ? field.getType() : Object.class, Object.class),
                getter,
                getter.type(),
                LambdaMetafactory.FLAG_SERIALIZABLE,
                1, //指示生成的Lambda对象应该是可序列化的。
                Serializable.class
        );
        return (Getter<C, V>) site.getTarget().invoke();
    }

    // 辅助方法：生成getter方法名
    private static String getGetterName(Field field) {
        String prefix = field.getType() == boolean.class ? "is" : "get";
        String fieldName = field.getName();
        return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

}
