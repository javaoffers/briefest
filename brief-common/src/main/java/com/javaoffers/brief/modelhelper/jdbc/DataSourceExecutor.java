package com.javaoffers.brief.modelhelper.jdbc;

import java.sql.Connection;

/**
 * @description:
 * @author: create by cmj on 2023/7/30 10:22
 */
public interface DataSourceExecutor {
    Connection getConnection();
}
