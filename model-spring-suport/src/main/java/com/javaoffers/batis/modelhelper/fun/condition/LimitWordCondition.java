package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: exists sql 语句
 * @Auther: create by cmj on 2022/5/3 02:54
 */
public class LimitWordCondition<V> extends WhereOnCondition<V> implements IgnoreAndOrWordCondition {

    private int pageNum;

    private int pageSize;

    private int startIndex;

    private int len;

    public LimitWordCondition(int pageNum, int pageSize) {
        if(pageNum < 1){
            pageNum = 1;
        }
        if(pageSize < 1){
            pageSize = 1;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startIndex = (pageNum-1) * pageSize;
        this.len = pageSize;
    }

    @Override
    public String getSql() {
        return getTag().getTag() +" "+this.startIndex +", "+ this.len;
    }

    @Override
    public ConditionTag getTag() {
        return ConditionTag.LIMIT;
    }
}
