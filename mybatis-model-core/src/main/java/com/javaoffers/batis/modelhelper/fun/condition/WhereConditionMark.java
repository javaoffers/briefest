package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: where标记
 * @Auther: create by cmj on 2022/5/22 16:18
 */
public class WhereConditionMark implements Condition {
    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.WHERE;
    }

    @Override
    public String getSql() {
        return ConditionTag.WHERE.getTag();
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }
}
