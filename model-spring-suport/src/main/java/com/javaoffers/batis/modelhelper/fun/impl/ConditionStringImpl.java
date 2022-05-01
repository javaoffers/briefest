package com.javaoffers.batis.modelhelper.fun.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:25
 */
public abstract class ConditionStringImpl<V> implements Condition<String,V> {

    private String colName;
    private V value;

    /**
     * 获取 字段名称
     * @return
     */
    public String getColName() {
        return this.colName;
    }

    /**
     * 获取 字段值
     * @return
     */

    public V getColValue() {
        return value;
    }

    public abstract ConditionTag getConditionTag();
}
