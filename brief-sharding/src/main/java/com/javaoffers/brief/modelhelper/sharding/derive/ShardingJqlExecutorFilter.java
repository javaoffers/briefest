package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.core.CrudSQLStatement;
import com.javaoffers.brief.modelhelper.core.SmartSQLInfo;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;

import java.util.List;
import java.util.function.Consumer;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/9/28
 */
public class ShardingJqlExecutorFilter implements JqlExecutorFilter {
    @Override
    public Object filter(JqlExecutorChain jqlExecutorChain) {
        JqlMetaInfo jqlMetaInfo = jqlExecutorChain.getJqlMetaInfo();
        BaseSQLStatement sqlStatement = jqlMetaInfo.getSqlStatement();
        if(sqlStatement instanceof SmartSQLInfo){
            SmartSQLInfo moreSQLInfo = (SmartSQLInfo) sqlStatement;
            if(moreSQLInfo.getHeadCondition().isSharding()
                    && jqlMetaInfo.getOperate()== JqlMetaInfo.Operate.QUERY){
                return ShardingQueryParse.shardingQuery(jqlExecutorChain, moreSQLInfo);
            }
        }

        //非sharding业务
        Object object = jqlExecutorChain.doChain();

        return object;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.MAX_VALUE;
    }
}
