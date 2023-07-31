package com.javaoffers.brief.modelhelper.speedier;

import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.config.BriefSpeedierConfigProperties;
import com.javaoffers.brief.modelhelper.utils.BriefUtils;
import com.javaoffers.brief.modelhelper.utils.JdkProxyUtils;
import com.javaoffers.brief.modelhelper.utils.Assert;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 快速开始.
 */
public class BriefSpeedier {

    /**
     * The data source.
     */
    private DataSource dataSource;

    /**
     * cache.
     */
    private Map<Class, BriefMapper> cache = new ConcurrentHashMap<>();

    /**
     * Create a default BriefMapper
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T> BriefMapper<T> newDefaultCrudMapper(Class<T> modelClass) {
        BriefMapper proxy = null;
        if ((proxy = cache.get(modelClass)) == null) {
            synchronized (modelClass) {
                Assert.isTrue(!BriefMapper.class.isAssignableFrom(modelClass), modelClass.getName() + " can't be BriefMapper subclass");
                //First create crudMapper implementation
                BriefMapper briefMapperImpl = BriefUtils.newCrudMapper(BriefMapper.class);
                //Generate crudMapper agent
                SpeedierMapperProxy speedierMapperProxy = new SpeedierMapperProxy(briefMapperImpl, dataSource, modelClass);
                proxy = JdkProxyUtils.createProxy(BriefMapper.class, speedierMapperProxy);
                cache.put(modelClass, proxy);
            }
        }

        return proxy;
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
        Type[] types = mapperClass.getGenericInterfaces();
        ParameterizedTypeImpl parameterizedTypes = (ParameterizedTypeImpl) types[0];
        Type modelclass = parameterizedTypes.getActualTypeArguments()[0];
        //First create crudMapper implementation
        BriefMapper briefMapperImpl = BriefUtils.newCrudMapper(mapperClass);
        //Generate crudMapper agent
        SpeedierMapperProxy speedierMapperProxy = new SpeedierMapperProxy(briefMapperImpl, dataSource, (Class) modelclass);
        M proxy = JdkProxyUtils.createProxy(mapperClass, speedierMapperProxy);
        return proxy;
    }

    public static BriefSpeedier getInstance(BriefSpeedierConfigProperties briefConfigProperties){
        return getInstance(briefConfigProperties.getDataSource());
    }

    /**
     * By specifying a data source to create
     *
     * @param dataSource
     * @return bs
     */
    public static BriefSpeedier getInstance(DataSource dataSource) {
        BriefSpeedier briefSpeedier = new BriefSpeedier();
        briefSpeedier.dataSource = dataSource;
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
