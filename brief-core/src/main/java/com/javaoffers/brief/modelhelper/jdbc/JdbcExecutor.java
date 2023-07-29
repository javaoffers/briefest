package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.SQL;

import javax.sql.DataSource;
import java.util.List;

/**
 * @description:
 * @author: create by cmj on 2023/7/29 17:14
 */
public interface JdbcExecutor<T> {

    Id save(SQL sql);

    List<Id> batchSave(SQL sql);

    int modify(SQL sql);

    int batchModify(SQL sql);

    T query(SQL sql);

    List<T> queryList(SQL sql);

}
