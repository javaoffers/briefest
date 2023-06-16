package com.javaoffers.brief.modelhelper.filter;

/**
 * @description:
 * @author: create by cmj on 2023/3/25 20:43
 */
public interface Filter<B,T> {
    B filter (T t);
}
