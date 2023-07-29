package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.SQL;

import javax.sql.DataSource;
import java.util.List;

/**
 * @description:
 * @author: create by cmj on 2023/7/29 18:48
 */
public class BriefQueryExecutor<T> implements QueryExecutor<T> {

    DataSource dataSource;

    Class<T> modelClass;

    public BriefQueryExecutor(DataSource dataSource, Class modelClass) {
        this.dataSource = dataSource;
        this.modelClass = modelClass;
    }

    @Override
    public T query(SQL sql) {
        return null;
    }

    @Override
    public List<T> queryList(SQL sql) {
        return null;
    }
}
