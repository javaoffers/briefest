package com.javaoffers.base.modelhelper.sample.spring.mapper;

import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.spring.model.UserOrder;
import com.javaoffers.batis.modelhelper.mapper.CrudMapper;

import java.util.List;

/**
 * @Description: sample
 * @Auther: create by cmj on 2022/5/2 22:30
 */
public interface CrudUserMapper extends CrudMapper<User> {

    default List<User> queryAll(){
        return select().colAll().where().exs();
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

    default User queryUserById(Integer id){
        return select()
                .colAll()
                .where()
                .eq(User::getId, id)
                .ex();
    }

    default User queryUserAndOrderByUserId(Integer id){
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
}
