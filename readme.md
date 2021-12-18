## mybatis model helper
- 概要
  ```
  该插件主要解决mybatis实体映射配置，我们知道通过mybatis进行实体映射时需要大量的xml标签配置，虽有有一些插件帮助生存
  ，但是仍然很鸡肋，每次增加或修改都需要新家xml映射标签。虽然mybatis自带一下注解但是使用起来依然很麻烦。那么该插件主
  要就是解决这些问题的。
  ```
- 使用环境mvn
  ```
  
  ```  
- 使用案例
  - sql
    ```
     CREATE TABLE `user` (
       `id` int(11) DEFAULT NULL,
       `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
       `birthday` datetime DEFAULT NULL
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
     
     INSERT INTO `base`.`user`(`id`, `name`, `birthday`) VALUES (1, 'cmj', '2021-12-13 12:22:28');
     
     CREATE TABLE `user_order` (
       `id` int(11) DEFAULT NULL,
       `order_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
       `order_money` int(255) DEFAULT NULL,
       `user_id` int(11) DEFAULT NULL
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
     
     INSERT INTO `base`.`user_order`(`id`, `order_name`, `order_money`, `user_id`) VALUES (1, '电脑', 100, 1);
     INSERT INTO `base`.`user_order`(`id`, `order_name`, `order_money`, `user_id`) VALUES (2, '手机', 120, 1);
        
    ```
  -  one model mapping
    ```
    mapper:
    <mapper namespace="com.javaoffers.base.modelhelper.sample.spring.mapper.UserMapper">
    	
    	<select id="queryUserDataLimitOne" resultType="model">
        		select * from user limit 1
        </select>
    
    </mapper>
    
    model:
    @BaseModel
    public class User {
    
        @BaseUnique
        private String id;
    
        private String name;
    
        private String birthday;
    
        public String getId() {
            return id;
        }
    
        public void setId(String id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getBirthday() {
            return birthday;
        }
    
        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
   
    }
    
    use:
      User user1 = userMapper.queryUserDataLimitOne();
      LOGUtils.printLog(objectMapper.writeValueAsString(user1));
      //{"id":"1","name":"cmj","birthday":"2021-12-13 12:22:28"}
    
    ```
  -  all model mapping
    ```
     <mapper namespace="com.javaoffers.base.modelhelper.sample.spring.mapper.UserMapper">
     
     	<select id="queryUserData" resultType="model">
     		select * from user
     	</select>
     
     </mapper>
  
     @BaseModel
     public class User {
     
         @BaseUnique
         private String id;
     
         private String name;
     
         private String birthday;
     
         public String getId() {
             return id;
         }
     
         public void setId(String id) {
             this.id = id;
         }
     
         public String getName() {
             return name;
         }
     
         public void setName(String name) {
             this.name = name;
         }
     
         public String getBirthday() {
             return birthday;
         }
     
         public void setBirthday(String birthday) {
             this.birthday = birthday;
         }
     
     }
  
      List<User> users = userMapper.queryUserData();
      LOGUtils.printLog(objectMapper.writeValueAsString(users));
      //[{"id":"1","name":"cmj","birthday":"2021-12-13 12:22:28","orders":null}]
  
    ```
  - one 2 many and many 2 many 
    ```
    <mapper namespace="com.javaoffers.base.modelhelper.sample.spring.mapper.UserMapper">
    
    	<select id="queryUserAndOrder" resultType="model">
    		select a.* , b.id as orderId, b.* from user a left join user_order b on a.id = b.user_id ;
    	</select>
    
    </mapper>
    
 
    @BaseModel
    public class User {
    
        @BaseUnique
        private String id;
    
        private String name;
    
        private String birthday;
    
        private List<UserOrder> orders;
    
        public String getId() {
            return id;
        }
    
        public void setId(String id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getBirthday() {
            return birthday;
        }
    
        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    
        public List<UserOrder> getOrders() {
            return orders;
        }
    
        public void setOrders(List<UserOrder> orders) {
            this.orders = orders;
        }
    }

    @BaseModel
    public class UserOrder {
    
        @BaseUnique
        private int orderId;
    
        private String orderName;
        private String orderMoney;
    
        public int getOrderId() {
            return orderId;
        }
    
        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    
        public String getOrderName() {
            return orderName;
        }
    
        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }
    
        public String getOrderMoney() {
            return orderMoney;
        }
    
        public void setOrderMoney(String orderMoney) {
            this.orderMoney = orderMoney;
        }
    }
    
     User user2 = userMapper.queryUserAndOrderOne();
     LOGUtils.printLog(objectMapper.writeValueAsString(user2));
    
    [{
    	"id": "1",
    	"name": "cmj",
    	"birthday": "2021-12-13 12:22:28",
    	"orders": [{
    		"orderId": 1,
    		"orderName": "电脑",
    		"orderMoney": "100"
    	}, {
    		"orderId": 2,
    		"orderName": "手机",
    		"orderMoney": "120"
    	}]
    }]    
    ```
- 使用方法
  - 注解使用
    ```
    该插件主要给予注解进行使用，注解只有两个分别为@BaseModel和@BaseUnique。 @BaseModel 表示此类是一model类，那么该插件才进行解析。
    @BaseUnique 表示数据的唯一属性，比如主键，唯一索引等，并且在sql中要体现出来即可。在使用时只需要有一个能确认唯一性字段的即可，例如
    在model类中存在主键和唯一索引，那么只需要在其中的一个属性上使用即可，并不需要主键和唯一索引都标记@BaseUnique。（当然如果你都进行
    标识也没有问题注意model类一定要有@BaseUnique）映射场景：一对一：通常为model 类中存在另一model类并作为属性。通常称为子model。例如：
    @BaseModel
    public class User{
        @BaseUnique
        String userId;//用户id
        Card card; //身份证， 一对一
        
    }
    
    @BaseModel
    public class Card{
        @BaseUnique
        String cardId;//身份证id
        String cardNum; //省份证号
    }
    
    以上就是一对一映射。
    一对多和多对多场景用java标识则为类与集合的关系，所以代码如下：
     @BaseModel
     public class User{
        @BaseUnique
        String userId;//用户id
        List<Order> orders;// 类与集合（一对多，多对多）。
     }
    
    @BaseModel
    public class Order{
        @BaseUnique
        String orderId;//订单id
        String cardName; //订单名称
    }    
    ```         
  - 注解使用注意点
    ```
    主model和子model中的 @BaseUnique 对应的属性名称一定要不同。
    ``` 
  
  - 类型转换
    ```
    该插件存在大量的类型转换器。比如： Date类型转换为String类型，默认格式化为yyyy-MM-dd HH:mm:ss ,数字类型也可转换成Date类型。
    类型转换支持如下：
        String2DoubleConvert (com.javaoffers.batis.modelhelper.convert)
        DateOne2DateTwoConvert (com.javaoffers.batis.modelhelper.convert)
        String2DateConvert (com.javaoffers.batis.modelhelper.convert)
        Boolean2StringConvert (com.javaoffers.batis.modelhelper.convert)
        Date2OffsetDateTimeConvert (com.javaoffers.batis.modelhelper.convert)
        Date2LongConvert (com.javaoffers.batis.modelhelper.convert)
        Number2SQLDateConvert (com.javaoffers.batis.modelhelper.convert)
        String2ByteConvert (com.javaoffers.batis.modelhelper.convert)
        Number2DateConvert (com.javaoffers.batis.modelhelper.convert)
        Date2LocalDateTimeConvert (com.javaoffers.batis.modelhelper.convert)
        String2LocalDateConvert (com.javaoffers.batis.modelhelper.convert)
        String2OffsetDateTimeConvert (com.javaoffers.batis.modelhelper.convert)
        Number2StringConvert (com.javaoffers.batis.modelhelper.convert)
        String2FloatConvert (com.javaoffers.batis.modelhelper.convert)
        Date2StringConvert (com.javaoffers.batis.modelhelper.convert)
        String2ShortConvert (com.javaoffers.batis.modelhelper.convert)
        PrimitiveNumber2PrimitiveNumberConvert (com.javaoffers.batis.modelhelper.convert)
        String2LongConvert (com.javaoffers.batis.modelhelper.convert)
        String2CharConvert (com.javaoffers.batis.modelhelper.convert)
        Character2StringConvert (com.javaoffers.batis.modelhelper.convert)
        String2IntegerConvert (com.javaoffers.batis.modelhelper.convert)
        Number2LocalDateConvert (com.javaoffers.batis.modelhelper.convert)
        Number2PrimitiveConvert (com.javaoffers.batis.modelhelper.convert)
        String2LocalDateTimeConvert (com.javaoffers.batis.modelhelper.convert)
        Date2LocalDateConvert (com.javaoffers.batis.modelhelper.convert)
        String2SQLDateConvert (com.javaoffers.batis.modelhelper.convert)
        String2BigDecimalConvert (com.javaoffers.batis.modelhelper.convert)
        String2BigIntegerConvert (com.javaoffers.batis.modelhelper.convert)
        Number2LocalDateTimeConvert (com.javaoffers.batis.modelhelper.convert)
    ```

- English description
## mybatis model helper

-Summary

```

This plug-in mainly solves the problem of mybatis entity mapping configuration. We know that entity mapping through mybatis requires a lot of XML tag configuration, although some plug-ins help survive

However, it is still a chicken rib. Each addition or modification requires a new home XML Mapping tag. Although mybatis comes with its own annotations, it is still troublesome to use. Then the plug-in master

To solve these problems.

```

-Usage environment MVN

```


```

-Use case

- sql

```

CREATE TABLE `user` (

`id` int(11) DEFAULT NULL,

`name` varchar(255) COLLATE utf8_ bin DEFAULT NULL,

`birthday` datetime DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_ bin;


INSERT INTO `base`.` user`(`id`, `name`, `birthday`) VALUES (1, 'cmj', '2021-12-13 12:22:28');


CREATE TABLE `user_ order` (

`id` int(11) DEFAULT NULL,

`order_ name` varchar(255) COLLATE utf8_ bin DEFAULT NULL,

`order_ money` int(255) DEFAULT NULL,

`user_ id` int(11) DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_ bin;


INSERT INTO `base`.` user_ Order ` (` ID `, ` order_name `, ` order_money `, ` user_id `) values (1, 'computer', 100, 1);

INSERT INTO `base`.` user_ Order ` (` ID `, ` order_name `, ` order_money `, ` user_id `) values (2, 'mobile', 120, 1);


```

- one model mapping

```

mapper:

<mapper namespace="com.javaoffers.base.modelhelper.sample.spring.mapper.UserMapper">


<select id="queryUserDataLimitOne" resultType="model">

select * from user limit 1

</select>


</mapper>


model:

@BaseModel

public class User {


@BaseUnique

private String id;


private String name;


private String birthday;


public String getId() {

return id;

}


public void setId(String id) {

this. id = id;

}


public String getName() {

return name;

}


public void setName(String name) {

this. name = name;

}


public String getBirthday() {

return birthday;

}


public void setBirthday(String birthday) {

this. birthday = birthday;

}


}


use:

User user1 = userMapper. queryUserDataLimitOne();

LOGUtils. printLog(objectMapper.writeValueAsString(user1));

//{"id":"1","name":"cmj","birthday":"2021-12-13 12:22:28"}


```

- all model mapping

```

<mapper namespace="com.javaoffers.base.modelhelper.sample.spring.mapper.UserMapper">


<select id="queryUserData" resultType="model">

select * from user

</select>


</mapper>


@BaseModel

public class User {


@BaseUnique

private String id;


private String name;


private String birthday;


public String getId() {

return id;

}


public void setId(String id) {

this. id = id;

}


public String getName() {

return name;

}


public void setName(String name) {

this. name = name;

}


public String getBirthday() {

return birthday;

}


public void setBirthday(String birthday) {

this. birthday = birthday;

}


}


List<User> users = userMapper. queryUserData();

LOGUtils. printLog(objectMapper.writeValueAsString(users));

//[{"id":"1","name":"cmj","birthday":"2021-12-13 12:22:28","orders":null}]


```

- one 2 many and many 2 many

```

<mapper namespace="com.javaoffers.base.modelhelper.sample.spring.mapper.UserMapper">


<select id="queryUserAndOrder" resultType="model">

select a.* , b.id as orderId, b.* from user a left join user_ order b on a.id = b.user_ id ;

</select>


</mapper>



@BaseModel

public class User {


@BaseUnique

private String id;


private String name;


private String birthday;


private List<UserOrder> orders;


public String getId() {

return id;

}


public void setId(String id) {

this. id = id;

}


public String getName() {

return name;

}


public void setName(String name) {

this. name = name;

}


public String getBirthday() {

return birthday;

}


public void setBirthday(String birthday) {

this. birthday = birthday;

}


public List<UserOrder> getOrders() {

return orders;

}


public void setOrders(List<UserOrder> orders) {

this. orders = orders;

}

}


@BaseModel

public class UserOrder {


@BaseUnique

private int orderId;


private String orderName;

private String orderMoney;


public int getOrderId() {

return orderId;

}


public void setOrderId(int orderId) {

this. orderId = orderId;

}        