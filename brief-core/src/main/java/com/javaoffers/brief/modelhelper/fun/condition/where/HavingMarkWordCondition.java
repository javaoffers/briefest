package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.IgnoreAndOrWordCondition;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:25
 */
public  class HavingMarkWordCondition<V> extends WhereOnCondition implements Condition, IgnoreAndOrWordCondition {

    public HavingMarkWordCondition() {
        super();
        cleanAndOrTag();
    }

    @Override
    public String getSql() {
        return ConditionTag.HAVING.getTag() + " 1=1 ";
    }


}
