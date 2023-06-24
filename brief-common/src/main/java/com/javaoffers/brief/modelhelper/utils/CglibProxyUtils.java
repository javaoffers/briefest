package com.javaoffers.brief.modelhelper.utils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * create by cmj
 */
public class CglibProxyUtils {

    public static <T> T createProxy(T t, CglibProxyProcess cglibProxyProcess) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(t.getClass());
        enhancer.setCallback(new MethodInterceptor(){
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return cglibProxyProcess.intercept(obj, t, method, args, proxy);
            }
        });
        return (T) enhancer.create();
    }
}
