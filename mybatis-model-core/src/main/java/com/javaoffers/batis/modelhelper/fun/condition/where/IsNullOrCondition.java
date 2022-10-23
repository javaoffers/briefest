package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.where.WhereOnCondition;

import java.util.Collections;
import java.util.Map;

public class IsNullOrCondition<V> extends WhereOnCondition<V> {

    public IsNullOrCondition(GetterFun colName, ConditionTag tag) {
        super(colName, null, tag);
    }

    @Override
    public String getSql() {
        return getColName() + getTag().getTag();
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }
}
