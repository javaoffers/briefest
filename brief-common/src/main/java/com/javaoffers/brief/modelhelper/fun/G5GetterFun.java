package com.javaoffers.brief.modelhelper.fun;

import java.io.Serializable;

/**
 * create by cmj 表示： getter方法
 * @param <A>
 * @param <B>
 */
@FunctionalInterface
public interface G5GetterFun<A ,B> extends Serializable, GGetterFun<A ,B>{
    B reply(A a);
}