package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.filter.ChainFilter;
import com.javaoffers.brief.modelhelper.filter.Filter;
import com.javaoffers.brief.modelhelper.filter.JqlChainFilter;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.utils.ReflectionUtils;
import com.javaoffers.brief.modelhelper.utils.Utils;
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
    private volatile List<ChainFilter> jqlChainFilterList = new ArrayList<>();
    private volatile long showLogTime = 10;
    private volatile JdbcExecutorFactory jdbcExecutorFactory;
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
        this.initJqlFilters();
        this.initShowLogTime();
        this.initJdbcExecutorFactory();
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

    //加载自定义jdbc处理器.
    public SmartBriefProperties setJdbcExecutorFactory(String jdbcExecutorFactoryClassName) {
        put(ConfigPropertiesConstants.JDBC_EXECUTOR_FACTORY, jdbcExecutorFactoryClassName);
        return this;
    }

    //慢sql打印时间
    public SmartBriefProperties setSlowSqlLogTime(String slowLogTime) {
        put(ConfigPropertiesConstants.SLOW_LOG_TIME, slowLogTime);
        return this;
    }

    public JdbcExecutorFactory getJdbcExecutorFactory() {
        return jdbcExecutorFactory;
    }

    /**
     * 获取慢sql日志时间
     *
     * @return
     */
    public long getSlowSqlTimeThreshold() {
        return showLogTime;
    }

    /**
     * 获取jql过滤器
     *
     * @return
     */
    public List<ChainFilter> getJqlFilters() {
        return jqlChainFilterList;
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

    //初始化jdbc执行工厂
    public void initJdbcExecutorFactory() {
        String jdbcExecutorFactoryClassName = bp.getProperty(ConfigPropertiesConstants.JDBC_EXECUTOR_FACTORY,
                "com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutorFactory");
        try {
            Class<?> jef = Class.forName(jdbcExecutorFactoryClassName);
            jdbcExecutorFactory = (JdbcExecutorFactory) jef.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BriefException(e.getMessage());
        }
    }

    //初始化慢sql时间
    public void initShowLogTime() {
        String mls = bp.getProperty(ConfigPropertiesConstants.SLOW_LOG_TIME);
        if (StringUtils.isNotBlank(mls)) {
            showLogTime = Long.parseLong(mls.trim());
        }
    }

    //初始化jql过滤器
    public void initJqlFilters() {
        //加载环境变量中的.因此客户可以自定义指定. 多个用逗号分割
        List<ChainFilter> jqlChainFilterList = new ArrayList<>();
        String jqlFilters = bp.getProperty(ConfigPropertiesConstants.JQL_FILTER);
        if (jqlFilters != null) {

            String[] jqlFilterArray = jqlFilters.replaceAll(" ", "").split(",");
            for (String jqlFilterClassName : jqlFilterArray) {
                try {
                    Class<?> jqlFilterClass = Class.forName(jqlFilterClassName);
                    if(JqlChainFilter.class.isAssignableFrom(jqlFilterClass)){
                        ChainFilter jqlChainFilter = (ChainFilter) jqlFilterClass.newInstance();
                        jqlChainFilterList.add(jqlChainFilter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //加载内部的
        Set<Class<? extends JqlChainFilter>> childs = ReflectionUtils.getChilds(JqlChainFilter.class);
        if (CollectionUtils.isNotEmpty(childs)) {
            for (Class clazz : childs) {
                try {
                    ChainFilter o1 = (ChainFilter) clazz.newInstance();
                    jqlChainFilterList.add(o1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.jqlChainFilterList = jqlChainFilterList;
    }
}
