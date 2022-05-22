package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @Description: exists sql 语句
 * @Auther: create by cmj on 2022/5/3 02:54
 */
public class ExistsCondition<V> extends WhereOnCondition<V> {

    private String existsSql;

    public ExistsCondition(GetterFun colName, V value, ConditionTag tag) {
        super(colName, value, tag);
    }

    public ExistsCondition(String existsSql) {
        super(null, null, ConditionTag.EXISTS);
        this.existsSql = existsSql;
    }

    @Override
    public String getSql() {
        return " "+getTag().getTag()+" ( "+ this.existsSql+" ) ";
    }
}
