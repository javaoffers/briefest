package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.condition.where.WhereCondition;

import java.util.Collections;
import java.util.List;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/8
 */
public interface ShardingTableStrategy {
    /**
     * 分表策略：通常用于精确匹配：{@link com.javaoffers.brief.modelhelper.fun.ConditionTag#EQ}
     * @param condition where condition
     * @param orgTableName org table name
     * @return  table name, not null
     */
    default String shardingExactly(WhereCondition condition, String orgTableName){
        return orgTableName;
    };

    /**
     * 分表策略：通常用于范围匹配：{@link com.javaoffers.brief.modelhelper.fun.ConditionTag },
     * 不包含EQ,都会走这里.
     * @param condition where condition
     * @param orgTableName org table name
     * @return list table names . not empty
     */
    default List<String> shardingRange(WhereCondition condition, String orgTableName){
        return Collections.emptyList();
    };

    /**
     * 在sharding table 前执行。比如是否需要新建表.
     * @param condition where condition
     * @param orgTableName org table name
     */
    default void shardingBefore(WhereCondition condition, String orgTableName){
        //
    }
}
