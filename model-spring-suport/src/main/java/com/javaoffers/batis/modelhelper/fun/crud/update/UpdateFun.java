package com.javaoffers.batis.modelhelper.fun.crud.update;

import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @author create by cmj on 2022-06-29
 */
public interface UpdateFun<M, C extends GetterFun<M, Object>, V> {

    /**
     * uodate include null col
     * @return
     */
    SmartUpdateFun<M,C,V> updateNull();

    /**
     * update exclude null col
     * @return
     */
    SmartUpdateFun<M,C,V> npdateNull();
}
