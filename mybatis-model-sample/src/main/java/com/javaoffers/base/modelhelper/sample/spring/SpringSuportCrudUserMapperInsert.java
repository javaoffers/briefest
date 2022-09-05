package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.core.Id;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
public class SpringSuportCrudUserMapperInsert implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    CrudUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperInsert.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testInsertModel();
        System.exit(0);

    }

    public void testInsertModel(){
        User m1 = User.builder().name("m1").build();
        User m2 = User.builder().name("m2").build();
        User m3 = User.builder().name("m3").birthday(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")).build();
        User m4 = User.builder().name("m4").birthday(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")).build();
        User m5 = User.builder().name("m5").birthday(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")).build();
        List<Id> exs = crudUserMapper.insert().colAll(m1, m2, m3, m4,m5).exs();
        LOGUtils.printLog(exs);
    }


    public void testInsert() throws JsonProcessingException {
        //查询col
        Id exOne = crudUserMapper.insert()
                .col(User::getBirthday, new Date())
                .col(User::getName, "xiao hua 1")
                .ex();
        print(exOne);

        //Insert all Col
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        User h1 = User.builder().name("h1").birthday(date).build();
        User h2 = User.builder().name("h2").birthday(date).build();
        User h3 = User.builder().name("h3").birthday(date).build();
        List<Id> ex = crudUserMapper.insert()
                .colAll(h1, h2)
                .colAll(h3)
                .exs();
        print(ex);

        //查询最新插入的数据
        List<User> exs = crudUserMapper.select().colAll()
                .where()
                .in(User::getId, ex)
                .exs();
        print(exs);

        h1.setName("saveUser");
        crudUserMapper.saveUser(h1);
        print(h1);
    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
