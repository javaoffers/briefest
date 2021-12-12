package com.javaoffers.base.modelhelper.sample.spring;

import com.javaoffers.base.modelhelper.sample.spring.mapper.UserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
@RequestMapping
public class SpringSuport implements InitializingBean {

    @Resource
    UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuport.class, args);

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        List<User> users = userMapper.queryUserData();
        LOGUtils.printLog(users.size());
        System.exit(0);
    }
}
