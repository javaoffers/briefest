package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserOrderMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.spring.model.UserOrder;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.core.Id;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
@Transactional(rollbackFor=Exception.class)
public class SpringSuportCrudUserMapperInsert implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();
    public static boolean status = true;
    @Resource
    CrudUserMapper crudUserMapper;

    @Resource
    CrudUserOrderMapper crudUserOrderMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperInsert.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testInsert();
        if(status){
            System.exit(0);
        }

    }

    public void testBatchInsert(){
        List<User> batchUser = new LinkedList<>();

        for(int i=0; i < 30000; i++){
            User jom = User.builder().name("Hom"+i).money(i+"").build();
            batchUser.add(jom);
        }
        long start = System.nanoTime();
        List<Id> exs = crudUserMapper.insert().colAll(batchUser).exs();
        long end = System.nanoTime();
        //insert cost time： 5s, 30000 pieces of data
        LOGUtils.printLog("insert cost time： "+TimeUnit.NANOSECONDS.toSeconds(end - start));
        LOGUtils.printLog(exs.size());

        start = System.nanoTime();
        List<User> exs1 = crudUserMapper
                .select()
                .colAll()
                .leftJoin(UserOrder::new)
                .colAll()
                .on()
                .oeq(User::getId, UserOrder::getOrderId)
                .where()
                .limitPage(1,100000)
                .exs();

        end = System.nanoTime();
        LOGUtils.printLog("query cost time： "+TimeUnit.NANOSECONDS.toMillis(end - start));//100000 cost 9s
        LOGUtils.printLog(exs1.size());

        Integer ex = crudUserMapper.delete().where().in(User::getId, exs).ex();
        LOGUtils.printLog(ex);
    }


    public void testInsertModelAndOrder(){
        User jom = User.builder().name("Hom").money("200000").build();
        User tom = User.builder().name("Pom").money("8000").build();
        User amop = User.builder().name("Lop").money("30000").birthdayDate(new Date()).build();
        List<Id> exs = crudUserMapper.insert().colAll(jom, tom, amop).exs();

        UserOrder iphone = UserOrder.builder().orderMoney("5000").orderName("Iphone").userId(exs.get(0).toString()).build();
        UserOrder huaWei = UserOrder.builder().orderMoney("5000").orderName("huaWei").userId(exs.get(2).toString()).build();
        List<Id> exs1 = crudUserOrderMapper.insert().colAll(iphone, huaWei).exs();

        LOGUtils.printLog(exs);
        LOGUtils.printLog(exs1);
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

        User duplicateUpdate = User.builder().name("duplicateUpdate").birthdayDate(new Date()).build();
        Id dupUpdate = crudUserMapper.insert().colAll(duplicateUpdate).dupUpdate().ex();
        duplicateUpdate.setId(dupUpdate.toLong());

        duplicateUpdate.setName("duplicateUpdate2");
        Id ex1 = crudUserMapper.insert().colAll(duplicateUpdate).dupUpdate().ex();

        User user = crudUserMapper.general().queryById(ex1);
        print(user);

        duplicateUpdate.setName("replaceInto");
        Id ex2 = crudUserMapper.insert().colAll(duplicateUpdate).dupReplace().ex();
        User user1 = crudUserMapper.general().queryById(ex2);
        print(user1);

        List<User> users = crudUserMapper.general().query(1, 2);
        User user2 = users.get(0);
        User user3 = users.get(1);
        user2.setName("duplicate2");
        user3.setName("duplicate3");
        user3.setBirthday(null);
        crudUserMapper.insert().colAll(user2, user3).dupUpdate().exs();
        List<Long> collect = users.stream().map(User::getId).collect(Collectors.toList());
        List<User> users1 = crudUserMapper.general().queryByIds(collect);
        print(users1);

        crudUserMapper.insert().col(User::getId, user2.getId()).col(User::getName,"duplicate4")
                .dupUpdate().ex();
        List<User> users2 = crudUserMapper.general().queryByIds(user2.getId());
        print(user2);

        crudUserMapper.insert().col(User::getId, user2.getId()).col(User::getName,"duplicate5")
                .dupReplace().ex();
        User user4 = crudUserMapper.general().queryById(user2.getId());
        print(user4);

        user2.setName("duplicate6");
        crudUserMapper.insert().colAll(user2).dupReplace().ex();
        User user5 = crudUserMapper.general().queryById(user2.getId());
        print(user5);

        user2.setName("duplicate7");
        crudUserMapper.insert().colAll(user2).dupReplace().ex();

        User user6 = crudUserMapper.general().queryById(user2.getId());
        print(user6);
    }


    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
