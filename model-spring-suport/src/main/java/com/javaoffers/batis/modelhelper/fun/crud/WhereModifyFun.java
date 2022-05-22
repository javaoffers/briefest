package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @Description:
 * @Auther: create by cmj on 2022/5/4 21:44
 */
public interface WhereModifyFun<M,V> extends  WhereFun<M, GetterFun<M,V>,V, WhereModifyFun<M,V>> , ExecutOneFun<Integer> {
}
