package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.utils.ReflectionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: brief 初始化配置信息.
 * @author: create by cmj on 2023/8/5 13:20
 */
public class SmartBriefProperties implements BriefProperties{

    private final Map<String, Object> properties = new ConcurrentHashMap<>();
    private final Properties bp = System.getProperties();
    private volatile long showLogTime = -1;
    private volatile boolean isPrintSql;
    private volatile boolean isPrintSqlCost;

    /**
     * 添加配置信息
     *
     * @param key
     * @param value
     * @return
     */
    public SmartBriefProperties put(String key, String value) {
        bp.setProperty(key, value);
        properties.put(key, value);
        return this;
    }

    @Override
    public void fresh() {
        this.initShowLogTime();
        this.initIsPrintSql();
        this.initIsPrintSqlCost();
    }

    /**
     * 是否打印sql
     *
     * @param isPrintSql
     */
    public SmartBriefProperties isPrintSql(String isPrintSql) {
        put(ConfigPropertiesConstants.IS_PRINT_SQL, isPrintSql.trim().toLowerCase());
        return this;
    }

    /**
     * 是否打印sql耗时
     *
     * @param isPrintSqlCost
     */
    public SmartBriefProperties isPrintSqlCost(String isPrintSqlCost) {
        put(ConfigPropertiesConstants.IS_PRINT_SQL_COST, isPrintSqlCost.trim().toLowerCase());
        return this;
    }


    //慢sql打印时间
    public SmartBriefProperties setSlowSqlLogTime(String slowLogTime) {
        put(ConfigPropertiesConstants.SLOW_LOG_TIME, slowLogTime);
        return this;
    }

    /**
     * 获取慢sql日志时间
     *
     * @return
     */
    public long getSlowSqlTimeThreshold() {
        return showLogTime;
    }


    public boolean isPrintSql() {
        return isPrintSql;
    }

    public boolean isPrintSqlCost() {
        return isPrintSqlCost;
    }

    //初始化是否打印sql
    public void initIsPrintSql() {
        isPrintSql = Boolean.parseBoolean(bp.getProperty(ConfigPropertiesConstants.IS_PRINT_SQL, "true"));
    }

    //初始化打印sql耗时
    public void initIsPrintSqlCost() {
        isPrintSqlCost = Boolean.parseBoolean(bp.getProperty(ConfigPropertiesConstants.IS_PRINT_SQL_COST, "true"));
    }


    //初始化慢sql时间
    public void initShowLogTime() {
        String mls = bp.getProperty(ConfigPropertiesConstants.SLOW_LOG_TIME);
        if (StringUtils.isNotBlank(mls)) {
            showLogTime = Long.parseLong(mls.trim());
        }
    }

}
