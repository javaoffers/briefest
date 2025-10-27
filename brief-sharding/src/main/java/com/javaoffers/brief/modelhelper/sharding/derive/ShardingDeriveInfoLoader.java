package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.brief.modelhelper.anno.derive.flag.ShardingStrategyMark;
import com.javaoffers.brief.modelhelper.context.DeriveInfoLoader;
import com.javaoffers.brief.modelhelper.sharding.exception.ShardingConfigException;
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
            DeriveInfo deriveColName = tableInfo.getDeriveColName(ShardingStrategyMark.SHARDING_TABLE_STRATEGY);
            if(deriveColName != null) {
                throw new ShardingConfigException("A sharding policy can only have one field," +
                        " but now multiple fields are found from " + tableInfo.getTableName());
            }
            tableInfo.putDeriveColName(ShardingStrategyMark.SHARDING_TABLE_STRATEGY, shardingDeriveInfo);
        }
    }
}
