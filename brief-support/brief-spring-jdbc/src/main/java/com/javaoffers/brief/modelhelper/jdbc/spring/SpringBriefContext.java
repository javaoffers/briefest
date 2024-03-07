package com.javaoffers.brief.modelhelper.jdbc.spring;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.context.SmartBriefProperties;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.utils.BriefUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.lang.reflect.Field;

/**
 * spring brief context
 */
public class SpringBriefContext extends SmartBriefContext {

    private ConfigurableListableBeanFactory beanFactory;

    public SpringBriefContext(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory.getBean(DataSource.class));
        this.beanFactory = beanFactory;
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void fresh() {
        loadSpringEnvironment();
        super.fresh();
    }

    /**
     * Parse the configuration in the spring environment and support brief
     */
    private void loadSpringEnvironment() {
        try {
            // {@code ConfigPropertiesConstants}
            SmartBriefProperties briefProperties = this.getBriefProperties(SmartBriefProperties.class).get(0);
            Environment environment = beanFactory.getBean(Environment.class);
            Field[] declaredFields = ConfigPropertiesConstants.class.getDeclaredFields();
            for(Field field : declaredFields){
                field.setAccessible(true);
                String property = (String)field.get(null);
                String propertyTmp = property.replaceAll(":", ".");
                String value = environment.getProperty(propertyTmp, "");
                if(StringUtils.isNotBlank(value)){
                    briefProperties.put(property, value);
                }
            }
            //spring-jdbc-factory
            briefProperties.setJdbcExecutorFactory(SpringJdbcExecutorFactory.class.getName());
        }catch (Exception e){
            e.printStackTrace();
            throw new BriefException(e.getMessage());
        }
    }

}
