package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.log.slowsql.CostTimeLogger;

/**
 * @description: Record the slow SQL
 * @author: create by cmj on 2023/6/1 20:31
 */
public class SlowJqlChainFilter implements JqlChainFilter {
    @Override
    public Object filter(Chain<Object, SqlMetaInfo> chain) {
        long cost = 0;
        long startTime = System.currentTimeMillis();
        Object o = chain.doChain();
        long endTime = System.currentTimeMillis();
        if((cost = endTime - startTime) >  CostTimeLogger.time){
            CostTimeLogger.log.info("COST TIME : {}" , cost );
        }
        return  o;
    }

}
