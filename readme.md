中文版： https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/readme2.md
## mybatis model helper

- Summary
  ```
  This plugin mainly addresses mybatis entity mapping configuration, we know that entity mapping through MyBatis 
  requires a lot of XML tag configuration, although there are some plugins to help survive, but it's still a no-no, 
  requiring new XML mapping tags every time you add or modify. Mybatis comes with annotations, but it's still a 
  hassle to use. Then the plug-in master To solve these problems.
  ```
- Use the environment MVN
  ```
    <dependency>
      <groupId>com.javaoffers</groupId>
      <artifactId>model-helper</artifactId>
      <version>1.0.1</version>
    </dependency>
  
    <dependency>
      <groupId>com.javaoffers</groupId>
      <artifactId>model-spring-suport</artifactId>
      <version>1.0.1</version>
    </dependency>
  
  ```    
- Use Cases
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
    
- Usage

- Annotation usage
```
The plugin is mainly used with annotations, only two of which are @BaseModel and @BaseUnique.
 @BaseModel indicates that this class is a Model class, which is then parsed by the plug-in.

@BaseUnique indicates the unique attribute of the data, such as primary key, unique index, and 
so on, and must be reflected in SQL. You only need to have a unique field, for example

If there are primary keys and unique indexes in the Model class, you only need to use them on 
one of the attributes. You don't need the @BaseUnique tag for both primary keys and unique indexes.
(If you do it all, of course Note that the model class must have @BaseUnique) mapping scenario: 
One-to-one: Usually, another Model class exists in the Model class as an attribute. Often referred
to as a child model. Such as:

@BaseModel
public class User{

    @BaseUnique
    String userId; // user id
    
    Card card; // Id card, one to one

}


@BaseModel
public class Card{

    @BaseUnique
    String cardId; // Id id
    
    String cardNum; // Province certificate number

}


So that's the one-to-one mapping.
One-to-many and many-to-many scenarios are class-collection relationships identified by Java, 
so the code is as follows:

@BaseModel
public class User{

    @BaseUnique
    String userId; // user id
    
    List orders; // Classes and sets (one-to-many, many-to-many).

}


@BaseModel
public class Order{

    @BaseUnique
    String orderId; // order id
    
    String cardName; // Order name

}

```

- Notes use note points

```
The attribute name for @BaseUnique in the primary model and child model must be different.The resulttype in mapper must be model
```


- Type conversion
```
The plug-in has a large number of type converters. For example, the Date type is converted 
to String, and the default format is YYYY-MM-DD HH: MM :ss. The number type can also be converted to Date.

Type conversion is supported as follows:

    String2DoubleConvert (com.javaoffers.batis.modelhelper.convert)
    DateOne2DateTwoConvert (com.javaoffers.batis.modelhelper.convert)
    String2DateConvert (com.javaoffers.batis.modelhelper.convert)
    Boolean2StringConvert (com.javaoffers.batis.modelhelper.convert)
    Date2OffsetDateTimeConvert (com.javaoffers.batis.modelhelper.convert)
    Date2LongConvert (com.javaoffers.batis.modelhelper.convert)
    Number2SQLDateConvert (com.javaoffers.batis.modelhelper.convert)
    String2ByteConvert (com.javaoffers.batis.modelhelper.convert)
    ByteArray2StringConvert2 (com.javaoffers.batis.modelhelper.convert)
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
    ByteArray2StringConvert (com.javaoffers.batis.modelhelper.convert)
    String2BigDecimalConvert (com.javaoffers.batis.modelhelper.convert)
    String2BigIntegerConvert (com.javaoffers.batis.modelhelper.convert)
    Number2LocalDateTimeConvert (com.javaoffers.batis.modelhelper.convert)


```    
    
    