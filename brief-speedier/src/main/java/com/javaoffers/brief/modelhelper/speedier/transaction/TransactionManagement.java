package com.javaoffers.brief.modelhelper.speedier.transaction;

import com.javaoffers.brief.modelhelper.speedier.BriefSpeedierDataSource;

import javax.sql.DataSource;

/**
 * affairs management
 * @author mingJie
 */
public class TransactionManagement {
    /**
     * The data source.
     */
    private DataSource dataSource;

    public TransactionManagement(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
