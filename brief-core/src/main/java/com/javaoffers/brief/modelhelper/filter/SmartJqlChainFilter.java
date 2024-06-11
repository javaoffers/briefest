package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.anno.derive.JsonColumn;
import com.javaoffers.brief.modelhelper.anno.derive.flag.Version;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.log.JqlLogger;
import com.javaoffers.brief.modelhelper.utils.*;

import java.util.List;
import java.util.Map;

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
        JqlMetaInfo jqlMetaInfo = chain.getJqlMetaInfo();
        TableInfo tableInfo = jqlMetaInfo.getTableInfo();
        DBType dbType = tableInfo.getDbType();
        jqlMetaInfo.getParams()
                .parallelStream()
                .flatMap(stringObjectMap -> stringObjectMap.entrySet().stream())
                .forEach(entry ->{
                    entry.setValue(dbType.processingTranslation(jqlMetaInfo,
                            entry.getKey(),entry.getValue()));
        });
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
