package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.UserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.spring.model.UserOrder;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.SelectFun;
import com.javaoffers.batis.modelhelper.fun.WhereFun;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@SpringBootApplication
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.com.javaoffers.batis.modelhelper.mapper")
@RequestMapping
public class SpringSuportUserMapper implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    CrudUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportUserMapper.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * 查询指定的字段
         */
        UserOrder exm = crudUserMapper.select().col(UserOrder::getOrderId).where().ex();

        /**
         * 带有条件的查询
         */
        exm = crudUserMapper
                .select()
                .col(UserOrder::getOrderId) //查询 orderId字段
                .where()
                .eq(UserOrder::getOrderId, "12")
                .ex();
        SelectFun<UserOrder, GetterFun<UserOrder, Serializable>, Serializable> select = crudUserMapper.select();


        System.exit(0);
    }
}
