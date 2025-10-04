package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.condition.where.WhereCondition;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/8
 */
public interface ShardingTableStrategy<T> {
    /**
     * 分表策略：通常用于精确匹配：{@link com.javaoffers.brief.modelhelper.fun.ConditionTag#EQ}
     * @param condition where condition
     * @param orgTableName org table name
     * @return  table name, not null
     */
    default String shardingExactly(ShardingParams<T> shardingParams){
        return shardingParams.getTableName();
    };

    /**
     * 分表策略：通常用于范围匹配：{@link com.javaoffers.brief.modelhelper.fun.ConditionTag },
     * 不包含EQ,都会走这里.
     * @param condition where condition
     * @param orgTableName org table name
     * @return list table names . not empty
     */
    default Set<String> shardingRange(ShardingParams<T> shardingParams){
        return Collections.emptySet();
    };

    /**
     * 在sharding table 前执行。比如是否需要新建表.
     * @param condition where condition
     * @param orgTableName org table name
     */
    default void shardingBefore(ShardingParams<T> shardingParams){
        //Check if the table exists, and if not, you can create a new one
    }
}
