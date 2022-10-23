package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.Collections;
import java.util.Map;

/**
 * 这的数据前部为空实现，只是用于做标记
 */
public class AddPatchMarkCondition  implements Condition {

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.BLANK;
    }

    @Override
    public String getSql() {
        return ConditionTag.BLANK.getTag();
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.emptyMap();
    }

}
