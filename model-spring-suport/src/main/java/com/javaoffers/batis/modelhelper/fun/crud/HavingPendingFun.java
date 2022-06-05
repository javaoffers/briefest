package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ExecutFun;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 18:18
 */
public interface HavingPendingFun<M,C, V, R extends HavingFun> extends ExecutFun<M> {
    HavingFun<M,C,V,R> having();

}
