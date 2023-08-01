package com.javaoffers.brief.modelhelper.jdbc.spring;


import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author cmj
 * @createTime December 12, 2021 20:22:00
 */
public class SpringJdbcConfig implements BeanFactoryPostProcessor, BeanPostProcessor {
    private AtomicBoolean status = new AtomicBoolean(true);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
       if(status.compareAndSet(true,false)){
           System.setProperty(ConfigPropertiesConstants.JDBC_EXECUTOR_FACTORY,
                   SpringJdbcExecutorFactory.class.getName());
       }
    }


}
