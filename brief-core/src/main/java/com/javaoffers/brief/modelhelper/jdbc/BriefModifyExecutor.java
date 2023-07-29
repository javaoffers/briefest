package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.SQL;

import javax.sql.DataSource;

/**
 * @description: modify
 * @author: create by cmj on 2023/7/29 18:46
 */
public class BriefModifyExecutor implements ModifyExecutor {

    DataSource dataSource;

    public BriefModifyExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int modify(SQL sql) {
        return 0;
    }

    @Override
    public int batchModify(SQL sql) {
        return 0;
    }
}
