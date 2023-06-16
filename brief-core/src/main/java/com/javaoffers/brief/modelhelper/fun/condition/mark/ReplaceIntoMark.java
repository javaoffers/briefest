package com.javaoffers.brief.modelhelper.fun.condition.mark;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: create by cmj on 2022/11/19 18:22
 */
public class ReplaceIntoMark implements Condition {
    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.REPLACE_INTO;
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
