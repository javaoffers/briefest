package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.spring.model.UserOrder;
import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.JoinFun;
import com.javaoffers.batis.modelhelper.fun.crud.OnFun;
import com.javaoffers.batis.modelhelper.fun.crud.SelectFun;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
public class SpringSuportCrudUserMapper implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    CrudUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapper.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * 查询指定的字段
         */
        User exm = crudUserMapper.select().col(User::getId).where().ex();
        print(exm);
        System.out.println("-------------------------------");

        /**
         * 带有条件的查询
         */
        exm = crudUserMapper
                .select()
                .col(User::getId)
                .col(User::getBirthday)
                .where()
                .ex();

        System.out.println("-------------------------------");

        /**
         * 查询所有字段
         */
        User ex1 = crudUserMapper.select().colAll().where().ex();
        print(ex1);
        System.out.println("-------------------------------");

        /**
         * left join 查询。 只支持 两张表 left join
         */
        User ex = crudUserMapper.select()
                .colAll()
                .leftJoin(UserOrder::new)
                .colAll()
                .on()
                .oeq(User::getId, UserOrder::getUserId)// a left join b表关系
                .eq(UserOrder::getIsDel, 1)// b 表字段值
                .where()
                .eq(User::getId, 1)
                .or()
                .eq(User::getId,2)
                .ex();
        print(ex);
        System.out.println("-------------------------------");

        List<String> ids = Arrays.asList("2", "3", "1");
        List<User> exs = crudUserMapper.select()
                .col(User::getId)
                .colAll()
                .col(false, User::getName)
                .where()
                .in(User::getId, ids)
                .in(User::getId, ids.toArray())
                .or()
                .in(User::getId, 1, 2, 3, 4, 5)
                .exs();
        print(exs);

        System.out.println("-----------实现group by--------------------");
        List<User> exs1 = crudUserMapper.select()
                .col(User::getBirthday)
                .col(User::getName)
                .where()
                .eq(User::getId, 1)
                .groupBy(User::getBirthday,User::getName)
                .exs();

        System.out.println("-----------实现group by ,  having --------------------");
        exs1 = crudUserMapper.select()
                .col(User::getBirthday)
                .col(User::getName)
                .where()
                .eq(User::getId, 1)
                .groupBy(User::getBirthday,User::getName)
                .having()
                .gt(AggTag.COUNT,User::getId, 1)
                .gt(AggTag.COUNT,User::getId,2)
                .exs();

        System.out.println("-----------实现group by ,  having, limitPage  --------------------");
        exs1 = crudUserMapper.select()
                .col(User::getBirthday)
                .col(User::getName)
                .col(AggTag.MAX,User::getId)
                .where()
                .eq(User::getId, 1)
                .groupBy(User::getBirthday,User::getName)
                .having()
                .eq(AggTag.COUNT,User::getId, 1)
                .limitPage(1,10)
                .exs();

        System.out.println("-----------实现left join , group by  --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .groupBy(User::getName, User::getId)//按照主表分组
                .exs();

        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .groupBy(UserOrder::getUserId) //可以直接按照子表分分组
                .exs();

        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //按照主表分组
                .groupBy(User::getName, User::getId)
                //按照子表分分组
                .groupBy(UserOrder::getUserId)
                .exs();

        System.out.println("-----------实现left join , group by , limitPage  --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //按照主表分组
                .groupBy(User::getName, User::getId)
                //按照子表分分组
                .groupBy(UserOrder::getUserId)
                .limitPage(1,10)
                .exs();

        System.out.println("-----------实现left join , group by , having limitPage  --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .groupBy(User::getName, User::getId)//按照主表分组
                .groupBy(UserOrder::getUserId) //按照子表分分组
                .having()
                //主表统计函数
                .eq(AggTag.COUNT,User::getName,1)
                //子表统计函数
                .gt(AggTag.COUNT,UserOrder::getOrderId,1)
                .limitPage(1,10)
                .exs();


        System.out.println("-----------实现left join , order by , limitPage --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                // 根据主表排序
                .orderA(User::getBirthday)
                //根据子表排序
                .orderA(UserOrder::getIsDel)
                .limitPage(1,10)
                .exs();

        System.out.println("-----------实现left join , group by  , order by, limitPage --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //按照主表分组
                .groupBy(User::getName, User::getId)
                //按照子表分分组
                .groupBy(UserOrder::getUserId)
                // 根据主表排序
                .orderA(User::getBirthday)
                //根据子表排序
                .orderA(UserOrder::getIsDel)
                .limitPage(1,10)
                .exs();

        System.out.println("-----------实现left join , group by  , having , order by, limitPage --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //按照主表分组
                .groupBy(User::getName, User::getId)
                //按照子表分分组
                .groupBy(UserOrder::getUserId)
                .having()
                // 根据主表排序
                .orderA(User::getBirthday)
                //根据子表排序
                .orderD(UserOrder::getIsDel)
                .limitPage(1,10)
                .exs();

        System.out.println("-----------实现新的方式查询 --------------------");
        List<User> users = crudUserMapper.queryAll();



        System.exit(0);
    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
