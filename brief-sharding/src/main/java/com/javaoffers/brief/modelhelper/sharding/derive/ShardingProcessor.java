package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/2
 */
public interface ShardingProcessor {
    /**
     * 处理eq condition
     * @param conditionContext
     * @param condition
     */
    public void processEq(ConditionContext conditionContext, Condition condition);
}
