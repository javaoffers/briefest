package com.javaoffers.brief.modelhelper.jdbc.spring;

import com.javaoffers.brief.modelhelper.exception.ConnectionException;
import com.javaoffers.brief.modelhelper.exception.SqlParseException;
import com.javaoffers.brief.modelhelper.jdbc.BriefSaveExecutor;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author mingJie
 */
public class SpringSaveExecutor extends BriefSaveExecutor {

    DataSource dataSource;

    public SpringSaveExecutor(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() {
        try {
            //由spring管理获取连接
            Connection con = DataSourceUtils.getConnection(this.dataSource);
            return con;
        }catch (CannotGetJdbcConnectionException e){
            e.printStackTrace();
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public void closeConnection(Connection connection, boolean isClose) {
        //交给spring管理是否需要释放这个连接
        DataSourceUtils.releaseConnection(connection, this.dataSource);
    }
}
