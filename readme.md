## mybatis model helper
- 概要
  ```
  该插件主要解决mybatis实体映射配置，我们知道通过mybatis进行实体映射时需要大量的xml标签配置，虽有有一些插件帮助生存
  ，但是仍然很鸡肋，每次增加或修改都需要新家xml映射标签。虽然mybatis自带一下注解但是使用起来依然很麻烦。那么该插件主
  要就是解决这些问题的。
  ```
- 使用化境mvn
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
    	
    	<select id="queryUserData" resultType="model">
    		select * from user
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
    
    List<User> userAndOrder = userMapper.queryUserAndOrder();
    LOGUtils.printLog(objectMapper.writeValueAsString(userAndOrder));
    
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