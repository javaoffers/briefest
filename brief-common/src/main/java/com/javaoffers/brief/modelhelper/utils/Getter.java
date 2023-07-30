package com.javaoffers.brief.modelhelper.utils;

/**
 * @description:
 * @author: create by cmj on 2023/7/30 23:53
 */
@FunctionalInterface
public interface Getter<T,U> {
    U getter(T t);
}
