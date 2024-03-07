package com.javaoffers.brief.modelhelper.speedier;

import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.mapper.SmartMapperProxy;
import com.javaoffers.brief.modelhelper.speedier.config.BriefSpeedierConfigProperties;
import com.javaoffers.brief.modelhelper.speedier.transaction.SpeedierTransactionManagement;
import com.javaoffers.brief.modelhelper.utils.BriefUtils;
import com.javaoffers.brief.modelhelper.utils.JdkProxyUtils;
import com.javaoffers.brief.modelhelper.utils.Assert;

import javax.sql.DataSource;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 快速开始.
 */
public class BriefSpeedier {

    /**
     * 事务管理
     */
    private SpeedierBriefContext briefContext;

    //缓存BriefMapper
    private Map<Class, BriefMapper> cache = new ConcurrentHashMap<>();

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
        BriefMapper briefMapper = cache.get(modelClass);
        if(briefMapper == null){
            Assert.isTrue(!Modifier.isAbstract(modelClass.getModifiers()), modelClass.getName() + " is Abstract ");
            BriefMapper briefMapperImpl = BriefUtils.newCrudMapper(BriefMapper.class);
            SmartMapperProxy smartMapperProxy = new SmartMapperProxy(briefMapperImpl, briefContext.getDataSource(), (Class) modelClass);
            cache.putIfAbsent(modelClass, JdkProxyUtils.createProxy(BriefMapper.class, smartMapperProxy));
            briefMapper = cache.get(modelClass);
        }
        return briefMapper;
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
