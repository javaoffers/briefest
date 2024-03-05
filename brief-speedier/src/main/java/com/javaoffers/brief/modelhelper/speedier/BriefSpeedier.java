package com.javaoffers.brief.modelhelper.speedier;

import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.config.BriefSpeedierConfigProperties;
import com.javaoffers.brief.modelhelper.speedier.transaction.SpeedierTransactionManagement;
import com.javaoffers.brief.modelhelper.utils.BriefUtils;
import com.javaoffers.brief.modelhelper.utils.JdkProxyUtils;
import com.javaoffers.brief.modelhelper.utils.Assert;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.sql.DataSource;
import java.lang.reflect.Type;

/**
 * 快速开始.
 */
public class BriefSpeedier {

    /**
     * 事务管理
     */
    private SpeedierBriefContext briefContext;

    /**
     * cache.
     */

    /**
     * Create a default BriefMapper
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T> BriefMapper<T> newDefaultCrudMapper(Class<T> modelClass) {
        BriefMapper proxy = null;
        if ((proxy = briefContext.getBriefMapper(modelClass)) == null) {
            synchronized (modelClass) {
                if((proxy = briefContext.getBriefMapper(modelClass)) == null){
                    Assert.isTrue(!BriefMapper.class.isAssignableFrom(modelClass), modelClass.getName() + " can't be BriefMapper subclass");
                    //First create crudMapper implementation
                    BriefMapper briefMapperImpl = BriefUtils.newCrudMapper(BriefMapper.class);
                    //Generate crudMapper agent
                    SpeedierMapperProxy speedierMapperProxy = new SpeedierMapperProxy(briefMapperImpl,
                            briefContext.getDataSource(), modelClass);
                    proxy = JdkProxyUtils.createProxy(BriefMapper.class, speedierMapperProxy);
                    briefContext.getCacheMapper().put(modelClass, proxy);
                }
            }
        }

        return proxy;
    }

    public SpeedierTransactionManagement getTransactionManagement() {
        return briefContext.getTransactionManagement();
    }

    /**
     * Create a custom Mapper
     *
     * @param mapperClass {@link BriefMapper}
     * @param <M>
     * @return
     */
    public <M extends BriefMapper> M newCustomCrudMapper(Class<M> mapperClass) {
        Assert.isTrue(BriefMapper.class.isAssignableFrom(mapperClass), mapperClass.getName() + " must be BriefMapper subclass");
        M proxyBriefMapper = (M) briefContext.getBriefMapper(mapperClass);
        if(proxyBriefMapper == null){
            synchronized (mapperClass){
                proxyBriefMapper = (M) briefContext.getBriefMapper(mapperClass);
                if(proxyBriefMapper == null){
                    Type[] types = mapperClass.getGenericInterfaces();
                    ParameterizedTypeImpl parameterizedTypes = (ParameterizedTypeImpl) types[0];
                    Type modelclass = parameterizedTypes.getActualTypeArguments()[0];
                    //First create crudMapper implementation
                    BriefMapper briefMapperImpl = BriefUtils.newCrudMapper(mapperClass);
                    //Generate crudMapper agent
                    SpeedierMapperProxy speedierMapperProxy = new SpeedierMapperProxy(briefMapperImpl,
                            this.briefContext.getDataSource(), (Class) modelclass);
                    proxyBriefMapper = JdkProxyUtils.createProxy(mapperClass, speedierMapperProxy);
                    briefContext.getCacheMapper().put(mapperClass, proxyBriefMapper);
                }
            }
        }
        return proxyBriefMapper;
    }

    public static BriefSpeedier getInstance(BriefSpeedierConfigProperties briefConfigProperties){
        BriefSpeedier briefSpeedier = new BriefSpeedier();
        DataSource dataSource = briefConfigProperties.getDataSource();
        briefSpeedier.briefContext = new SpeedierBriefContext(dataSource);
        briefSpeedier.briefContext.getBriefPropertiesList().add(briefConfigProperties);
        briefSpeedier.briefContext.fresh();
        return briefSpeedier;
    }

    /**
     * By specifying a data source to create
     *
     * @param dataSource
     * @return bs
     */
    public static BriefSpeedier getInstance(DataSource dataSource) {
        BriefSpeedier briefSpeedier = new BriefSpeedier();
        briefSpeedier.briefContext = new SpeedierBriefContext(dataSource);
        briefSpeedier.briefContext.fresh();
        return briefSpeedier;
    }

    /**
     * Use common data source
     *
     * @param dataSource
     * @return
     */
    public static BriefSpeedier getInstance(BriefSpeedierDataSource dataSource) {
        return getInstance((DataSource) dataSource);
    }

}
