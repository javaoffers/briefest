package com.javaoffers.brief.modelhelper.jdbc;

import javax.sql.DataSource;

/**
 * @author mingJie
 */
public class BriefJdbcExecutorFactory implements JdbcExecutorFactory {

    public static final BriefJdbcExecutorFactory instance = new BriefJdbcExecutorFactory();

    @Override
    public <T> JdbcExecutor<T> createJdbcExecutor(DataSource dataSource, Class<T> modelClass) {
        return new BriefJdbcExecutor<T>(dataSource, modelClass);
    }
}
