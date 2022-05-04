package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereSelectFunImpl;

/**
 * @Description:
 * @Auther: create by cmj on 2022/5/4 21:37
 */
public interface WhereSelectFun<M,V> extends  WhereFun<M, GetterFun<M,V>,V, WhereSelectFun<M,V>> , ExecutFun<M> {
}
