package com.javaoffers.batis.modelhelper.fun.condition.update;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.Map;

/**
 * 这的数据前部为空实现，只是用于做标记
 */
public class AddPatchMarkCondition  implements Condition {

    private static Object EMPTY = null;

    @Override
    public ConditionTag getConditionTag() {
        return (ConditionTag)EMPTY;
    }

    @Override
    public String getSql() {
        return (String) EMPTY;
    }

    @Override
    public Map<String, Object> getParams() {
        return (Map<String, Object>) EMPTY;
    }
}
