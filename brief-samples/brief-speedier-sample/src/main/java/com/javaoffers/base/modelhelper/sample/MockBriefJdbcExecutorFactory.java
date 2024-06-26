package com.javaoffers.base.modelhelper.sample;

import com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutor;
import com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutor;

import javax.sql.DataSource;

public class MockBriefJdbcExecutorFactory extends BriefJdbcExecutorFactory {

    @Override
    public <T> JdbcExecutor<T> createJdbcExecutor(DataSource dataSource, Class<T> modelClass) {
        return new MockBriefJdbcExecutor();
    }
}
