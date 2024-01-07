package com.javaoffers.brief.modelhelper.jdbc.spring;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.context.SmartBriefProperties;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.jdbc.BriefTransaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.lang.reflect.Field;

/**
 * spring brief context
 */
public class SpringBriefContext extends SmartBriefContext {

    ConfigurableListableBeanFactory beanFactory;

    public SpringBriefContext(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory.getBean(DataSource.class), null);
    }

    @Override
    public void fresh() {
        try {
            SmartBriefProperties briefProperties = this.getBriefProperties();
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
            briefProperties.setJdbcExecutorFactory(SpringJdbcExecutorFactory.class.getName());
        }catch (Exception e){
            e.printStackTrace();
            throw new BriefException(e.getMessage());
        }
        super.fresh();
    }

    /**
     * 事务由spring控制
     */
    @Override
    public BriefTransaction getBriefTransaction() {
        throw new UnsupportedOperationException(" Transactions are controlled by Spring");
    }
}
