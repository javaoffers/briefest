package com.javaoffers.batis.modelhelper.fun;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * head condition
 * @author mingJie
 */
public class HeadCondition implements Condition {
    private JdbcTemplate template;

    private AtomicLong nextTag = new AtomicLong(0);

    public HeadCondition(JdbcTemplate template) {
        this.template = template;
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Override
    public ConditionTag getConditionTag() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSql() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getParams() {
        throw new UnsupportedOperationException();
    }

    public String getNextTag(){
        return nextTag.getAndIncrement() + "";
    }

    public long getNextLong(){
        return nextTag.getAndIncrement();
    }

    public static Connection getNewConnection(HeadCondition condition) {
        Connection connection = null;
        try {
            connection = condition.getTemplate().getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
