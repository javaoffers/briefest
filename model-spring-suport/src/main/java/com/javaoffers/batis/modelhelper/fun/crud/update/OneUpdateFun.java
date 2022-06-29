package com.javaoffers.batis.modelhelper.fun.crud.update;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;

import java.util.List;

/**
 * @author create by cmj on 2022-06-29
 */
public interface OneUpdateFun  <M, C extends GetterFun<M, Object>, V>  {
    /**
     * 更新的字段。
     * @param col colName
     * @param value value
     * @return this
     */
    OneUpdateFun<M,C,V> col(C col, V value);

    /**
     * 更新的字段。
     * @param condition true will update this col
     * @param col colName
     * @param value value
     * @return this
     */
    OneUpdateFun<M,C,V> col(boolean condition, C col, V value);

    /**
     * where
     * @return
     */
    WhereModifyFun<M,V> where();
}
