package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.log.JqlLogger;

import java.util.List;

/**
 * @description: SmartJqlChainFilter
 * <p>
 * Record the slow SQL, Secure Dynamic Updates when call setter methods of model.
 * </p>
 * @author: create by cmj on 2023/6/1 20:31
 */
public class SmartJqlChainFilter implements JqlExecutorFilter {

    @Override
    public Object filter(JqlExecutorChain chain) {
        long cost = 0;
        long startTime = System.currentTimeMillis();
        Object o = chain.doChain();
        long endTime = System.currentTimeMillis();
        if ((cost = endTime - startTime) > JqlLogger.time) {
            if(o instanceof List){
                JqlLogger.infoSqlCost("COST TIME : {}, SIZE: {}", cost, ((List) o).size());
            }else{
                JqlLogger.infoSqlCost("COST TIME : {}", cost);
            }
        }
        return o;
    }


}
