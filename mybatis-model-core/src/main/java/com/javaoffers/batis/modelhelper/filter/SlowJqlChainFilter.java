package com.javaoffers.batis.modelhelper.filter;

import com.javaoffers.batis.modelhelper.log.slowsql.CostTimeLogger;

import java.util.function.Supplier;

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
