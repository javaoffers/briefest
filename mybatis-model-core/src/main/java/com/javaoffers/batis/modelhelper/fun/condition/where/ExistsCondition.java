package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.condition.where.WhereOnCondition;

/**
 * @Description: exists sql 语句
 * @Auther: create by cmj on 2022/5/3 02:54
 */
public class ExistsCondition<V> extends WhereOnCondition<V> {

    private String existsSql;
    private ConditionTag tag;

    public ExistsCondition(String existsSql) {
        this.existsSql = existsSql;
        this.tag = ConditionTag.EXISTS;
    }

    public ExistsCondition(String existsSql, ConditionTag tag) {
        this.existsSql = existsSql;
        this.tag = tag;
    }

    @Override
    public String getSql() {
        return " "+getTag().getTag()+" ( "+ this.existsSql+" ) ";
    }

    @Override
    public ConditionTag getTag() {
        return this.getTag();
    }
}
