package com.javaoffers.base.modelhelper.sample.spring.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.mapper.BriefUserMapper;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptorLoader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.mapper")
public class SpringSuportJqlInterceptor implements InitializingBean , JqlInterceptorLoader{

    ObjectMapper objectMapper = new ObjectMapper();
    public static boolean status = true;
    @Resource
    BriefUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportJqlInterceptor.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<User> query = this.crudUserMapper.general().query(1, 1);
        print(query);

        System.exit(0);
    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }

    @Override
    public JqlInterceptor loadJqlInterceptor() {
        LogInterceptor logInterceptor = new LogInterceptor();
        return logInterceptor;
    }
}