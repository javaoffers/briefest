package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.sharding.context.ShardingStrategyContext;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/2
 */
public interface ShardingProcessor {
    /**
     * process WhereCondition.
     * @param context
     */
    public void processWhere(ShardingStrategyContext context);

    /**
     * process ColValueCondition.
     * @param context
     */
    public void processInsert(ShardingStrategyContext context);

    /**
     * process InsertAllColValueCondition
     * @param context
     */
    public void processInsertALL(ShardingStrategyContext context, Condition condition);

}
