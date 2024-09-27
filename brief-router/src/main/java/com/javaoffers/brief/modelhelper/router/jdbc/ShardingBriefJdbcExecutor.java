package com.javaoffers.brief.modelhelper.router.jdbc;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutor;

import javax.sql.DataSource;
import java.util.List;

/**
 * @description: 执行jdbc操作
 * @author: create by cmj on 2023/7/29 17:47
 */
public class ShardingBriefJdbcExecutor<T> implements JdbcExecutor<T> {

    DataSource dataSource;

    Class<T> modelClass;

    public ShardingBriefJdbcExecutor(DataSource dataSource, Class<T> modelClass) {
        this.dataSource = dataSource;
        this.modelClass = modelClass;
    }

    @Override
    public Id save(BaseSQLInfo sql) {
        return new ShardingBriefSaveExecutor(dataSource).save(sql);
    }

    @Override
    public List<Id> batchSave(BaseSQLInfo sql) {
        return new ShardingBriefSaveExecutor(dataSource).batchSave(sql);
    }

    @Override
    public int modify(BaseSQLInfo sql) {
        return new ShardingBriefModifyExecutor(dataSource).modify(sql);
    }

    @Override
    public int batchModify(BaseSQLInfo sql) {
        return new ShardingBriefModifyExecutor(dataSource).batchModify(sql);
    }

    @Override
    public T query(BaseSQLInfo sql) {
        return new ShardingBriefQueryExecutor<T>(dataSource, modelClass).query(sql);
    }

    @Override
    public List<T> queryList(BaseSQLInfo sql) {
        return new ShardingBriefQueryExecutor<T>(dataSource, modelClass).queryList(sql);
    }

    @Override
    public void queryStream(BaseSQLInfo sql) {
        new ShardingBriefQueryExecutor<T>(dataSource, modelClass).queryStream(sql);
    }
}
