package com.javaoffers.batis.modelhelper.install;

import com.javaoffers.batis.modelhelper.aggent.InstallModelHelper;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereFunStringImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.annotation.Resource;
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
    public InitMapper setDataSource(){
        return new InitMapper();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        InstallModelHelper.install();
    }

}
