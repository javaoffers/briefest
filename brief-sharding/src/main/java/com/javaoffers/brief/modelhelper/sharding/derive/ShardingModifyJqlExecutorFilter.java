package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.core.CrudSQLStatement;
import com.javaoffers.brief.modelhelper.core.SmartSQLInfo;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.List;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/10/1
 */
public class ShardingModifyJqlExecutorFilter implements JqlExecutorFilter {
    @Override
    public Object filter(JqlExecutorChain chain) {
        JqlMetaInfo jqlMetaInfo = chain.getJqlMetaInfo();
        BaseSQLStatement baseSQLStatement = jqlMetaInfo.getSqlStatement();
        if(baseSQLStatement instanceof SmartSQLInfo){
            SmartSQLInfo smartSQLInfo = (SmartSQLInfo) baseSQLStatement;
            if(smartSQLInfo.getHeadCondition() != null && smartSQLInfo.getHeadCondition().isSharding()){
                JqlMetaInfo.Operate operate = jqlMetaInfo.getOperate();
                switch (operate) {
                    case INSERT:
                    case UPDATE:
                    case DELETE:
                        List<CrudSQLStatement> sqlStatements = smartSQLInfo.getSqlStatements();
                        TableInfo tableInfo = chain.getTableInfo();
                        String tableName = tableInfo.getTableName();
                        for (CrudSQLStatement sqlStatement : sqlStatements) {
                            String sql = sqlStatement.getSql();
                            //为了兼容多种数据库，因此把表的别名去掉,
                            // delete from user_2023_07 user 变为 delete from user_2023_07 包括where条件中的
                            sqlStatement.setSql(sql.replaceAll(tableName+"\\.", "")
                                    .replaceAll(" "+tableName+" ", " "));
                        }

                }
            }
        }

        return chain.doChain();
    }

    @Override
    public int orderId() {
        return JqlExecutorFilter.MIN_ORDER - 10;
    }
}
