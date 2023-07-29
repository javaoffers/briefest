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

    /**
     * 保存
     * @param sql
     * @return
     */
    Id save(SQL sql);

    /**
     * 批量保存
     * @param sql
     * @return
     */
    List<Id> batchSave(SQL sql);

    /**
     * 更新
     * @param sql
     * @return
     */
    int modify(SQL sql);

    /**
     * 批量更新
     * @param sql
     * @return
     */
    int batchModify(SQL sql);

    /**
     * 查询
     * @param sql
     * @return
     */
    T query(SQL sql);

    /**
     * 查询列表
     * @param sql
     * @return
     */
    List<T> queryList(SQL sql);

}
