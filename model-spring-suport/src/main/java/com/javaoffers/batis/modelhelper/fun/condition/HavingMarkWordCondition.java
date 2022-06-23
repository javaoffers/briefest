package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:25
 */
public  class HavingMarkWordCondition<V> extends WhereOnCondition implements Condition, IgnoreAndOrWordCondition {

    public HavingMarkWordCondition() {
    }

    @Override
    public String getSql() {
        return ConditionTag.HAVING.getTag() + " 1=1 ";
    }
}
