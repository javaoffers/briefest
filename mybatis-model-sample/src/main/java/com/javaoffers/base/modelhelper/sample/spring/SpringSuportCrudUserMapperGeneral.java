package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.batis.modelhelper.util.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
public class SpringSuportCrudUserMapperGeneral implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    CrudUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperGeneral.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        User general = User.builder().name("general").build();
        int remove = crudUserMapper.general().remove(general);

        //save
        long save = crudUserMapper.general().save(general);

        //query by id
        User user = crudUserMapper.general().queryById(save);
        print(user);

        //modify By Id
        user.setBirthday(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        crudUserMapper.general().modifyById(user);
        user = crudUserMapper.general().queryById(save);
        print(user);

        //modify By Id, If id is set to null, the update operation will not be triggered
        user.setId(null);//If id is set to null, the update operation will not be triggered
        user.setCountId(null);//If id is set to null, the update operation will not be triggered
        user.setName("no update");
        int i = crudUserMapper.general().modifyById(user);
        Assert.isTrue(i == 0);

        List<User> query = this.crudUserMapper.general().query(1, 10);
        print(query);
        query.forEach(user1 -> {
            user1.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        });
        this.crudUserMapper.general().modifyBatchById(query);
        List<User> users = this.crudUserMapper.general().queryByIds(query.stream().map(User::getId).collect(Collectors.toList()));
        print(users);

        long count = this.crudUserMapper.general().count();
        print(count);

        long moneyCount = this.crudUserMapper.general().count(User::getMoney);
        print(moneyCount);
        System.exit(0);

    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
