package com.javaoffers.brief.modelhelper.fun;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/9
 */
public interface ShardingCondition extends Condition{
    /**
     * 支持sharding table
     * @param tableName
     */
    void shardingTableName(String tableName);

    /**
     * 获取表名
     * @return
     */
    String getTableName();

    /**
     * 获取sharding状态
     * @return
     */
    boolean isDone();

    /**
     * 克隆一份新的数据，
     * @param tableName 新数据的新表名
     * @return
     */
    ShardingCondition clone(String tableName);
}
