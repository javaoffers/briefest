package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @Description: select where 条件
 * @Auther: create by cmj on 2022/5/4 21:37
 */
public interface WhereSelectFun<M,V> extends
        WhereFun<M, GetterFun<M,V>,V, WhereSelectFun<M,V>> ,
        GroupFun<M,GetterFun<M,V>,V, HavingPendingFun<M,GetterFun<M,V>,V, ?>>,
        OrderFun<M, GetterFun<M,V>,V, WhereSelectFun<M,V>>,
        LimitFun<M, WhereSelectFun<M,V>>,
        ExecutFun<M> {
}
