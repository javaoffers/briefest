package com.javaoffers.brief.modelhelper.config;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.filter.Filter;
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
 * @description: brief 配置信息
 * @author: create by cmj on 2023/8/5 13:20
 */
public class BriefProperties  {
    private static final Map<String,Object> properties = new ConcurrentHashMap<>();
    private static final Properties bp = System.getProperties();
    private static volatile List<Filter> jqlChainFilterList = new ArrayList<>();
    private static volatile long showLogTime = 10;
    private static volatile JdbcExecutorFactory jdbcExecutorFactory;
    private static volatile boolean isPrintSql;
    private static volatile boolean isPrintSqlCost;

    /**
     * 添加配置信息
     * @param key
     * @param value
     * @return
     */
    public static Properties put(String key, String value){
        bp.setProperty(key, value);
        properties.put(key, value);
        return bp;
    }

    public static void setIsPrintSql(String boolStr){
        put(ConfigPropertiesConstants.IS_PRINT_SQL, boolStr.trim().toLowerCase());
        initIsPrintSql();
    }

    public static void setIsPrintSqlCost(String boolStr){
        put(ConfigPropertiesConstants.IS_PRINT_SQL_COST, boolStr.trim().toLowerCase());
        initIsPrintSqlCost();
    }

    public static void setJdbcExecutorFactory(String jdbcExecutorFactoryClassName){
        put(ConfigPropertiesConstants.JDBC_EXECUTOR_FACTORY, jdbcExecutorFactoryClassName);
        initJdbcExecutorFactory();
    }

    public static void setSlowSqlLogTime(String slowLogTime){
        put(ConfigPropertiesConstants.SLOW_LOG_TIME, slowLogTime);
        initShowLogTime();
    }

    public static JdbcExecutorFactory getJdbcExecutorFactory(){
        return jdbcExecutorFactory;
    }

    /**
     * 获取慢sql日志时间
     * @return
     */
    public static long getSlowSqlLogTime(){
        return BriefProperties.showLogTime;
    }

    /**
     * 获取jql过滤器
     * @return
     */
    public static List<Filter> getJqlFilters(){
        return BriefProperties.jqlChainFilterList;
    }

    public static boolean isIsPrintSql(){
        return BriefProperties.isPrintSql;
    }

    public static boolean isIsPrintSqlCost(){
        return BriefProperties.isPrintSql;
    }

    //刷新配置信息
    public static void freshAll(){
        initJqlFilters();
        initShowLogTime();
        initJdbcExecutorFactory();
        initIsPrintSql();
    }

    private static void initIsPrintSql() {
        isPrintSql = Boolean.parseBoolean(bp.getProperty(ConfigPropertiesConstants.IS_PRINT_SQL, "true"));
    }

    private static void initIsPrintSqlCost() {
        isPrintSqlCost = Boolean.parseBoolean(bp.getProperty(ConfigPropertiesConstants.IS_PRINT_SQL_COST, "true"));
    }

    public static void initJdbcExecutorFactory(){
        String jdbcExecutorFactoryClassName = bp.getProperty(ConfigPropertiesConstants.JDBC_EXECUTOR_FACTORY,
                "com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutorFactory");
        try {
            Class<?> jef = Class.forName(jdbcExecutorFactoryClassName);
            BriefProperties.jdbcExecutorFactory = (JdbcExecutorFactory)jef.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            throw new BriefException(e.getMessage());
        }
    }

    //初始化慢sql时间
    public static void initShowLogTime() {
        String mls = bp.getProperty(ConfigPropertiesConstants.SLOW_LOG_TIME);
        if(StringUtils.isNotBlank(mls)){
            showLogTime = Long.parseLong(mls.trim());
        }
    }

    //初始化jql过滤器
    public static void initJqlFilters() {
        List<Filter> jqlChainFilterList = new ArrayList<>();
        String jqlFilters = bp.getProperty(ConfigPropertiesConstants.JQL_FILTER);
        if(jqlFilters != null){

            String[] jqlFilterArray = jqlFilters.replaceAll(" ", "").split(",");
            for(String jqlFilterClassName : jqlFilterArray){
                try {
                    Class<?> jqlFilterClass = Class.forName(jqlFilterClassName);
                    Filter jqlChainFilter = (Filter)jqlFilterClass.newInstance();
                    jqlChainFilterList.add(jqlChainFilter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        String jqlClassName = "com.javaoffers.brief.modelhelper.filter.JqlChainFilter";
        Set<Class<? extends Filter>> childs = ReflectionUtils.getChilds(Utils.getClass(jqlClassName));
        if(CollectionUtils.isNotEmpty(childs)){
            for(Class clazz : childs){
                try {
                    Filter o1 = (Filter)clazz.newInstance();
                    jqlChainFilterList.add(o1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        BriefProperties.jqlChainFilterList = jqlChainFilterList;
    }
}
