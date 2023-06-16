package com.javaoffers.brief.modelhelper.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description: jdk代理类工具
 * @author: create by cmj on 2023/5/28 00:09
 */
public class JdkProxyUtils {

    //创建代理对象
    public static Object createProxy(Object instance, ProxyProcess proxyProcess) {

       return Proxy.newProxyInstance(JdkProxyUtils.class.getClassLoader(), instance.getClass().getInterfaces(), new InvocationHandler() {

            private Object instance ;//目标对象

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return proxyProcess.doInvoke(instance, method, args);
            }
        });
    }


}
