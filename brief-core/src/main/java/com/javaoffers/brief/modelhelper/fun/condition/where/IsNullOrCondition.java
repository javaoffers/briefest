package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;

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
