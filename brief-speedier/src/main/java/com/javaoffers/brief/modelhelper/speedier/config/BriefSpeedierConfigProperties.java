package com.javaoffers.brief.modelhelper.speedier.config;

import com.javaoffers.brief.modelhelper.context.SmartBriefProperties;

import javax.sql.DataSource;

/**
 * @author mingJie
 */
public class BriefSpeedierConfigProperties extends SmartBriefProperties {

    /**
     * 数据源
     */
    private DataSource dataSource;

    public static BriefSpeedierConfigProperties getInstance(DataSource dataSource){
        BriefSpeedierConfigProperties briefConfigProperties = new BriefSpeedierConfigProperties();
        briefConfigProperties.dataSource = dataSource;
        return briefConfigProperties;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
