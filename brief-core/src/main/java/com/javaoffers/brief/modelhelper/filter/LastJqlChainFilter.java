package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.core.CrudSQLStatement;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.SmartSQLInfo;
import com.javaoffers.brief.modelhelper.exception.UpdateFieldsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handle update actions. last Filter. Optimize insertion and updates
 *
 * @author cao ming jie create by 2025/10/1
 */
public class LastJqlChainFilter implements JqlExecutorFilter{
    @Override
    public Object filter(JqlExecutorChain chain) {
        JqlMetaInfo jqlMetaInfo = chain.getJqlMetaInfo();
        SmartSQLInfo smartSQLInfo = (SmartSQLInfo)jqlMetaInfo.getSqlStatement();
        List<CrudSQLStatement> sqlStatements = smartSQLInfo.getSqlStatements();;
        switch (jqlMetaInfo.getOperate()){
            case INSERT:
                List<Id> list = new ArrayList<>();
                sqlStatements.forEach(sqlStatement -> {
                    jqlMetaInfo.setSqlStatement(sqlStatement);
                    list.addAll((List)chain.doChain());
                });
                return list;
            case UPDATE:
                if(sqlStatements.size() == 1){
                    return chain.doChain();
                }
                //多个可进行优化
                HashMap<String, List<Map<String, Object>>> sqlbatch = new HashMap<>();
                for(CrudSQLStatement sqlStatement : sqlStatements){
                    String sql = sqlStatement.getSql();
                    List<Map<String, Object>> params = sqlStatement.getParams();
                    List<Map<String, Object>> paramBatch = sqlbatch.get(sql);
                    if(paramBatch == null){
                        paramBatch = new LinkedList<Map<String, Object>>();
                        sqlbatch.put(sql, paramBatch);
                    }
                    paramBatch.addAll(params);
                }
                if(sqlbatch.size() == 0){
                    //Even npdate Null must be at least one update field
                    throw new UpdateFieldsException("Update fields must be specified." +
                            "Even npdate Null must be at least one update field");
                }
                AtomicInteger count = new AtomicInteger();
                sqlbatch.forEach((sql, params) ->{
                    CrudSQLStatement sqlStatement = CrudSQLStatement.builder().status(true).sql(sql).params(params).build();
                    jqlMetaInfo.setSqlStatement(sqlStatement);
                    Integer integer = (Integer) chain.doChain();
                    count.addAndGet(integer);
                });
                return count.get();
        }
        return chain.doChain();
    }

    @Override
    public int orderId() {
        return JqlExecutorFilter.MAX_ORDER;
    }
}
