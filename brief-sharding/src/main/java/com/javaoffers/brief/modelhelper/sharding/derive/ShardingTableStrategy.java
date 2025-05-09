package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.condition.where.WhereCondition;

import java.util.List;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/8
 */
public interface ShardingTableStrategy {
    /**
     * 分表策略
     * @param condition where condition
     * @param orgTableName org table name
     * @return list table names
     */
    List<String> sharding(WhereCondition condition, String orgTableName);
}
