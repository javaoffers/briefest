package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.SQL;

import javax.sql.DataSource;
import java.util.List;

/**
 * @description:
 * @author: create by cmj on 2023/7/29 18:16
 */
public class BriefSaveExecutor implements SaveExecutor {

    DataSource dataSource;

    public BriefSaveExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Id save(SQL sql) {
        return null;
    }

    @Override
    public List<Id> batchSave(SQL sql) {
        return null;
    }
}
