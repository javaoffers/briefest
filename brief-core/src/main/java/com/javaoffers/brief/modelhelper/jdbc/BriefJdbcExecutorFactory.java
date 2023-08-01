package com.javaoffers.brief.modelhelper.jdbc;

import javax.sql.DataSource;

/**
 * @author mingJie
 */
public class BriefJdbcExecutorFactory implements JdbcExecutorFactory {
    @Override
    public <T> JdbcExecutor<T> createJdbcExecutor(DataSource dataSource, Class<T> modelClass) {
        return new BriefJdbcExecutor<T>(dataSource, modelClass);
    }
}
