package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.jdbc.BriefTransaction;
import com.javaoffers.brief.modelhelper.mapper.BaseMapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * brief context . 用于初始化化brief启动前的必要信息. 是brief的上下文，代表brief的应用.
 *
 * @author mingJie
 */
public interface BriefContext {

    //brief的配置信息,存在默认配置+用户配置(用户可自定义brief提供的配置功能).
    public BriefProperties getBriefProperties();

    //是否开启加密.
    boolean getEncryptState();

    //数据源
    public DataSource getDataSource();

    //brief事务
    public BriefTransaction getBriefTransaction();

    //缓存BriefMapper
    public <T extends BaseMapper> T getBriefMapper(Class<?> briefMapper);

    /**
     * 通过{@code BriefPropertiesLoader 加载配置到 briefProperties}
     * 只需要实现接口{@code BriefPropertiesLoader} 即可. 子类会被调用执行.
     */
    public List<BriefPropertiesLoader> getBriefPropertiesLoader();

    /**
     * 获取jql拦截器.
     */
    public List<JqlInterceptor> getJqlInterceptors();

    /**
     * 执行fresh方法后开始生效。
     */
    public void fresh();
}
