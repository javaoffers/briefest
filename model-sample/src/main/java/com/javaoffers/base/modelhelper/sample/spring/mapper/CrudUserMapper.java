package com.javaoffers.base.modelhelper.sample.spring.mapper;

import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.batis.modelhelper.mapper.CrudMapper;

import java.util.List;

/**
 * @Description:
 * @Auther: create by cmj on 2022/5/2 22:30
 */
public interface CrudUserMapper extends CrudMapper<User> {

    default List<User> queryAll(){
        return select().colAll().where().exs();
    }
}
