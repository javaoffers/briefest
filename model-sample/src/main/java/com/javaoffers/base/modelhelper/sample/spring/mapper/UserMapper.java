package com.javaoffers.base.modelhelper.sample.spring.mapper;

import com.javaoffers.base.modelhelper.sample.spring.model.User;

import java.util.List;

public interface UserMapper  {

   User queryUserDataLimitOne();

   List<User>  queryUserData();

   List<User> queryUserAndOrder();

   User queryUserAndOrderOne();
}
