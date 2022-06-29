package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.util.List;

/**
 * 单插入.
 * @author create by cmj
 */
public interface OneInsertFun <M, C extends GetterFun<M, Object>, V> extends ExecutOneFun<List<Id>> {

    /**
     * 指定具体的字段插入
     *
     * @param col   字段
     * @param value 值
     * @return this
     */
    OneInsertFun<M, C, V> col(C col, V value);

    /**
     * 指定具体的字段插入
     *
     * @param condition 如果为true则插入
     * @param col       字段
     * @param value     值
     * @return this
     */
    OneInsertFun<M, C, V> col(boolean condition, C col, V value);


}
