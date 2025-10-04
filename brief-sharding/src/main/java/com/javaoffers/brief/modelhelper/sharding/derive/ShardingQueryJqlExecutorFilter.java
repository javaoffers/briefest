package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.core.SmartSQLInfo;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/9/28
 */
public class ShardingQueryJqlExecutorFilter implements JqlExecutorFilter {

    @Override
    public Object filter(JqlExecutorChain jqlExecutorChain) {
        JqlMetaInfo jqlMetaInfo = jqlExecutorChain.getJqlMetaInfo();
        BaseSQLStatement sqlStatement = jqlMetaInfo.getSqlStatement();
        if(sqlStatement instanceof SmartSQLInfo){
            SmartSQLInfo moreSQLInfo = (SmartSQLInfo) sqlStatement;
            if(moreSQLInfo.getHeadCondition().isSharding()
                    && jqlMetaInfo.getOperate()== JqlMetaInfo.Operate.QUERY
                    // if size value is one, then as ordinary query
                    && ((SmartSQLInfo) sqlStatement).getSqlStatements().size() > 1
            ){
                return ShardingQueryParse.shardingQuery(jqlExecutorChain, moreSQLInfo);
            }
        }

        //非 sharding query 业务
        Object object = jqlExecutorChain.doChain();

        return object;
    }

    @Override
    public int orderId() {
        return JqlExecutorFilter.MIN_ORDER + 10;
    }
}
