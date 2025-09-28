package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.core.MoreSQLInfo;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/9/28
 */
public class ShardingJqlExecutorFilter implements JqlExecutorFilter {
    @Override
    public Object filter(JqlExecutorChain jqlExecutorChain) {
        JqlMetaInfo jqlMetaInfo = jqlExecutorChain.getSqlStatement();
        BaseSQLStatement sqlStatement = jqlMetaInfo.getSqlStatement();
        if(sqlStatement instanceof MoreSQLInfo){
            MoreSQLInfo moreSQLInfo = (MoreSQLInfo) sqlStatement;
            if(moreSQLInfo.getHeadCondition().isSharding()){
                //sharding 下，修改 consumer
                jqlMetaInfo.setConsumer(obj->{

                });
            }
        }
        Object object = jqlExecutorChain.doChain();

        return object;
    }
}
