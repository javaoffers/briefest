package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.exception.JdbcException;

import java.sql.Connection;

/**
 * @description:
 * @author: create by cmj on 2023/7/30 10:22
 */
public interface DataSourceExecutor {
    Connection getConnection();

    default void closeConnection(Connection connection, boolean isClose){
        if(isClose){
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
                throw new JdbcException(e.getMessage());
            }
        }
    }
}
