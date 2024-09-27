package com.javaoffers.brief.modelhelper.router;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefProperties;
import com.javaoffers.brief.modelhelper.context.DeriveProcess;
import com.javaoffers.brief.modelhelper.context.JqlInterceptor;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.mapper.BaseMapper;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import com.javaoffers.brief.modelhelper.parser.TableInfoParser;
import com.javaoffers.brief.modelhelper.router.jdbc.ShardingBriefJdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.utils.DBType;

import java.util.Collections;
import java.util.List;

public class ShardingBriefContext implements BriefContext {

    private BriefContext briefContext;

    public ShardingBriefContext(BriefContext briefContext) {
        this.briefContext = briefContext;
    }

    @Override
    public BriefProperties getBriefProperties() {
        return briefContext.getBriefProperties();
    }

    @Override
    public JdbcExecutorFactory getJdbcExecutorFactory() {
        return ShardingBriefJdbcExecutorFactory.instance;
    }

    @Override
    public <T extends BaseMapper> T getBriefMapper(Class<T> briefMapper) {
        return briefContext.getBriefMapper(briefMapper);
    }

    @Override
    public List<JqlInterceptor> getJqlInterceptors() {
        return briefContext.getJqlInterceptors();
    }

    @Override
    public List<JqlExecutorFilter> getJqlExecutorFilters() {
        return briefContext.getJqlExecutorFilters();
    }

    @Override
    public StatementParser getStatementParser(DBType dbType) {
        return briefContext.getStatementParser(dbType);
    }

    @Override
    public TableInfoParser getTableInfoParser() {
        return briefContext.getTableInfoParser();
    }

    @Override
    public List<DeriveProcess> getDeriveProcess() {
        return briefContext.getDeriveProcess();
    }

    @Override
    public void fresh() {
        briefContext.fresh();
    }
}
