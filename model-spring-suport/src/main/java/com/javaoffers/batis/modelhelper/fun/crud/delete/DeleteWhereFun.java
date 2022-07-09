package com.javaoffers.batis.modelhelper.fun.crud.delete;

import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;

/**
 * @Description:
 * @Auther: create by cmj on 2022/7/10 01:21
 */
public interface DeleteWhereFun<M,C,V> extends WhereFun<M, C, V, DeleteWhereFun<M,C,V>>, ExecutOneFun<Integer> {
}
