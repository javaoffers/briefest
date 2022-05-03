package com.javaoffers.batis.modelhelper.install;

import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereFunStringImpl;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Description:
 * @Auther: create by cmj on 2022/5/3 17:27
 */
public class InitMapper implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DataSource){
            new WhereFunStringImpl(new JdbcTemplate((DataSource) bean));
            new TableHelper((DataSource) bean);
        }
        return bean;
    }
}
