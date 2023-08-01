package com.javaoffers.brief.modelhelper.jdbc.spring;

import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutor;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;

import javax.sql.DataSource;

/**
 * @author mingJie
 */
public class SpringJdbcExecutorFactory implements JdbcExecutorFactory {
    @Override
    public <T> JdbcExecutor<T> createJdbcExecutor(DataSource dataSource, Class<T> modelClass) {
        return new SpringJdbcExecutor(dataSource, modelClass);
    }


}
