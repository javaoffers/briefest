package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

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
        ObjectMapper objectMapper = new ObjectMapper();

        User user1 = userMapper.queryUserDataLimitOne();
        LOGUtils.printLog(objectMapper.writeValueAsString(user1));

        List<User> users = userMapper.queryUserData();
        LOGUtils.printLog(users.size());
        User user = users.get(0);
        LOGUtils.printLog(objectMapper.writeValueAsString(user));
        LOGUtils.printLog(objectMapper.writeValueAsString(users));

        List<User> userAndOrder = userMapper.queryUserAndOrder();
        User userO = userAndOrder.get(0);
        LOGUtils.printLog(objectMapper.writeValueAsString(userO));
        LOGUtils.printLog(objectMapper.writeValueAsString(userAndOrder));

        User user2 = userMapper.queryUserAndOrderOne();
        LOGUtils.printLog(objectMapper.writeValueAsString(user2));

        User user3 = userMapper.queryUserLimitOne();
        LOGUtils.printLog(objectMapper.writeValueAsString(user3));

        List<User> user4 = userMapper.queryUserLimitOneAndOrder();
        LOGUtils.printLog(objectMapper.writeValueAsString(user4));


        System.exit(0);
    }
}
