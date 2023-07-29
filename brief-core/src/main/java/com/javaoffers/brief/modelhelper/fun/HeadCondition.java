package com.javaoffers.brief.modelhelper.fun;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * head condition
 * @author mingJie
 */
public class HeadCondition implements Condition {

    private DataSource dataSource;

    private AtomicLong nextTag = new AtomicLong(0);

    private Class modelClass;

    public HeadCondition(DataSource dataSource, Class modelClass) {
        this.dataSource = dataSource;
        this.modelClass = modelClass;
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

    public HeadCondition clone(){
        return new HeadCondition(this.dataSource, this.modelClass);
    }

    public DataSource getDataSource(){
       return this.dataSource;
    }

    public Class getModelClass() {
        return modelClass;
    }
}
