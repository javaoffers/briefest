package com.javaoffers.batis.modelhelper.utils;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 00:40
 */
@FunctionalInterface
public interface ProxyProcess {
    /**
     *
     * @param instance 真是对象， 非代理对象
     * @param method 调用的方法
     * @param args 调用参数
     * @return
     */
    public Object doInvoke(Object instance, Method method, Object[] args);
}
