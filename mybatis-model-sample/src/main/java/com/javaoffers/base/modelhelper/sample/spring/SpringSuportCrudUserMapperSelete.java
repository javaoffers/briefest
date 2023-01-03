package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.Teacher;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.spring.model.UserOrder;
import com.javaoffers.base.modelhelper.sample.spring.model.UserTeacher;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.AggTag;
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
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
public class SpringSuportCrudUserMapperSelete implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();

    public static boolean status = true;

    @Resource
    CrudUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperSelete.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testDistinc();
        testColAll();
        testEnum();
        test3Join();
        testAsName();
        testSelectOp();
        testSelect();

        if (status) {
            System.exit(0);
        }
    }

    public void testDistinc(){
        User ex = crudUserMapper.select().distinct().colAll().where().limitPage(1, 1).ex();
        LOGUtils.printLog(ex);
    }

    public void testColAll(){
        for (int i = 0; i < 10; i++){
            List<User> query = crudUserMapper.general().query(1, 1);
            LOGUtils.printLog(query);
        }
    }

    public void testEnum() {
        List<User> exs = this.crudUserMapper
                        .select()
                        .col(User::getSex)
                        .where()
                        .exs();
        LOGUtils.printLog(exs);
    }


    public void test3Join() {
        List<User> exs = this.crudUserMapper.select()
                .col(User::getId)
                .innerJoin(UserTeacher::new)
                .on()
                .oeq(User::getId, UserTeacher::getUserId)
                .innerJoin(Teacher::new)
                .col(Teacher::getName)
                .on()
                .oeq(UserTeacher::getTeacherId, Teacher::getId)
                .where()
                .exs();
        LOGUtils.printLog(exs);

        exs = this.crudUserMapper.select()
                .col(AggTag.MAX, User::getId)
                .innerJoin(UserTeacher::new)
                .on()
                .oeq(User::getId, UserTeacher::getUserId)
                .innerJoin(Teacher::new)
                .col(AggTag.MAX, Teacher::getName)
                .on()
                .oeq(UserTeacher::getTeacherId, Teacher::getId)
                .where()
                .groupBy(Teacher::getId)
                .exs();
        LOGUtils.printLog(exs);

        exs = this.crudUserMapper.select()
                .col(User::getId)
                .innerJoin(UserTeacher::new)
                .on()
                .oeq(User::getId, UserTeacher::getUserId)
                .innerJoin(Teacher::new)
                .col(AggTag.MAX, Teacher::getName)
                .on()
                .oeq(UserTeacher::getTeacherId, Teacher::getId)
                .where()
                .groupBy(User::getId)
                .groupBy(UserTeacher::getTeacherId)
                .groupBy(Teacher::getId)
                .having()
                .exs();
        LOGUtils.printLog(exs);

        exs = this.crudUserMapper
                .select()
                .col(User::getId)
                .innerJoin(UserTeacher::new)
                .col(UserTeacher::getTeacherId)
                .on()
                .oeq(User::getId, UserTeacher::getId)
                .innerJoin(Teacher::new)
                .col(Teacher::getId)
                .col(AggTag.MAX, Teacher::getName)
                .on()
                .oeq(UserTeacher::getTeacherId, Teacher::getId)
                .where()
                .gt(User::getId, 0)
                .groupBy(Teacher::getId)
                .groupBy(UserTeacher::getTeacherId)
                .groupBy(User::getId)
                .having()
                .gt(AggTag.MAX, User::getId, 0)
                .gt(AggTag.MAX, UserTeacher::getId, 0)
                .gt(AggTag.MAX, Teacher::getId, 0)
                .orderA(User::getId)
                .orderA(UserTeacher::getId)
                .orderA(Teacher::getId)
                .exs();
        LOGUtils.printLog(exs);

        exs = this.crudUserMapper
                .select()
                .col(AggTag.MAX, User::getId)
                .innerJoin(UserTeacher::new)
                .col(AggTag.MAX, UserTeacher::getTeacherId)
                .on()
                .oeq(User::getId, UserTeacher::getUserId)
                .innerJoin(Teacher::new)
                .col(Teacher::getId)
                .col(AggTag.MAX, Teacher::getName)
                .on()
                .oeq(UserTeacher::getTeacherId, Teacher::getId)
                .where()
                .gt(User::getId, 0)
                .groupBy(Teacher::getId)
                .having()
                .gt(AggTag.MAX, User::getId, 0)
                .gt(AggTag.MAX, UserTeacher::getId, 0)
                .gt(AggTag.MAX, Teacher::getId, 0)
                //sql_mode=only_full_group_by
//                .orderA(User::getId)
//                .orderA(UserTeacher::getId)
                .orderA(Teacher::getId)
                .limitPage(1, 10)
                .exs();
        LOGUtils.printLog(exs);
    }

    public void testAsName() {
        User id = this.crudUserMapper.select()
                .col(AggTag.MAX, User::getId, "id")
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderId, "orderId")
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .ex();
        LOGUtils.printLog(id);
    }


    public void testSelectOp() {
        long start = System.nanoTime();
        List<User> exs1 = crudUserMapper
                .select()
                .colAll()
                .leftJoin(UserOrder::new)
                .colAll()
                .on()
                .oeq(User::getId, UserOrder::getOrderId)
                .where()
                .limitPage(1, 10000)
                .exs();

        long end = System.nanoTime();
        LOGUtils.printLog("query cost time： " + TimeUnit.NANOSECONDS.toMillis(end - start));//10000 cost 688ms
        LOGUtils.printLog(exs1.size());
    }

    public void testSelect() throws Exception {
        crudUserMapper.delete().where().gt(User::getId, 1000).ex();
        /**
         * Queries the specified field
         */
        User exm = crudUserMapper.select().col(User::getId).where().ex();
        print(exm);
        System.out.println("-------------------------------");

        exm = crudUserMapper.select()
                .colAll()
                .where()
                .isNotNull(User::getId)
                .ex();
        print(exm);

        /**
         * Queries with conditions
         */
        exm = crudUserMapper
                .select()
                .col(User::getId)
                .col(User::getBirthday)
                .where()
                .ex();

        System.out.println("-------------------------------");

        /**
         * Query all fields
         */
        User ex1 = crudUserMapper.select().colAll().where().ex();
        print(ex1);
        System.out.println("-------------------------------");

        /**
         * The query supports only two tables left join
         */
        User ex = crudUserMapper.select()
                .colAll()
                .leftJoin(UserOrder::new)
                .colAll()
                .on()
                .oeq(User::getId, UserOrder::getUserId)// a left join b table relationships
                .eq(UserOrder::getIsDel, 1)// b Table field values
                .where()
                .eq(User::getId, 1)
                .or()
                .eq(User::getId, 2)
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

        System.out.println("-----------group by--------------------");
        List<User> exs1 = crudUserMapper.select()
                .col(User::getBirthday)
                .col(User::getName)
                .where()
                .eq(User::getId, 1)
                .groupBy(User::getBirthday, User::getName)
                .exs();

        System.out.println("-----------group by ,  having --------------------");
        exs1 = crudUserMapper.select()
                .col(User::getBirthday)
                .col(User::getName)
                .where()
                .eq(User::getId, 1)
                .groupBy(User::getBirthday, User::getName)
                .having()
                .gt(AggTag.COUNT, User::getId, 1)
                .gt(AggTag.COUNT, User::getId, 2)
                .exs();

        System.out.println("-----------group by ,  having, limitPage  --------------------");
        exs1 = crudUserMapper
                .select()
                .col(User::getBirthday)
                .col(User::getName)
                .col(AggTag.MAX, User::getId)
                .where()
                .eq(User::getId, 1)
                .groupBy(User::getBirthday, User::getName)
                .having()
                .eq(AggTag.COUNT, User::getId, 1)
                .limitPage(1, 10)
                .exs();

        System.out.println("-----------left join , group by  --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .groupBy(User::getName, User::getId)//According to the main group
                .exs();
        print(exs1);

        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .groupBy(UserOrder::getUserId) //It can be grouped directly by subtable
                .exs();
        print(exs1);

        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //Group by main table
                .groupBy(User::getName, User::getId)
                //Group according to sub-table
                .groupBy(UserOrder::getUserId)
                .exs();
        print(exs1);

        System.out.println("-----------left join , group by , limitPage  --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //Group by main table
                .groupBy(User::getName, User::getId)
                //Group according to sub-table
                .groupBy(UserOrder::getUserId)
                .limitPage(1, 10)
                .exs();
        print(exs1);

        System.out.println("-----------left join , group by , having,  limitPage  --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .groupBy(User::getName, User::getId)//Group by main table
                .groupBy(UserOrder::getUserId) //Group according to sub-table
                .having()
                //Main table statistics function
                .eq(AggTag.COUNT, User::getName, 1)
                .or()
                .unite(unite -> {
                    unite.in(AggTag.COUNT, UserOrder::getUserId, 1)
                            .in(AggTag.COUNT, UserOrder::getUserId, 1);
                })
                //Subtable statistics function
                .gt(AggTag.COUNT, UserOrder::getOrderId, 1)
                .limitPage(1, 10)
                .exs();
        print(exs1);

        System.out.println("-----------left join , order by , limitPage --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                // Sort by primary table
                .orderA(User::getBirthday)
                //Sort by subtable
                .orderA(UserOrder::getIsDel)
                .limitPage(1, 10)
                .exs();
        print(exs1);

        System.out.println("-----------left join , group by  , order by, limitPage --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //Group by main table
                .groupBy(User::getName, User::getId)
                //Group according to sub-table
                .groupBy(UserOrder::getUserId)
                // Sort by primary table
                .orderA(User::getName)
                //Sort by subtable
                .orderA(UserOrder::getUserId)
                .limitPage(1, 10)
                .exs();
        print(exs1);

        System.out.println("----------- inner join , group by  , having , order by, limitPage --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .innerJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //Group by main table
                .groupBy(User::getName, User::getId)
                //Group according to sub-table
                .groupBy(UserOrder::getUserId)
                .having()
                // Sort by primary table
                .orderA(User::getId)
                //Sort by subtable
                .orderD(UserOrder::getUserId)
                .limitPage(1, 10)
                .exs();
        print(exs1);

        HashMap<String, Object> params = new HashMap<>();
        params.put("ids", Arrays.asList(1, 2));
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .innerJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .condSQL("1=1")
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .condSQL("1=1")
                .condSQL("user.id in (#{ids})", params)
                //Group by main table
                .groupBy(User::getName, User::getId)
                //Group according to sub-table
                .groupBy(UserOrder::getUserId)
                // Sort by primary table
                .orderA(User::getName)
                //Sort by subtable
                .orderD(UserOrder::getUserId)
                .limitPage(1, 10)
                .exs();
        print(exs1);
        System.out.println("----------- inner join on unite, where unite  group by  , having unite, order by, limitPage --------------------");
        exs1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .innerJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                //support and (xxx )
                .unite(unite -> {
                    unite.eq(UserOrder::getUserId, 1)
                            .or()
                            .eq(UserOrder::getUserId, 2);
                })
                .where()
                //support and (xxx )
                .unite(unite -> {
                    unite.eq(User::getId, 1)
                            .or()
                            .eq(User::getId, 2);
                })
                //Accordinprint(user);g to the main group
                .groupBy(User::getName, User::getId)
                //Group according to sub-table
                .groupBy(UserOrder::getUserId)
                .having()
                .gt(AggTag.MAX, User::getId, 1)
                .gt(AggTag.MAX, UserOrder::getOrderId, 1)
                // Sort by primary table
                .orderA(User::getName)
                //Sort by subtable
                .orderD(UserOrder::getUserId)
                .limitPage(1, 10)
                .exs();
        print(exs1);

        ex1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getBirthday)
                .where()
                .condSQL("2=2")
                .groupBy("left(birthday,10)")
                .ex();
        print(ex1);

        ex1 = crudUserMapper.select()
                //The alias must be the same as the attribute name of the model class
                .col("max(birthday) as birthday")
                .where()
                .groupBy("left(birthday,10)")
                .ex();
        print(ex1);

        ex1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getBirthday)
                .where()
                .condSQL("2=2")
                .groupBy("left(birthday,10)")
                //sql_mode=only_full_group_by
                //.orderA(User::getBirthday)
                .limitPage(1, 1000)
                .ex();
        print(ex1);

        ex1 = crudUserMapper.select()
                .col(AggTag.MAX, User::getBirthday)
                .where()
                .condSQL("2=2")
                .groupBy("left(birthday,10)")
                .having()
                .gt(AggTag.AVG, User::getAbsCN, 1)
                //sql_mode=only_full_group_by
                //.orderA(User::getBirthday)
                .limitPage(1, 1000)
                .ex();
        print(ex1);


        System.out.println("-----------Implement a new way to query, written in the interface default method queryAll-------------------");
        List<User> users = crudUserMapper.queryAll();
        print(users);

        System.out.println("-----------Implement a new way to query, written in the interface default method queryAllAndOrder------------------");
        users = crudUserMapper.queryAllAndOrder();
        print(users);

        System.out.println("-----------Implement a new way to query, written in the interface default method queryUserById------------------");
        User user = crudUserMapper.queryUserById(1);
        print(user);

        System.out.println("-----------Implement a new way to query, written in the interface default method queryUserAndOrderByUserId------------------");
        user = crudUserMapper.queryUserAndOrderByUserId(1);
        print(user);

        System.out.println("-----------Implement a new way to query, written in the interface default method countUsers ------------------");
        long l = crudUserMapper.countUsers();
        LOGUtils.printLog(l);

        System.out.println("-----------Implement a new way to query, written in the interface default method queryUserAndOrderByUserId------------------");
        Date birthday = ConvertRegisterSelectorDelegate.convert.converterObject(Date.class, "2021-12-13");
        user = crudUserMapper.queryUserByBrithday(birthday);
        print(user);

        user = crudUserMapper.select().colAll().where().isNotNull(User::getId).limitPage(1, 1).ex();
        print(user);

    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
