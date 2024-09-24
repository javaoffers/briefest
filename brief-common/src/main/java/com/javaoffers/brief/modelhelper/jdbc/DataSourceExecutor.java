package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.exception.JdbcException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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

    default void closeStatement(Statement statement){
        try {
            if(statement != null && !statement.isClosed()){
                statement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new JdbcException(e.getMessage());
        }
    }

    default void closeResultSet(ResultSet resultSet){
        try {
            if(resultSet!=null && !resultSet.isClosed()){
                resultSet.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new JdbcException(e.getMessage());
        }
    }

    default void close(Connection connection, boolean isClose, Statement statement, ResultSet resultSet){
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection, isClose);
    }

    default void close(Connection connection, boolean isClose, Statement statement){
        closeStatement(statement);
        closeConnection(connection, isClose);
    }

}
