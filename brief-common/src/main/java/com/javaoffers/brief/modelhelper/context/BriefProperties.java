package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.filter.Filter;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;

import java.util.List;

/**
 * @description: brief 初始化配置信息.
 * @author: create by cmj on 2023/8/5 13:20
 */
public interface BriefProperties {

    /**
     * 添加配置信息
     *
     * @param key
     * @param value
     * @return
     */
    public BriefProperties put(String key, String value);

    /**
     * 获取配置Jdbc执行工厂
     * @return
     */
    public JdbcExecutorFactory getJdbcExecutorFactory();
    /**
     * 获取慢sql日志时间
     *
     * @return
     */
    public long getSlowSqlLogTime();

    /**
     * 获取jql过滤器
     *
     * @return
     */
    public List<Filter> getJqlFilters();

    /**
     * 是否打印sql
     * @return
     */
    public boolean isPrintSql();

    /**
     * 是否打印sql耗时
     * @return
     */
    public boolean isPrintSqlCost();




}
