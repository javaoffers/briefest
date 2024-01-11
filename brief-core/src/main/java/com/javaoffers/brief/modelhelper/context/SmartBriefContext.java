package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.jdbc.BriefTransaction;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.utils.Lists;
import com.javaoffers.brief.modelhelper.utils.ReflectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * brief context . 用于初始化化brief启动前的必要信息. 是brief的上下文，代表brief的应用.
 *
 * @author mingJie
 */
public class SmartBriefContext implements BriefContext{

    //brief的配置信息,存在默认配置+用户配置(用户可自定义brief提供的配置功能).
    private final List<BriefProperties> briefPropertiesList = new ArrayList<>() ;

    //是否开启加密.
    public volatile boolean encryptState = false;

    //数据源
    public DataSource primaryDataSource;

    //brief事务
    public BriefTransaction briefTransaction;

    //缓存BriefMapper
    private Map<Class, BriefMapper> cache = new ConcurrentHashMap<>();

    //加载器
    private List<BriefPropertiesLoader> briefPropertiesLoaderList =
            new ArrayList<BriefPropertiesLoader>((Set)ReflectionUtils.getChildInstance(BriefPropertiesLoader.class));

    //briefContext后置处理器
    private List<BriefContextPostProcess> briefContextPostProcessList =
            new ArrayList<BriefContextPostProcess>((Set)ReflectionUtils.getChildInstance(BriefContextPostProcess.class));

    //briefContextAware
    private List<BriefContextAware> briefContextAwareList =
            new ArrayList<BriefContextAware>((Set)ReflectionUtils.getChildInstance(BriefContextAware.class));

    //jql拦截器
    private final  ArrayList<JqlInterceptor> coreInterceptorsList = Lists.newArrayList();


    public SmartBriefContext(SmartBriefProperties smartBriefProperties, boolean encryptState, DataSource dataSource, BriefTransaction briefTransaction) {
        this.briefPropertiesList.add(smartBriefProperties);
        this.encryptState = encryptState;
        this.primaryDataSource = dataSource;
        this.briefTransaction = briefTransaction;
    }

    public SmartBriefContext(SmartBriefProperties smartBriefProperties, DataSource dataSource, BriefTransaction briefTransaction) {
        this.briefPropertiesList.add(smartBriefProperties);
        this.primaryDataSource = dataSource;
        this.briefTransaction = briefTransaction;
    }

    public SmartBriefContext( DataSource dataSource, BriefTransaction briefTransaction) {
        this.briefPropertiesList.add(new SmartBriefProperties());
        this.primaryDataSource = dataSource;
        this.briefTransaction = briefTransaction;
    }

    public List<BriefProperties> getBriefPropertiesList() {
        return briefPropertiesList;
    }

    @Override
    public boolean getEncryptState() {
        return this.encryptState;
    }

    @Override
    public DataSource getDataSource() {
        return this.primaryDataSource;
    }

    @Override
    public BriefTransaction getBriefTransaction() {
        return this.briefTransaction;
    }

    @Override
    public BriefMapper getBriefMapper(Class briefMapper) {
        return this.cache.get(briefMapper);
    }

    @Override
    public List<BriefPropertiesLoader> getBriefPropertiesLoader() {
        return this.briefPropertiesLoaderList;
    }

    @Override
    public List<JqlInterceptor> getJqlInterceptors() {
        return this.coreInterceptorsList;
    }

    public Map<Class, BriefMapper> getCacheMapper() {
        return cache;
    }

    public <T extends BriefProperties> List<T> getBriefProperties(Class<T> clazz){
        List collect = this.getBriefPropertiesList().stream().filter(briefProperties -> {
            return clazz.isAssignableFrom(briefProperties.getClass());
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void fresh() {
        initProperties();//初始化配置信息
        initContextPostProcess();//执行加载器
        finish();//已经可以对外提供功能了.
    }

    //初始化配置信息.
    private void initProperties() {

        //加载配置
        for(BriefPropertiesLoader briefPropertiesLoader : briefPropertiesLoaderList){
            briefPropertiesList.add(briefPropertiesLoader.startLoader());

        }
        //初始化配置
        for(BriefProperties briefProperties : briefPropertiesList){
            briefProperties.fresh();
        }
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
