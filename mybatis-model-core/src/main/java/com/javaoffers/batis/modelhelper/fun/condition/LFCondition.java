package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: 左括号
 * @Auther: create by cmj on 2022/6/19 02:11
 */
public class LFCondition extends WhereOnCondition<String> {

    private ConditionTag tag;

    public LFCondition(ConditionTag tag) {
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
