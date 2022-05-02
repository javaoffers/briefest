package com.javaoffers.batis.modelhelper.install;

import com.javaoffers.batis.modelhelper.aggent.InstallModelHelper;
import com.javaoffers.batis.modelhelper.aggent.MapperProxyAggent;
import com.javaoffers.batis.modelhelper.aggent.TypeAliasRegistryAggent;
import com.javaoffers.batis.modelhelper.core.CrudMapperProxy;
import com.javaoffers.batis.modelhelper.fun.impl.WhereFunStringImpl;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
public class InstallAgent implements BeanFactoryPostProcessor, InitializingBean {

    @Resource
    DataSource dataSource;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        InstallModelHelper.install();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new WhereFunStringImpl(new JdbcTemplate(dataSource));
    }
}
