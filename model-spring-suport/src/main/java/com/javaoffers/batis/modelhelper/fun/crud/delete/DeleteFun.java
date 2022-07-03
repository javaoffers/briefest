package com.javaoffers.batis.modelhelper.fun.crud.delete;

import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;

public interface DeleteFun<M, C extends GetterFun<M, Object>, V> {

    /**
     * sql 语句： where
     * @return
     */
    public WhereFun<M, C, V, ?> where();

}
