package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;

/**
 * @Description: exists sql 语句
 * @Auther: create by cmj on 2022/5/3 02:54
 */
public class ExistsCondition<V> extends WhereOnCondition<V> {

    public ExistsCondition(GetterFun  existsSql) {
        super(existsSql, null, ConditionTag.EXISTS);
    }

    @Override
    public String getSql() {
        return " "+getTag().getTag()+" ( "+ this.getColName()+" ) ";
    }

}
