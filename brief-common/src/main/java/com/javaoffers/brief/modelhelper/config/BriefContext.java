package com.javaoffers.brief.modelhelper.config;

import com.javaoffers.brief.modelhelper.utils.ReflectionUtils;

/**
 * brief context . 用于初始化化brief启动前的必要信息.
 *
 * @author mingJie
 */
public class BriefContext {

    //brief的配置信息,存在默认配置+用户配置(用户可自定义brief提供的配置功能).
    private final BriefProperties briefProperties = new BriefProperties();

    //是否开启加密.
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

    /**
     * 通过{@code BriefPropertiesLoader 加载配置到 briefProperties}
     * 只需要实现接口{@code BriefPropertiesLoader} 即可. 子类会被调用执行.
     */
    private void initBriefPropertiesLoader() {


    }
}
