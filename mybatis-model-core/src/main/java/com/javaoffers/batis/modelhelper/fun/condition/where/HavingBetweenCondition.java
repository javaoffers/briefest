package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.where.WhereOnCondition;

/**
 * @Description: between 语句
 * @Auther: create by cmj on 2022/5/2 17:11
 */
public class HavingBetweenCondition<V> extends WhereOnCondition<V> {

    private V end;

    private AggTag aggTag;

    private HavingBetweenCondition(AggTag aggTag, GetterFun colName, V start, ConditionTag tag) {
        super(colName, start, tag);
        this.aggTag = aggTag;
    }

    public HavingBetweenCondition(AggTag aggTag, GetterFun colName, V start, V end, ConditionTag tag) {
        super(colName, start, tag);
        this.end = end;
        this.aggTag = aggTag;
    }

    @Override
    public String getSql() {
        long startIdx = getNextLong();
        long endIdx = getNextLong();
        getParams().put(startIdx+"",getValue());
        getParams().put(endIdx+"",end);
        return aggTag.name() +"(" + super.getColName() +") "
                + getTag().getTag()
                +" #{"+startIdx+"} and  #{"+endIdx+"} ";
    }
}
