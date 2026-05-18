package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: query
 * @author: create by cmj on 2023/7/29 18:26
 */
public interface QueryExecutor<T> extends DataSourceExecutor{

    T query(BaseSQLInfo sql);

    List<T> queryList(BaseSQLInfo sql);

    List<Map<String, Object>> queryMapList(BaseSQLInfo sql);

    int queryStream(BaseSQLInfo sql);
}
