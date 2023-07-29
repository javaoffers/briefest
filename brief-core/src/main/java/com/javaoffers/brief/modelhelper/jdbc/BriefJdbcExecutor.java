package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.SQL;

import javax.sql.DataSource;
import java.util.List;

/**
 * @description: 执行jdbc操作
 * @author: create by cmj on 2023/7/29 17:47
 */
public class BriefJdbcExecutor<T> implements JdbcExecutor<T> {

    DataSource dataSource;

    Class<T> modelClass;

    public BriefJdbcExecutor(DataSource dataSource, Class<T> modelClass) {
        this.dataSource = dataSource;
        this.modelClass = modelClass;
    }

    @Override
    public Id save(SQL sql) {
        return new BriefSaveExecutor(dataSource).save(sql);
    }

    @Override
    public List<Id> batchSave(SQL sql) {
        return new BriefSaveExecutor(dataSource).batchSave(sql);
    }

    @Override
    public int modify(SQL sql) {
        return new BriefModifyExecutor(dataSource).modify(sql);
    }

    @Override
    public int batchModify(SQL sql) {
        return new BriefModifyExecutor(dataSource).batchModify(sql);
    }

    @Override
    public T query(SQL sql) {
        return new BriefQueryExecutor<T>(dataSource, modelClass).query(sql);
    }

    @Override
    public List<T> queryList(SQL sql) {
        return new BriefQueryExecutor<T>(dataSource, modelClass).queryList(sql);
    }
}
