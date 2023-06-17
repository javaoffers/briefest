package com.javaoffers.brief.modelhelper.speedier;

import com.javaoffers.brief.modelhelper.mapper.CrudMapper;
import com.javaoffers.brief.modelhelper.utils.BriefUtils;
import com.javaoffers.brief.modelhelper.utils.JdkProxyUtils;
import org.springframework.util.Assert;
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
    private Map<Class, CrudMapper> cache = new ConcurrentHashMap<>();

    /**
     * Create a default CrudMapper
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T> CrudMapper<T> newDefaultCrudMapper(Class<T> modelClass) {
        CrudMapper proxy = null;
        if ((proxy = cache.get(modelClass)) == null) {
            synchronized (modelClass) {
                Assert.isTrue(!CrudMapper.class.isAssignableFrom(modelClass), modelClass.getName() + " can't be CrudMapper subclass");
                //First create crudMapper implementation
                CrudMapper crudMapperImpl = BriefUtils.newCrudMapper(CrudMapper.class);
                //Generate crudMapper agent
                SpeedierMapperProxy speedierMapperProxy = new SpeedierMapperProxy(crudMapperImpl, dataSource, modelClass);
                proxy = JdkProxyUtils.createProxy(CrudMapper.class, speedierMapperProxy);
                cache.put(modelClass, proxy);
            }
        }

        return proxy;
    }

    /**
     * Create a custom Mapper
     *
     * @param mapperClass {@link CrudMapper}
     * @param <M>
     * @return
     */
    public <M extends CrudMapper> M newCustomCrudMapper(Class<M> mapperClass) {
        Assert.isTrue(CrudMapper.class.isAssignableFrom(mapperClass), mapperClass.getName() + " must be CrudMapper subclass");
        Type[] types = mapperClass.getGenericInterfaces();
        ParameterizedTypeImpl parameterizedTypes = (ParameterizedTypeImpl) types[0];
        Type modelclass = parameterizedTypes.getActualTypeArguments()[0];
        //First create crudMapper implementation
        CrudMapper crudMapperImpl = BriefUtils.newCrudMapper(mapperClass);
        //Generate crudMapper agent
        SpeedierMapperProxy speedierMapperProxy = new SpeedierMapperProxy(crudMapperImpl, dataSource, (Class) modelclass);
        M proxy = JdkProxyUtils.createProxy(mapperClass, speedierMapperProxy);
        return proxy;
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
