package com.javaoffers.brief.modelhelper.utils;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface CglibProxyProcess {
    /**
     * @param proxyObject proxyObject
     * @param obj realObject
     * @param method proxyMethod
     * @param args args
     * @param proxy call invoke real methods
     * @return
     * @throws Throwable
     */
    public Object intercept(Object proxyObject, Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable;
}
