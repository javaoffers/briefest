package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.SQL;

import java.util.List;

/**
 * @description: query
 * @author: create by cmj on 2023/7/29 18:26
 */
public interface QueryExecutor<T> {

    T query(SQL sql);

    List<T> queryList(SQL sql);

}
