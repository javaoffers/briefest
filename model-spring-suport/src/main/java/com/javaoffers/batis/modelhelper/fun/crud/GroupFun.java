package com.javaoffers.batis.modelhelper.fun.crud;

/**
 * @Description: 支持分组.
 * @Auther: create by cmj on 2022/6/5 18:10
 */
public interface GroupFun <M,C, V, R extends HavingPendingFun<M,C, V,?>> {
    R groupBy(C... c);

}
