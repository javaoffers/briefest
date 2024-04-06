package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.mapper.SmartMapperProxy;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import com.javaoffers.brief.modelhelper.parser.TableInfoParser;
import com.javaoffers.brief.modelhelper.utils.*;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * brief context . 用于初始化化brief启动前的必要信息. 是brief的上下文，代表brief的应用.
 *
 * @author mingJie
 */
public class SmartBriefContext implements BriefContext{

    //brief的配置信息,存在默认配置+用户配置(用户可自定义brief提供的配置功能).
    private SmartBriefProperties smartBriefProperties = new SmartBriefProperties();

    //数据源
    public DataSource primaryDataSource;

    //缓存BriefMapper
    private Map<Class, BriefMapper> cache = new ConcurrentHashMap<>();

    //briefProperties加载器
    private List<BriefPropertiesLoader> briefPropertiesLoaderList =
            new ArrayList<BriefPropertiesLoader>((Set)ReflectionUtils.getChildInstance(BriefPropertiesLoader.class));

    //briefContextPostProcess后置处理器
    private List<BriefContextPostProcess> briefContextPostProcessList =
            new ArrayList<BriefContextPostProcess>((Set)ReflectionUtils.getChildInstance(BriefContextPostProcess.class));

    //briefContextAware
    private List<BriefContextAware> briefContextAwareList =
            new ArrayList<BriefContextAware>((Set)ReflectionUtils.getChildInstance(BriefContextAware.class));

    //jqlChainFilterList
    private  List<JqlExecutorFilter> jqlExecutorFilters = new ArrayList<>(ReflectionUtils.getChildInstance(JqlExecutorFilter.class));

    //jqlInterceptor拦截器
    private final  ArrayList<JqlInterceptor> coreInterceptorsList = Lists.newArrayList();

    //DBType
    private final Map<DBType, StatementParser> statementParserMap = new HashMap<>();

    //SmartTableInfoParser
    private final SmartTableInfoParser smartTableInfoParser = new SmartTableInfoParser();


    public SmartBriefContext(SmartBriefProperties smartBriefProperties, DataSource dataSource) {
        this.smartBriefProperties = smartBriefProperties;
        this.primaryDataSource = dataSource;
    }

    public SmartBriefContext( DataSource dataSource) {
        this.primaryDataSource = dataSource;
    }

    public SmartBriefProperties getBriefProperties() {
        return smartBriefProperties;
    }

    @Override
    public DataSource getDataSource() {
        return this.primaryDataSource;
    }

    public JdbcExecutorFactory getJdbcExecutorFactory(){
        return BriefJdbcExecutorFactory.instance;
    }

    @Override
    public  BriefMapper getBriefMapper(Class briefMapper) {
        Assert.isTrue(BriefMapper.class.isAssignableFrom(briefMapper), briefMapper.getName() + " must be BriefMapper subclass");
        BriefMapper mapper = cache.get(briefMapper);
        if(mapper == null){
            Type modelClass = Utils.getModelClass(briefMapper);
            BriefMapper briefMapperImpl = BriefUtils.newCrudMapper(briefMapper);
            SmartMapperProxy smartMapperProxy = new SmartMapperProxy(briefMapperImpl, getDataSource(), (Class) modelClass);
            cache.putIfAbsent(briefMapper, (BriefMapper)JdkProxyUtils.createProxy(briefMapper, smartMapperProxy));
            mapper = cache.get(briefMapper);
        }
        return mapper;
    }

    @Override
    public List<JqlInterceptor> getJqlInterceptors() {
        return this.coreInterceptorsList;
    }

    public List<JqlExecutorFilter> getJqlExecutorFilters(){
        return this.jqlExecutorFilters;
    }

    @Override
    public StatementParser getStatementParser(DBType dbType) {
        return statementParserMap.get(dbType);
    }

    @Override
    public SmartTableInfoParser getTableInfoParser() {
        return smartTableInfoParser;
    }

    public Map<DBType, StatementParser> getStatementParserMap(){
        return this.statementParserMap;
    }

    @Override
    public void fresh() {
        //初始化配置信息
        initProperties();
        //执行content后置处理器
        initContextPostProcess();
        //已经可以对外提供功能了. 释放content可用.
        finish();
    }

    //初始化配置信息.
    private void initProperties() {
        //加载配置. 在briefPropertiesLoader中初始化配置信息
        for(BriefPropertiesLoader briefPropertiesLoader : briefPropertiesLoaderList){
            briefPropertiesLoader.startLoader(this.getBriefProperties());
        }
        //发布可用
        this.smartBriefProperties.fresh();

    }

    /**
     * 测试briefContext已经创建成功. 已经可以对外提供功能了.
     */
    private void finish() {
        for(BriefContextAware briefContextAware : briefContextAwareList){
            briefContextAware.setBriefContext(this);
        }
    }

    /**
     * 通过{@code BriefPropertiesLoader 加载配置到 briefProperties}
     * 只需要实现接口{@code BriefPropertiesLoader} 即可. 子类会被调用执行.
     */
    protected void initContextPostProcess() {

        //上下文后置处理器
        for(BriefContextPostProcess briefContextPostProcess : briefContextPostProcessList){
            briefContextPostProcess.postProcess(this);
        }

    }
}
