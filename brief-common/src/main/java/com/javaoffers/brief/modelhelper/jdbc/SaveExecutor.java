package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.core.Id;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: save
 * @author: create by cmj on 2023/7/29 18:26
 */
public interface SaveExecutor extends DataSourceExecutor{

    Id save(BaseSQLInfo sql);

    List<Id> batchSave(BaseSQLInfo sql) throws SQLException;

}
