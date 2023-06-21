package com.javaoffers.brief.modelhelper.speedier.config;

import javax.sql.DataSource;

/**
 * @author mingJie
 */
public class BriefConfigProperties {

    /**
     * 数据源
     */
    private DataSource dataSource;

    public static BriefConfigProperties getInstance(DataSource dataSource){
        BriefConfigProperties briefConfigProperties = new BriefConfigProperties();
        briefConfigProperties.dataSource = dataSource;
        return briefConfigProperties;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
