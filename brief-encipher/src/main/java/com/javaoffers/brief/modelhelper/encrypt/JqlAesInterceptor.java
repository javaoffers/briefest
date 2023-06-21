package com.javaoffers.brief.modelhelper.encrypt;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.exception.SqlParseException;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public class JqlAesInterceptor implements JqlInterceptor {

    private static volatile List<SqlAesProcessorImpl> sqlAesProcessors;

    public synchronized void setSqlAesProcessors(List<SqlAesProcessorImpl> sqlAesProcessors){
        if(JqlAesInterceptor.sqlAesProcessors == null){
            JqlAesInterceptor.sqlAesProcessors = Collections.unmodifiableList(sqlAesProcessors);
        }
    }

    @Override
    public void handler(BaseSQLInfo baseSQLInfo) {
        if(CollectionUtils.isNotEmpty(sqlAesProcessors) && baseSQLInfo != null && StringUtils.isNotBlank(baseSQLInfo.getSql())){
            for(SqlAesProcessorImpl sqlAesProcessor : sqlAesProcessors){
                try {
                    String newSql = sqlAesProcessor.parseSql(baseSQLInfo.getSql());
                    baseSQLInfo.resetSql(newSql);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new SqlParseException("sql encrypt error : " + e.getMessage());
                }
            }
        }
    }
}
