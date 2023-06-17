package com.javaoffers.base.modelhelper.sample.speedier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.constant.Work;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.mapper.CrudMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedierDataSource;
import com.javaoffers.brief.modelhelper.utils.Utils;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class BriefSpeedierSample {
    ObjectMapper objectMapper = new ObjectMapper();
    public static String driver6 = "com.mysql.cj.jdbc.Driver";//mysql connection 6
    public static String url6 = "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowMultiQueries=true";//6 需要带时区
    static String  jdbcUrl = "jdbc:mysql://127.0.0.1:3306/base"+url6;
    static String username = "root";
    static String password = "zqbxcmj";
    static BriefSpeedierDataSource dataSource = BriefSpeedierDataSource.getInstance(driver6, jdbcUrl, username, password);
    @Test
    public void testGenMapper(){
        BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
        BriefSpeedier speedier2 = BriefSpeedier.getInstance(dataSource);

        CrudMapper<User> userCrudMapper = speedier.newDefaultCrudMapper(User.class);
        CrudMapper<User> userCrudMapper2 = speedier2.newDefaultCrudMapper(User.class);

        print(userCrudMapper.hashCode());
        print(userCrudMapper2.hashCode());
    }

    @Test
    public void testBriefSpeedier(){

        BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
        //Using custom mapper
        CrudUserMapper crudUserMapper = speedier.newCustomCrudMapper(CrudUserMapper.class);
        List<User> userList = crudUserMapper.select().colAll().where().limitPage(1, 10).exs();
        print(userList);

        User user = new User();
        user.setName("testBriefSpeedier");
        user.setBirthdayDate(new Date());
        user.setWork(Work.JAVA);
        Id save = crudUserMapper.general().save(user);
        print(save.toLong());
        for(int i=0; i<1000; i++) {
            List<User> users = crudUserMapper.queryAll();
            print(user);
        }
        //Use the default mapper
        CrudMapper<User> userCrudMapper = speedier.newDefaultCrudMapper(User.class);
        userList = userCrudMapper.select().colAll().where().limitPage(1, 10).exs();
        print(userList);

        Number count = userCrudMapper.general().count();
        print(count);

    }

    public void print(Object user) {
        try {
            System.out.println(objectMapper.writeValueAsString(user));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
