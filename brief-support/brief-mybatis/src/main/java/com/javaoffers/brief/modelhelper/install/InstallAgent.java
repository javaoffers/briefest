package com.javaoffers.brief.modelhelper.install;

import com.javaoffers.brief.modelhelper.aggent.InstallModelHelper;
import com.javaoffers.brief.modelhelper.config.BriefMybatisConfigContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author cmj
 * @createTime December 12, 2021 20:22:00
 */
@Configuration
@AutoConfigureAfter(DataSource.class)
public class InstallAgent implements BeanFactoryPostProcessor, BeanPostProcessor {
    private AtomicBoolean status = new AtomicBoolean(true);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
       if(status.compareAndSet(true,false)){
           InstallModelHelper.install();
           Environment environment = beanFactory.getBean(Environment.class);
           BriefMybatisConfigContext.init(environment);
       }
    }


}
