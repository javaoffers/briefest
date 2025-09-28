package com.javaoffers.brief.modelhelper.sharding;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/9/28
 */
public class ShardingLimitWordCondition extends LimitWordCondition {

    LimitWordCondition limitWordCondition;

    public ShardingLimitWordCondition(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    public ShardingLimitWordCondition(LimitWordCondition limitWordCondition) {
        super(1, limitWordCondition.pageNum() * limitWordCondition.pageSize());
        this.limitWordCondition = limitWordCondition;
    }

    @Override
    public void setHeadCondition(HeadCondition headCondition) {
        limitWordCondition.setHeadCondition(headCondition);
    }

    @Override
    public String getSql() {
        return limitWordCondition.getSql();
    }

    @Override
    public ConditionTag getTag() {
        return limitWordCondition.getTag();
    }

    @Override
    public String cleanLimit(String limitSql) {
        return limitWordCondition.cleanLimit(limitSql);
    }
}
