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
     * shardingStrategyContext
     */
    public void processWhere(ShardingStrategyContext shardingStrategyContext);

    public void processInsert(ShardingStrategyContext shardingStrategyContext);

}
