package com.javaoffers.base.modelhelper.sample.spring.mapper;

import com.javaoffers.base.modelhelper.sample.spring.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper  {

   User queryUserDataLimitOne();

   List<User>  queryUserData();

   List<User> queryUserAndOrder();

   User queryUserAndOrderOne();

   @Select("select * from user limit 1")
   User queryUserLimitOne();

   @Select("select a.* , b.id as orderId, b.* from user a left join user_order b on a.id = b.user_id")
   List<User> queryUserLimitOneAndOrder();
}
