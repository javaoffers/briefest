package com.javaoffers.batis.modelhelper.fun.condition.mark;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: create by cmj on 2022/11/19 12:30
 */
public  class OnDuplicateKeyUpdateMark implements Condition {
    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.ON_DUPLICATE_KEY_UPDATE;
    }

    @Override
    public String getSql() {
        return getConditionTag().getTag();
    }

    @Override
    public Map<String, Object> getParams() {
        return new HashMap<>();
    }
}
