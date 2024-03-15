package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.filter.ChainFilter;
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
    default BriefProperties put(String key, String value){return this;};

    /**
     * 获取jdbc执行工厂.
     * @return
     */
    public JdbcExecutorFactory getJdbcExecutorFactory();

    /**
     * 是否要打印sql
     * @return
     */
    public boolean isPrintSql();

    /**
     * 是否打印sql耗时
     * @return
     */
    public boolean isPrintSqlCost();

    /**
     * 获取链过滤器.
     * @return
     */
    public List<ChainFilter> getJqlFilters();

    /**
     * 获取慢sql时间阈值, 单位是毫秒.
     * @return
     */
    public long getSlowSqlTimeThreshold();

    /**
     * 刷新完成创建.
     */
    public void fresh();

}
