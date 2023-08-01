package com.javaoffers.brief.modelhelper.jdbc.spring;

import com.javaoffers.brief.modelhelper.exception.SqlParseException;
import com.javaoffers.brief.modelhelper.jdbc.BriefModifyExecutor;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author mingJie
 */
public class SpringModifyExecutor extends BriefModifyExecutor {
    DataSource dataSource;

    public SpringModifyExecutor(DataSource dataSource) {
        super(dataSource);
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
