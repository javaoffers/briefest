package com.javaoffers.brief.modelhelper.encrypt;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.exception.SqlParseException;
import com.javaoffers.brief.modelhelper.context.JqlInterceptor;
import com.javaoffers.brief.modelhelper.context.JqlInterceptorLoader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JqlAesInterceptorImpl implements JqlAesInterceptor , JqlInterceptorLoader {

    private static volatile List<SqlAesProcessorImpl> sqlAesProcessors = new ArrayList<>();

    public synchronized void setSqlAesProcessors(List<SqlAesProcessorImpl> sqlAesProcessors){
        ArrayList<SqlAesProcessorImpl> newSqlAesProcessors = new ArrayList<>(sqlAesProcessors);
        JqlAesInterceptorImpl.sqlAesProcessors = Collections.unmodifiableList(newSqlAesProcessors);
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

    @Override
    public JqlInterceptor loadJqlInterceptor() {
        return this;
    }
}
