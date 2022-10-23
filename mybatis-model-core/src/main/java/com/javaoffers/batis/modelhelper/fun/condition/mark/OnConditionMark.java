package com.javaoffers.batis.modelhelper.fun.condition.mark;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: on标记
 * @Auther: create by cmj on 2022/5/22 16:18
 */
public class OnConditionMark implements Condition {
    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.ON;
    }

    @Override
    public String getSql() {
        return ConditionTag.ON.getTag();
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }
}
