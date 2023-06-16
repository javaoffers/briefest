package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.ExecutOneFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.update.SmartUpdateFun;

/**
 * @Description: where
 * @Auther: create by cmj on 2022/5/4 21:44
 */
public interface WhereModifyFun<M,V> extends  WhereFun<M, GetterFun<M,V>,V, WhereModifyFun<M,V>> , ExecutOneFun<Integer> {

    /**
     * add patch
     * @return
     */
    SmartUpdateFun<M,GetterFun<M,Object>, V> addBatch();
}
