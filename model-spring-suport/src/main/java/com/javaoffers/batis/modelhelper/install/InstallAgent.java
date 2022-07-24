package com.javaoffers.batis.modelhelper.install;

import com.javaoffers.batis.modelhelper.aggent.InstallModelHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author cmj
 * @Description TODO
 * @createTime 2021年12月12日 20:22:00
 */
@Configuration
@AutoConfigureAfter(DataSource.class)
public class InstallAgent implements BeanFactoryPostProcessor {

    @Bean
    public InitMapper setDataSource(DataSource dataSource){
        return new InitMapper(dataSource);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        InstallModelHelper.install();
    }

}
