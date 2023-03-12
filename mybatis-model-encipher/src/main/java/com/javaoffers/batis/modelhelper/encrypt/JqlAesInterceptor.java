package com.javaoffers.batis.modelhelper.encrypt;

import com.javaoffers.batis.modelhelper.core.BaseSQLInfo;
import com.javaoffers.batis.modelhelper.exception.SqlParseException;
import com.javaoffers.batis.modelhelper.interceptor.JqlInterceptor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
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
