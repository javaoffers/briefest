package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

/**
 * @description:
 * @author: create by cmj on 2023/7/30 23:53
 */
public interface Getter<T,U> extends GetterFun {
    U getter(T t);
}
