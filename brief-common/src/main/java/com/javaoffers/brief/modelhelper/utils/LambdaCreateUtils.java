package com.javaoffers.brief.modelhelper.utils;


import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * @author mingJie
 */
public class LambdaCreateUtils {

    static final MethodHandles.Lookup lookup = MethodHandles.lookup();

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


}
