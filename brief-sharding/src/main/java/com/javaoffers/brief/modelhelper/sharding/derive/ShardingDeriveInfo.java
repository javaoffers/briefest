package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;

import java.lang.reflect.Field;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/2
 */
public class ShardingDeriveInfo extends DeriveInfo {
    private ShardingStrategy shardingStrategy;
    private ShardingTableStrategy shardingTableStrategy;
    public ShardingDeriveInfo(String colName, Field field,
                              ShardingStrategy shardingStrategy) {
        super(colName, field);
        this.shardingStrategy = shardingStrategy;
        Class<? extends ShardingTableStrategy> shardingTableStrategyClass = shardingStrategy.value();
        try {
             this.shardingTableStrategy = shardingTableStrategyClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public ShardingStrategy getShardingStrategy() {
        return shardingStrategy;
    }

    public ShardingTableStrategy getShardingTableStrategy() {
        return shardingTableStrategy;
    }
}
