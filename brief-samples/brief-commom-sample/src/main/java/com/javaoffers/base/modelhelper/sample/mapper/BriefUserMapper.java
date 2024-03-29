package com.javaoffers.base.modelhelper.sample.mapper;

import com.javaoffers.base.modelhelper.sample.model.Teacher;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.base.modelhelper.sample.model.UserOrder;
import com.javaoffers.base.modelhelper.sample.model.UserTeacher;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: sample
 * @Auther: create by cmj on 2022/5/2 22:30
 */
public interface BriefUserMapper extends BriefMapper<User> {

    default List<User> queryAll(){
        return select()
                .colAll()
                .where()
                .exs();
    }

    default List<User> queryAllAndOrder(){
        return select()
                .colAll()
                .leftJoin(UserOrder::new)
                .colAll()
                .on()
                .oeq(User::getId,UserOrder::getUserId)
                .where()
                .exs();
    }

    default User queryUserById(Number id){
        return select()
                .colAll()
                .where()
                .eq(User::getId, id)
                .ex();
    }

    default User queryUserAndOrderByUserId(Number id){
        return select()
                .colAll()
                .leftJoin(UserOrder::new)
                .colAll()
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .eq(User::getId, id)
                .ex();
    }

    default Long countUsers(){
        return select()
                .col(AggTag.COUNT,User::getCountId)
                .where()
                .ex()
                .getCountId();
    }

    /**
     * 查询某一天出生的user
     * @param birthday 日期，忽略时分秒
     * @return
     */
    default User queryUserByBrithday(Date birthday){
        Calendar instance = Calendar.getInstance();
        instance.setTime(birthday);
        instance.set(Calendar.HOUR,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        instance.set(Calendar.MILLISECOND,0);
        Date start = instance.getTime();

        instance.set(Calendar.HOUR,23);
        instance.set(Calendar.MINUTE,59);
        instance.set(Calendar.SECOND,59);
        instance.set(Calendar.MILLISECOND,999);
        Date end = instance.getTime();

        return select()
                .col(User::getId,User::getName,User::getBirthday)
                .where()
                .between(User::getBirthday, start, end)
                .ex();
    }

    /**
     * save user
     * @param user
     * @return
     */
    default User saveUser(User user){
        Id ex = insert().colAll(user).ex();
        user.setId(ex.toLong());
        return user;
    }

    @Transactional(rollbackFor=Exception.class)
    default List<Id> saveUserWithTran(User... users){
        this.select()
                .col(User::getId)
                .innerJoin(UserTeacher::new)
                .colAll()
                .on()
                .oeq(User::getId, UserTeacher::getUserId)
                .innerJoin(Teacher::new)
                .col(Teacher::getName)
                .on()
                .oeq(UserTeacher::getTeacherId, Teacher::getId)
                .where()
                .exs();
        List<Id> exs = insert().colAll(users).exs();
        return exs;
    }
}
