package com.javaoffers.brief.modelhelper.fun.crud.delete;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

public interface DeleteFun<M, C extends GetterFun<M, Object>, V> {

    /**
     * sql 语句： where
     * @return
     */
    public DeleteWhereFun<M, C, V> where();

}
