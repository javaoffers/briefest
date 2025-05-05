package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.context.DeriveInfoLoader;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.lang.reflect.Field;

/**
 * 加载sharding衍生策略.
 *
 * @author cao ming jie create by 2025/5/2
 */
public class ShardingDeriveInfoLoader implements DeriveInfoLoader {
    @Override
    public void loadDeriveInfo(TableInfo tableInfo, Field colF, String colName) {
        ShardingStrategy declaredAnnotation = colF.getDeclaredAnnotation(ShardingStrategy.class);
        if(declaredAnnotation != null) {
            ShardingDeriveInfo shardingDeriveInfo = new ShardingDeriveInfo(colName, colF, declaredAnnotation);
            tableInfo.putDeriveColName(ShardingStrategyMark.SHARDING_TABLE_STRATEGY, shardingDeriveInfo);
        }
    }
}
