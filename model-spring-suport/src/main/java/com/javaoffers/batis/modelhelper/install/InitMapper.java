package com.javaoffers.batis.modelhelper.install;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereSelectFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.update.WhereModifyFunImpl;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Description:
 * @Auther: create by cmj on 2022/5/3 17:27
 */
public class InitMapper {
    private DataSource bean;
    public InitMapper(DataSource bean) {
        this.bean = bean;
        JdbcTemplate jdbcTemplate = new JdbcTemplate( bean);
        BaseBatisImpl.baseBatis.setJdbcTemplate(jdbcTemplate);
        new TableHelper( bean);
    }
}
