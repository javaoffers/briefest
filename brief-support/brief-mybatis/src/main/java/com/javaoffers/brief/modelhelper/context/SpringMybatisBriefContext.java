package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.jdbc.spring.SpringBriefContext;
import com.javaoffers.brief.modelhelper.jdbc.spring.SpringJdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.utils.BriefUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring brief context
 */
public class SpringMybatisBriefContext extends SpringBriefContext {

    //缓存BriefMapper
    private static Map<Class, BriefMapper> cache = new ConcurrentHashMap<>();

    public SpringMybatisBriefContext(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory, null);
    }

    @Override
    public BriefMapper getBriefMapper(Class briefMapperClass) {
        BriefMapper briefMapper = cache.get(briefMapperClass);
        if(briefMapper == null){
            cache.putIfAbsent(briefMapperClass,BriefUtils.newCrudMapper(briefMapperClass));
            briefMapper = cache.get(briefMapperClass);
        }
        return briefMapper;
    }

}
