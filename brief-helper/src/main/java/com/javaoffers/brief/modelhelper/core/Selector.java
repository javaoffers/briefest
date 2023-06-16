package com.javaoffers.brief.modelhelper.core;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/7 18:23
 */
public interface Selector<V,T> {

    /**
     * 根据T 选择V
     * @return
     */
    V selector(T t);
}
