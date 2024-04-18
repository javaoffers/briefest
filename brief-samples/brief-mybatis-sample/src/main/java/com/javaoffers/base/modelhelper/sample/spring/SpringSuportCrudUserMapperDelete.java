package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.mapper.BriefUserMapper;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.brief.modelhelper.core.Id;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        testBatchDelete();
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

    public void testBatchDelete(){
        List<User> ls = new ArrayList<User>();
        for(int i=0; i< 103; i++){
            User user = User.builder().name("h1_delete_"+i).build();
            ls.add(user);
        }
        List<Id> ids = crudUserMapper.general().saveBatch(ls);
        System.out.println("---------------> insert size "+ids.size());

        List<User> userList = crudUserMapper.select().colAll().where().like(User::getName, "h1_delete_").exs();

        System.out.println("-----------> select size " + userList.size());

        int i = crudUserMapper.general().removeByIds(userList.stream().map(User::getId).collect(Collectors.toList()));

        System.out.println("-------------> delete size " + i);

        List<User> userList2 = crudUserMapper.select().colAll().where().like(User::getName, "h1_delete_").exs();

        System.out.println("-----------> select size " + userList2.size());

    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
