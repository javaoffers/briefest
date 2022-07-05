package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.spring.model.UserOrder;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.AggTag;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
public class SpringSuportCrudUserMapperUpdate implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    CrudUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperUpdate.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testUpdate();

        System.exit(0);

    }

    private void testUpdate() throws JsonProcessingException {
        Long id = 109L;
        List<User> exs = crudUserMapper.select()
                .colAll()
                .where()
                .isNotNull(User::getId)
                .exs();
        id = exs.get(0).getId();


        crudUserMapper.update()
                .npdateNull()
                .col(User::getName,"ling")
                .where()
                .eq(User::getId,id)
                .ex();

        crudUserMapper.update().npdateNull()
                .col(User::getBirthday, new Date())
                //name not will update . because its null
                .col(User::getName,null)
                .where()
                .eq(User::getId, id)
                .ex();
        User user = crudUserMapper.queryUserById(id);
        print(user);

        crudUserMapper.update()
                .updateNull()
                .col(User::getBirthday, new Date())
                .col(User::getId, id)
                .where()
                .eq(User::getId,id)
                .ex();

        user = crudUserMapper.select()
                .colAll()
                .where()
                .eq(User::getId, id)
                .ex();

        crudUserMapper.update()
                .npdateNull()
                .colAll(user)
                .where()
                .eq(User::getId,id)
                .ex();

        crudUserMapper.update()
                .npdateNull()
                .colAll(new User())
                .where()
                .eq(User::getId,id)
                .ex();

        crudUserMapper.update()
                .npdateNull()
                .col(User::getName,"zhou")
                .where()
                .eq(User::getId,1)
                .addBatch()
                .colAll(user)
                .where()
                .eq(User::getId,2)
                .ex();

        print(user);
    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
