package com.javaoffers.brief.modelhelper.jdbc.spring;


import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.context.BriefProperties;
import com.javaoffers.brief.modelhelper.context.SmartBriefProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author cmj
 * @createTime December 12, 2021 20:22:00
 */
public class SpringJdbcConfig implements BeanFactoryPostProcessor, BeanPostProcessor, BriefContextPostProcess {
    private AtomicBoolean status = new AtomicBoolean(true);
    static BriefContext briefContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
       if(status.compareAndSet(true,false)){
           BriefProperties briefProperties = briefContext.getBriefProperties();
           briefProperties
           .setJdbcExecutorFactory(SpringJdbcExecutorFactory.class.getName());
          SmartBriefProperties.initJdbcExecutorFactory();
       }
    }


    @Override
    public void postProcess(BriefContext briefContext) {

    }
}
