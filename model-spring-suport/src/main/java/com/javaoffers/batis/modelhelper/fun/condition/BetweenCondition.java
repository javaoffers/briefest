package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: between 语句
 * @Auther: create by cmj on 2022/5/2 17:11
 */
public class BetweenCondition<V> extends WhereOnCondition<V> {

    private V end;

    private BetweenCondition(String colName, V start, ConditionTag tag) {
        super(colName, start, tag);
    }

    public BetweenCondition(String colName, V start, V end, ConditionTag tag) {
        super(colName, start, tag);
        this.end = end;
    }
}
