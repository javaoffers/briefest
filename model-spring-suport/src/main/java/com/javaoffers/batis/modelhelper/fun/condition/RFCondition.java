package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: 左右括号
 * @Auther: create by cmj on 2022/6/19 02:11
 */
public class RFCondition extends WhereOnCondition<String> implements IgnoreAndOrCondition {

    private ConditionTag tag;

    public RFCondition(ConditionTag tag) {
        this.tag = tag;
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
