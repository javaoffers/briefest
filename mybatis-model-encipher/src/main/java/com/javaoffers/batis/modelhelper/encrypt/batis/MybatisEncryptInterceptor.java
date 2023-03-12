package com.javaoffers.batis.modelhelper.encrypt.batis;



import com.javaoffers.batis.modelhelper.encrypt.SqlAesProcessor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * 拦截sql. 这时的sql中的mybatis表达式已经被替换成占位符号.
 * @author mingJie
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class MybatisEncryptInterceptor implements Interceptor {

    //AES加密处理器
    private List<SqlAesProcessor> sqlAesProcessors;

    public MybatisEncryptInterceptor (List<SqlAesProcessor> sqlAesProcessors){
        //log.info("batis初始化Aes加解密 : {}", sqlAesProcessors);
        this.sqlAesProcessors = sqlAesProcessors;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof StatementHandler) {
            if ("prepare".equals(invocation.getMethod().getName()))
                return invokeStatementHandlerPrepare(invocation);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
       //ignore
    }

    private Object invokeStatementHandlerPrepare(Invocation invocation) throws Exception {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        // aes 加密解析sql
        for(SqlAesProcessor sqlAesProcessor : sqlAesProcessors){
            sql = sqlAesProcessor.parseSql(sql);
        }
        //通过反射回写
        Field sqlNodeField = boundSql.getClass().getDeclaredField("sql");
        sqlNodeField.setAccessible(true);
        sqlNodeField.set(boundSql, sql);
        return invocation.proceed();
    }
}
