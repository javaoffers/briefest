package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.mapper.BriefUserMapper;
import com.javaoffers.base.modelhelper.sample.model.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.mapper")
public class SpringSuportCrudUserMapperDelete implements InitializingBean {
    public static boolean status = true;
    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    BriefUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperDelete.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testDelete();

        if(status){
            System.exit(0);
        }

    }

    public void testDelete() {
        crudUserMapper.delete()
                .where()
                .eq(User::getId, 2)
                //.isNotNull(User::getId)
                .isNull(User::getId)
                .ex();
    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
