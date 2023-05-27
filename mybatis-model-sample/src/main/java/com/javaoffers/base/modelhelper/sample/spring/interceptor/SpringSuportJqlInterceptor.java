package com.javaoffers.base.modelhelper.sample.spring.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.constant.Sex;
import com.javaoffers.base.modelhelper.sample.spring.constant.Work;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.batis.modelhelper.utils.InterceptorLoader;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
public class SpringSuportJqlInterceptor implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();
    public static boolean status = true;
    @Resource
    CrudUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportJqlInterceptor.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LogInterceptor logInterceptor = new LogInterceptor();
        ArrayList<JqlInterceptor> jqlInterceptors = new ArrayList<>();
        jqlInterceptors.add(logInterceptor);
        InterceptorLoader.init(jqlInterceptors);

        List<User> query = this.crudUserMapper.general().query(1, 1);
        print(query);

        System.exit(0);
    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}