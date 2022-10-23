package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.condition.IgnoreAndOrWordCondition;

/**
 * @Description: 右括号
 * @Auther: create by cmj on 2022/6/19 02:11
 */
public class RFWordCondition extends WhereOnCondition<String> implements IgnoreAndOrWordCondition {

    private ConditionTag tag;

    public RFWordCondition() {
        cleanAndOrTag();
    }

    public RFWordCondition(ConditionTag tag) {
        this.tag = tag;
        cleanAndOrTag();
    }

    @Override
    public String getSql() {
        return tag.getTag();
    }

    @Override
    public ConditionTag getTag() {
        return tag;
    }
}
