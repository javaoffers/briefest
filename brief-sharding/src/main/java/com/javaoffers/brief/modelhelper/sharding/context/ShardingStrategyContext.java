package com.javaoffers.brief.modelhelper.sharding.context;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereCondition;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingTableStrategy;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/9
 */
public class ShardingStrategyContext {

    ConditionContext conditionContext;

    Condition condition;

    ShardingTableStrategy shardingTableStrategy;

    private String orgTableName;

    private String colName;

    public ConditionContext getConditionContext() {
        return conditionContext;
    }

    public void setConditionContext(ConditionContext conditionContext) {
        this.conditionContext = conditionContext;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public ShardingTableStrategy getShardingTableStrategy() {
        return shardingTableStrategy;
    }

    public void setShardingTableStrategy(ShardingTableStrategy shardingTableStrategy) {
        this.shardingTableStrategy = shardingTableStrategy;
    }

    public String getOrgTableName() {
        return orgTableName;
    }

    public void setOrgTableName(String orgTableName) {
        this.orgTableName = orgTableName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}
