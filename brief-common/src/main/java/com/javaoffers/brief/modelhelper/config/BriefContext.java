package com.javaoffers.brief.modelhelper.config;

import com.javaoffers.brief.modelhelper.utils.ReflectionUtils;

/**
 * brief context . 用于初始化化brief启动前的必要信息.
 *
 * @author mingJie
 */
public class BriefContext {

    private final BriefProperties briefProperties = new BriefProperties();

    public volatile boolean encryptState = false;

    public BriefContext() { }

    public BriefProperties getBriefProperties() {
        return briefProperties;
    }



    //刷新配置信息
    public void freshAll() {



        briefProperties.initJqlFilters();
        briefProperties.initShowLogTime();
        briefProperties.initJdbcExecutorFactory();
        briefProperties.initIsPrintSql();
        briefProperties.initIsPrintSqlCost();
        initBriefPropertiesLoader();//执行加载器
    }

    private void initBriefPropertiesLoader() {


    }
}
