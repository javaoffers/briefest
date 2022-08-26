package com.javaoffers.batis.modelhelper.fun.crud.update;

import com.javaoffers.batis.modelhelper.fun.GetterFun;

public interface OneUpdateCol<M, C extends GetterFun<M, Object>, V> {

    /**
     * updated field.
     * @param col colName
     * @param value value
     * @return this
     */
    OneUpdateFun<M,C,V> col(C col, V value);

    /**
     * updated field.
     * @param condition true will update this col
     * @param col colName
     * @param value value
     * @return this
     */
    OneUpdateFun<M,C,V> col(boolean condition, C col, V value);

}
