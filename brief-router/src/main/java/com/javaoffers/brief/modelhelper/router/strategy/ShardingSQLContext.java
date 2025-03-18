package com.javaoffers.brief.modelhelper.router.strategy;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分表上下文信息.
 *
 * @author cao ming jie create by 2025/3/18
 */
public class ShardingSQLContext {
    private BaseSQLInfo originSQLInfo;
    private List<BaseSQLInfo> shardingSQLInfos = new ArrayList<>();

    public ShardingSQLContext(BaseSQLInfo originSQLInfo) {
        this.originSQLInfo = originSQLInfo;
        this.shardingSQLInfos.add(originSQLInfo);
    }

    public List<BaseSQLInfo> getShardingSQLInfos() {
        return shardingSQLInfos;
    }

    public BaseSQLInfo getOriginSQLInfo() {
        return originSQLInfo;
    }
}
