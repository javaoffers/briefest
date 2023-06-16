package com.javaoffers.brief.modelhelper.encrypt;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.exception.SqlParseException;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class JqlAesInterceptor implements JqlInterceptor {

    private static   List<SqlAesProcessor> sqlAesProcessors;

    public void setSqlAesProcessors(List<SqlAesProcessor> sqlAesProcessors){
        JqlAesInterceptor.sqlAesProcessors = sqlAesProcessors;
    }

    @Override
    public void handler(BaseSQLInfo baseSQLInfo) {
        if(CollectionUtils.isNotEmpty(sqlAesProcessors) && baseSQLInfo != null && StringUtils.isNotBlank(baseSQLInfo.getSql())){
            for(SqlAesProcessor sqlAesProcessor : sqlAesProcessors){
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
