package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;

/**
 * @Description: between 语句
 * @Auther: create by cmj on 2022/5/2 17:11
 */
public class BetweenCondition<V> extends WhereOnCondition<V> {

    private V end;

    private String sql;

    private BetweenCondition(GetterFun colName, V start, ConditionTag tag) {
        super(colName, start, tag);
    }

    public BetweenCondition(GetterFun colName, V start, V end, ConditionTag tag) {
        super(colName, start, tag);
        this.end = end;
    }

    @Override
    public String getSql() {
        if(sql == null){
            long startIdx = getNextLong();
            long endIdx = getNextLong();
            getParams().put(startIdx+"",getValue());
            getParams().put(endIdx+"",end);
            this.sql = super.getColName() +" "
                    + getTag().getTag()
                    +" #{"+startIdx+"} and  #{"+endIdx+"} ";
        }
        return sql;
    }

    public V getEnd() {
        return end;
    }
}
