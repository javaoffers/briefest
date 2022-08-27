package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ExecutFun;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/12 18:40
 */
public interface LeftHavingPendingFun<M,M2,C,C2,V,V2>  extends ExecutFun<M> {
    HavingFun<M,C,V,?> having();



}