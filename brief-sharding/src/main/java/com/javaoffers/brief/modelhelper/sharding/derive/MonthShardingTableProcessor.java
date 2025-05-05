package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;

/**
 * 按照月进行sharding.
 *
 * @author cao ming jie create by 2025/5/2
 */
public final class MonthShardingTableProcessor implements ShardingProcessor{

    @Override
    public void processEq(ConditionContext conditionContext, Condition condition) {

    }
}
