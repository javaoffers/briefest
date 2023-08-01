package com.javaoffers.brief.modelhelper.jdbc;

import javax.sql.DataSource;

/**
 * @author mingJie
 */
public interface JdbcExecutorFactory {

    <T> JdbcExecutor<T> createJdbcExecutor(DataSource dataSource, Class<T> modelClass);
}
