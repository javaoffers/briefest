package com.javaoffers.brief.modelhelper.jdbc.spring;

import com.javaoffers.brief.modelhelper.exception.SqlParseException;
import com.javaoffers.brief.modelhelper.jdbc.BriefQueryExecutor;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author mingJie
 */
public class SpringQueryExecutor<T> extends BriefQueryExecutor<T> {
    DataSource dataSource;

    public SpringQueryExecutor(DataSource dataSource, Class modelClass) {
        super(dataSource, modelClass);
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() {
        try {
            Connection con = DataSourceUtils.getConnection(this.dataSource);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        }
    }

    @Override
    public void closeConnection(Connection connection, boolean isClose) {
        DataSourceUtils.releaseConnection(connection, this.dataSource);
    }
}
