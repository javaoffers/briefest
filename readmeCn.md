
# brief
<p>
<code>brief</code> 是一款高性能、轻量级、简单易用, 零配置的orm框架. 让复杂的屎山sql消失、开发效率最大化、代码量更少且阅读性更高、维护性可持续。 
这是<code>brief</code>存在的原因. <code>brief</code>带你体验前所未有的丝滑.<img src="https://5b0988e595225.cdn.sohucs.com/images/20171206/5b69749fcaf34927872b15e21b86f44c.gif" width="20px">
</p>

### 简介
<p>
 简化开发。让编写 SQL 就像编写 Java 代码一样。这里我们称之为JQL。并形成一套JQL API流程来降低SQL错误率。 JQL 旨在将复杂的 SQL 分解为简单的 SQL，这是开发brief的核心。
  <code>brief</code> 支持多表join并且不需要任何映射配置。 brief支持新的书写格式。在<code>Mapper</code> default方法中可以直接操作JQL API（前提是继承了<code>BriefMapper</code>）。 
  集成了brief功能，可以直接使用 api。让我用Java流写JQL，提高开发效率。更少的代码和更流畅的写作。 性能是mybatis的2倍.
</p>

## 单独使用brief
<p>
<code>brief-speedier</code> 可以单独使用. 不依赖任何环境. 
使用案例： https://github.com/javaoffers/brief/blob/develop/brief-sample/src/main/java/com/javaoffers/base/modelhelper/sample/speedier/BriefSpeedierSample.java
</p>

- maven

  ```java
    <properties>
         <brief.version>3.6.2</brief.version>
    </properties>
   <!--brief轻量级不依赖任何框架-->
     <dependency>
         <groupId>com.javaoffers</groupId>
         <artifactId>brief-speedier</artifactId>
         <version>${brief.version}</version>
     </dependency>
   ```

   ```java
    BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
    BriefMapper<User> userBriefMapper = speedier.newDefaultBriefMapper(User.class);
    userList = userBriefMapper.select().colAll().where().limitPage(1, 10).exs();
    print(userList);
   ```

## brief增强mybatis
<p>
<code>brief-mybatis</code> 是对 <code>mybatis</code>增强,让 <code>mybatis</code> 拥有brief能力。 所以<code>brief-mybatis</code> 完全兼容 <code>mybatis</code>.
如果你的项目中使用的是<code>mybatis</code> 那么你可以直接引入 <code>brief-mybatis</code> 依赖即可. 只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑。无需任何配置。
只需要让你的 <code>Mapper</code>类继承<code>BriefMapper</code>即可使用brief特性. 
</p>

- maven

  ```
   <!--brief对mybatis增强-->
   <dependency>
       <groupId>com.javaoffers</groupId>
       <artifactId>brief-mybatis</artifactId>
       <version>${brief.version}</version>
   </dependency>

  ```

## brief-spring-boot-start
<p>
待支持，支持<code>spring-boot</code>. 如果你的spring-boot项目引用了 <code>mybatis</code> 框架， 那么你只需要引入 <code>brief-mybatis</code> 对mybatis增强即可.
</p>

## brief 功能介绍
- 特征
  - 高性能查询和插入
  - 支持分库分表 (下个版本支持)
  - 支持多数据源切换 (下个版本支持)
  - 不必编写本机 SQL。可以按照Java的stream api来写。
  - SQL函数注解化，简单易用
  - 新的写法，支持mapper接口类写default默认方法。
  - 强大的自动类型转换功能。
  - 插入/更新自动识别优化为批处理执行
  - 提供可选自动识别差异数据实时更新能力
  - 多表查询不需要配置。自动映射一对一，一对多，多对多。
  - 支持逻辑删除、乐观锁。
  - 集成了常用的API，无需开发即可直接使用。
  - 支持mysql语法标准
  - 表字段自动加解密（支持like模糊查询）.
  - 字段查询模糊脱敏
  - sql拦截器， 可自由定制
  - sql过滤器，可自由定制
  - 慢sql监控. 可自定义慢sql处理。
  - 支持json字段.

  
- 项目实战，已在内部进行了使用。效果非常好. 
![](note-doc/img/2220967059897.png)


### 基础使用    
#### 查询操作
 <p>
在看操作之前，我们先看一下数据结构：这里有两个关键的注解。 @BaseModel用于表示该类属于模型类（类名与表名相同，ModelHelp最终会将驼峰式类名转换为下划线表名，属性相同），@ BaseUnique表示类中唯一的属性（对应A unique attribute in a table，当表中使用联合主键时可以是多个）。我们将在最后详细解释注解的使用。下面是基本使用
 </p>
 
 ```java
@BaseModel
public class User {

    @BaseUnique
    private Long id;

    private String name;

    private String birthday;
    
    private Work work;
    
    private IsDel isDel;
    
    private Version version;  

    @CaseWhen(whens = {
            @CaseWhen.When(when = "money < 10", then = "'pool'"),
            @CaseWhen.When(when = "money > 10000", then = "'rich'")},
            elseEnd = @CaseWhen.Else("'civilian'")
    )
    private String moneyDes;    

    private List<UserOrder> orders;

    // .... getter setter
}

@BaseModel
public class UserOrder {

    @BaseUnique
    private int id;
    private String orderName;
    private String orderMoney;
      
    //getter, setter  methods  
} 


```

##### 全表查询
 ```java

 List<User>  users = crudUserMapper 
                    .select() 
                    .colAll() 
                    .where() 
                    .exs(); 

 ```
 
  <p>
这个 JQL 最终会被翻译为 select id, name, xxx..  from user。这里的colall是查询所有表字段的意思。如果要查询指定的字段，比如姓名和生日字段，可以这样做： 
</p>

##### 查询指定的表字段 
 ```java
 List<User> users = crudusermapper
                    .select()
                    .col (user:: getbirthday)
                    .col (user:: getname)
                    .where()
                    .exs();
 ```
 
##### 指定条件查询 
 <p>
可以通过col()指定要查询的字段。这里的where()和SQL中的关键字where是一样的。比如要查询一个id值为1的用户，可以这样写：
 </p>
 
 ```java
 User user = crudusermapper
             .select() 
             .colAll() 
             .where() 
             .eq(User::getId, 1) 
             .ex();
 ```

##### 分页查询

```java
 int pageNum = 1;
 int pageSize = 10;
 List<User> users = crudusermapper
                    .select()
                    .col (user:: getbirthday)
                    .col (user:: getname)
                    .where()
                    .limitPage(1, 10)// 1: 第一页， 查询10条数据
                    .exs();
 
```

##### 统计查询
```java
List<User> users = this.crudUserMapper
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
```

 <p>
你会发现有两个特殊的函数exs()，ex()这两个函数代表触发执行。 exs()通常用于查询更多的数据，返回结果为list，而ex()用于只返回一个结果T； JQL 必须通过才能触发 where 和 ex/exs 。大多数工作场景下，WHERE后面都会加上过滤条件，除了专门统计所有表数据，这样设计也是很好的提醒大家记得填写WHERE条件，当然如果你不需要加任何WHERE条件为了查询所有表数据，可以使用where().ex(),where().exs()
 </p>  
 <p>
  更多复杂查询案例：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/brief-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperSelete.java
 </p>

#### 插入操作

```java
Id exOne = crudUserMapper
                .insert()
                .col(User::getBirthday, new Date())
                .col(User::getName, "Jom")
                .ex();
```
<p>
一个简单的insert语句，返回一个wrapper class Id，通常是新插入数据的主键。一个插入操作就这么简单。还有一种更简单的插入数据的方法。插入对象。并支持多个。
编队逻辑针对批处理进行了优化。例如下面的案例
</p>

```java
        User user = User.builder().name("Jom1").birthday(date).build();
        
        List<Id> ex = crudUserMapper
                      .insert()
                      .colAll(user)
                      .ex();
        print(ex);
```

<p>

  我们可以插入整个模型对象，表示要查询所有字段，对层进行批处理。性能非常好。
  更多案例请参考：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/brief-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperInsert.java

</p>

#### 更新操作
<p>
允许更新空值updateNull、不允许更新空值npdateNull、存在则更新否则插入，乐观锁版本更新、batch更新、
请看下面的案例
</p>

```java
crudUserMapper
        .update().npdateNull()
                 .col(User::getBirthday, new Date())
                 //名称不会更新。因为它的 npdateNull
                 .col(User::getName,null)
                 .where()
                 .eq(User::getId, id)
                 .ex();

crudUserMapper
        .update().updateNull()
                 .col(User::getBirthday, new Date())
                 //名称将更新。因为它的 updateNull
                 .col(User::getName,null)
                 .where()
                 .eq(User::getId, id)
                 .ex();

this.crudUserMapper.general().saveOrModify(user);

this.crudUserMapper.general().saveOrUpdate(user);

this.crudUserMapper.general().vsModifyById(user);

this.crudUserMapper.general().modifyBatchById(user);

this.crudUserMapper.general().updateBatchById(user);

```

<p>
通过上面的案例，我们可以在业务中很好的控制字段的更新。
</p>

#### 追踪差异更新
<p>
    可以实时追踪差异更新. 当model数据发生变化时进行自动更新. 想使用此功能只需要将<code>@BaseModel</code>
    的autoUpdate = true. 开启后查询的每一条带有主键id的数据都是具有差异更新能力的. 在操作的过程中如果将
    主键id设置为null, 则model数据将失去差异更新能力并且不会回复即使你还原了主键id.  
</p>

```java

   @Data
   @BaseModel(value = "encrypt_data",autoUpdate = true)
   public class EncryptDataAutoUpdate {
   
       @BaseUnique
       private Integer id;
   
       private String encryptNum;
   }
   EncryptDataAutoUpdate encryptData = new EncryptDataAutoUpdate();
   encryptData.setEncryptNum("12345678");
   Id id = autoUpdateBriefMapper.general().save(encryptData);
   EncryptDataAutoUpdate autoUpdate = autoUpdateBriefMapper.general().queryById(id);
   print(autoUpdate);
   //Not updated, because there is no difference
   autoUpdate.setEncryptNum("12345678");
   //Will be updated
   autoUpdate.setEncryptNum("87654321900");
   print(autoUpdate);
   
   encryptData = this.autoUpdateBriefMapper.general().queryById(id);
   print(encryptData);
   //Cancel the differences to update
   encryptData.setId(null);
   //Not updated, as has already been canceled difference update functionality;
   encryptData.setEncryptNum("098712345");

```

#### 删除操作
<p>
<code>brief</code>支持丰富的删除功能. 同时还支持逻辑删除. 使用逻辑删除需要在<code>User</code>中使用IsDel 枚举即可.

</p>

```java
 
crudUserMapper.delete()
               .where()
               .eq(User::getId, id)
               .eq(User::getName, 'xxx')
               .ex();   
this.crudUserMapper.general().remove(user);
this.crudUserMapper.general().removeById(id);
this.crudUserMapper.general().removeByIds(id1,id2,id3);
this.crudUserMapper.general().removeByIds(idList);
this.crudUserMapper.general().logicRemove(user);
this.crudUserMapper.general().logicRemoveById(id);
```


#### Mapper接口中支持default编写jql/sql
<p>
一种新的编码方式。我们可以在 <code>Mapper</code> 接口中编写默认方法。用于集中管理 jql/sql. 防止项目中出现到处都是jql/sql.
例如下面的案例(我们推荐使用这种风格).
</p>

```java
public interface CrudUserMapper extends BriefMapper<User> {

    default User queryUserById(Number id){
        return select()
                .colAll()
                .where()
                .eq(User::getId, id)
                .ex();
    }
   
}
```

<p>
当我的接口继承了<code>BriefMapper</code>  接口后，我们就可以默认编写我们的JQL逻辑了。这避免了传统的在 <code>Mapper</code> 接口上编写原生 SQL 语句的方法。.
更多案例请查看:https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/brief-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/mapper/BriefUserMapper.java
</p>

- 演示 crud:
  - demo ：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/brief-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring
    
#### 多表join 
- 这部分主要介绍如何使用JQL来表达一些复杂的查询语句. 多表进行join不需要任何配置（零配置）. 
<p>
  在上面的基础部分，我们解释了一些常见和最基本的用途。接下来，我们将介绍一些实际项目中的场景。一些稍微复杂的用例。主要包括连接查询、分组查询、统计查询和常用的通用操作。
</p>

<p>
    JQL 提供了丰富的常用 API。比如 >= , <= , in , between, like, likeLeft, likeRight, exists等。还有一个combination unite，主要是把多个条件组合成一个，比如(xx > xx or xx < xx )把两个关联条件为一。同时我们让你写原生sql的入口，比如col(sql), condSQL(sql)，虽然我们通常不推荐使用原生sql。因为尽量不要用sql进行复杂的逻辑处理，比如截取一些字符串。或者拼接等，这些操作建议在业务层处理。先来看一个简单的join JQL案例：推荐在接口类中写JQL
</p>

```java
public interface CrudUserMapper extends BriefMapper<User> {
    
    default List<User> queryAllAndOrder(){
        return   select()
                .colAll()
                .leftJoin(UserOrder::new)
                .colAll()
                .on()
                .oeq(User::getId,UserOrder::getUserId)
                .where()
                .exs();
    }
}
```


##### use left join , group by , limitPage  
  
      
```java
 crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                //OXX The beginning indicates the relationship between two tables
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //Group by main table
                .groupBy(User::getName, User::getId)
                //Group according to sub-table
                .groupBy(UserOrder::getUserId)
                //1:pageNum,10:pageSize
                .limitPage(1,10)
                .exs();

```

####  通用API

<p>
    我封装了一些常用的功能，使用起来非常简单。而且代码也非常简洁明了。例如通过 id 查询或更改。
</p>

<p>
    常用的api只需要调用general()方法即可使用。比如通过id查询数据
</p>

```java
//query by id
User user = crudUserMapper.general().queryById(id);
```

<p>
    save api，保存一个对象到数据库
</p>

```java
 User user = User.builder().name("general").build();
 //save
 long saveId = crudUserMapper.general().save(user);
```

<p>
    通过id删除指定数据
</p>

```java
crudUserMapper.general().removeById(1);
```

<p>
   比较简单常用的API如下。
</p>

```java

     /**
         * save model
         * @param model class
         * @return primary key id
         */
        public Id save(T model);
    
        /**
         * save or modify.
         * sql :  insert into on duplicate key update
         * @param model class
         * @return  primary key id. or modify count num. so return void
         */
        public void saveOrModify(T model);
    
        /**
         * save or update.
         * By the @UniqueId field to query data, if the query not null then to update, or to insert.
         * @param model class
         * @return  primary key id. or modify count num. so return void
         */
        public void saveOrUpdate(T model);
    
        /**
         * save or replace
         * sql: replace into
         * @param model class
         * @return   primary key id. or modify count num. so return void
         */
        public void saveOrReplace(T model);
    
        /**
         * save model
         * @param models class
         * @return primary key ids
         */
        public List<Id> saveBatch(Collection<T> models);
    
        /**
         * save or modify.
         * sql :  insert into on duplicate key update
         * @param models class
         * @return primary key id. or modify count num. so return void
         */
        public void saveOrModify(Collection<T> models);
    
        /**
         * save or update.
         * By the @UniqueId field to query data, if the query not null then to update, or to insert.
         * @param models class
         * @return  primary key id. or modify count num. so return void
         */
        public void saveOrUpdate(Collection<T> models);
    
        /**
         * save or replace
         * sql: replace into
         * @param models class
         * @return primary key id. or modify count num. so return void
         */
        public void saveOrReplace(Collection<T> models);
    
        /**
         * delete model.Where conditions will be generated based on properties of the model
         * class for which there is a value.
         * Note that this is a physical deletion
         * @param model
         */
        public int remove(T model);
    
        /**
         * delete model by id
         * Note that this is a physical deletion
         */
        public int removeById(Serializable id );
    
        /**
         * delete model by ids
         * Note that this is a physical deletion
         */
        public int removeByIds(Serializable... ids );
    
        /**
         * delete model by ids
         * Note that this is a physical deletion
         */
        public <ID extends Serializable> int removeByIds(Collection<ID> ids);
    
        /**
         * logic delete model.Where conditions will be generated based on properties of the model
         * class for which there is a value.
         * {@link IsDel}
         * {@link RowStatus}
         * @param model
         */
        public int logicRemove(T model);
    
        /**
         * logic delete model by id
         * {@link IsDel}
         * {@link RowStatus}
         */
        public int logicRemoveById(Serializable id );
    
        /**
         * logic delete model by ids
         * {@link IsDel}
         * {@link RowStatus}
         */
        public int logicRemoveByIds(Serializable... ids );
    
        /**
         * logic delete model by ids
         * {@link IsDel}
         * {@link RowStatus}
         */
        public <ID extends Serializable> int logicRemoveByIds(Collection<ID> ids);
    
        /**
         * Update the model, note that the update condition is the property marked with the Unique annotation.
         * Only properties with values ​​are updated.
         * In other words, the @BaseUnique annotation will generate a Where condition, and other non-null properties will
         * generate a set statement.
         * 支持版本更新
         * @param model model
         * @return The number of bars affected by the update
         */
        public int modifyById(T model);
    
        /**
         * Update the model, note that the update condition is the property marked with the Unique annotation.
         * Only properties with values ​​are updated.
         * In other words, the @BaseUnique annotation will generate a Where condition, and the field will
         * generate a set statement
         * @param model model
         * @return The number of bars affected by the update
         */
        public int updateById(T model);
    
        /**
         * batch update. Empty fields will not be able to update the database.
         * @param models models
         * @return Affect the number of bars
         */
        public int modifyBatchById(Collection<T> models);
    
        /**
         * batch update ,Will update the database if the field is empty.
         * @param models models
         * @return Affect the number of bars
         */
        public int updateBatchById(Collection<T> models);
        
        /**
         * Support version update.
         * Update the model, note that the update condition is the property marked with the Unique annotation.
         * Only properties with values ​​are updated.
         * In other words, the @BaseUnique annotation will generate a Where condition, and other non-null properties will
         * generate a set statement.
         * @param model model
         * @return The number of bars affected by the update
         */
        public int vsModifyById(T model);
    
        /**
         * Support version update.
         * Update the model, note that the update condition is the property marked with the Unique annotation.
         * Only properties with values ​​are updated.
         * In other words, the @BaseUnique annotation will generate a Where condition, and the field will
         * generate a set statement
         * @param model model
         * @return The number of bars affected by the update
         */
        public int vsUpdateById(T model);
    
        /**
         * Support version update.
         * batch update. Empty fields will not be able to update the database.
         * @param models models
         * @return Affect the number of bars
         */
        public int vsModifyByIds(Collection<T> models);
    
        /**
         * Support version update.
         * batch update ,Will update the database if the field is empty.
         * @param models models
         * @return Affect the number of bars
         */
        public int vsUpdateByIds(Collection<T> models);
    
        /**
         * Query the main model, be careful not to include child models. Non-null properties will generate a where statement.
         * <>Note that properties such as Collection<Model> will be ignored, even if they are not null </>
         * @param model model
         * @return return query result
         */
        public List<T> query(T model);
    
        /**
         * Query the main model, be careful not to include child models. Non-null properties will generate a where statement.
         * <>Note that properties such as Collection<Model> will be ignored, even if they are not null </>
         * @param model model
         * @param pageNum page number
         * @param pageSize Number of bars displayed per page
         * @return return query result
         */
        public List<T> query(T model,int pageNum,int pageSize);
    
        /**
         * Paging query full table data
         * @param pageNum page number, If the parameter is less than 1, it defaults to 1
         * @param pageSize Number of bars displayed per page， If the parameter is less than 1, it defaults to 10
         * @return return query result
         */
        public List<T> query(int pageNum,int pageSize);
    
        /**
         * query by id
         * @param id primary key id
         * @return model
         */
        public T queryById(Serializable id);
    
        /**
         * query by id
         * @param ids primary key id
         * @return model
         */
        public List<T> queryByIds(Serializable... ids);
    
        /**
         * query by id
         * @param ids primary key id
         * @return model
         */
        public <ID extends Serializable>  List<T> queryByIds(Collection<ID> ids);
    
        /**
         * query by id
         * @param ids primary key id
         * @return model
         */
        public <ID extends Serializable> List<T> queryByIds(List<ID> ids);
    
        /**
         * query by id
         * @param ids primary key id
         * @return model
         */
        public <ID extends Serializable> List<T> queryByIds(Set<ID> ids);
    
    
        /**
         * Map<String,Object>. String: Field names of the table. The value corresponding to the Object field
         * @param param Parameters. key database field name, value field value
         * @return model
         */
        public List<T> queryByParam(Map<String,Object> param);
    
        /**
         * Map<String,Object>. String: Field names of the table. The value corresponding to the Object field
         * @param param Parameters. key database field name, value field value
         * @param pageNum page number
         * @param pageSize Number of bars displayed per page
         * @return model
         */
        public List<T> queryByParam(Map<String,Object> param,int pageNum,int pageSize);
    
        /**
         * The number of statistical tables
         * @return not null
         */
        public Number count();
    
        /**
         * The number of statistical tables, through the specified field
         * @return not null
         */
        public Number count(C c);
    
        /**
         * The number of statistical tables, through the specified field
         * Statistical results after deduplication. count(DISTINCT c)
         * @return not null
         */
        public Number countDistinct(C c);
    
    
        /**
         * The number of statistical tables.  Will use the model as the where condition
         * @return not null
         */
        public Number count(T model);
    
        /**
         * The number of statistical tables, through the specified field.
         * Will use the model as the where condition
         * @return not null
         */
        public Number count(C c,T model);
    
        /**
         * The number of statistical tables, through the specified field
         * Statistical results after deduplication. count(DISTINCT c).
         * Will use the model as the where condition
         * @return not null
         */
        public Number countDistinct(C c,T model);

```
#### sql函数注解
<p>
    我们可以通过在类的字段上使用注解来使用sql函数。以下是一些用例：
</p>

```java
public class FunAnnoParserSample {
    @ColName("name")
    @Left(10)
    private String colName1; //LEFT(name,10)

    @ColName("name")
    @Left(10)
    @Concat( {"age"})
    private String colName2; //CONCAT(LEFT(name,10),age)


    @Left(10)
    @Concat( {"age"})
    private String colName3;//CONCAT(LEFT(colName3,10),age)

    @Now
    @Left(10)
    @Concat( {"age"})
    private String colName4;//CONCAT(LEFT(NOW(),10),age)


    @Concat( {"age"})
    private String colName5;//CONCAT(colName5,age)

    @Now
    @Concat({"age"})
    @Left(10)
    private String colName6;//LEFT(CONCAT(NOW(),age),10)


    @Concat({"age"})
    @Left(10)
    private String colName7;//LEFT(CONCAT(colName7,age),10)

    @Now
    @Left(10)
    private String colName8;//LEFT(NOW(),10)


    @Rand
    private String colName9;//RAND()

    @Rand
    @ColName("name")
    private String colName10;//java.lang.IllegalArgumentException: @ColName and @RAND cannot be used together

    @ColName("name")
    @IfNull("'Amop'")
    private String colName11;//IFNULL(name,'Amop')

    @ColName("sex = 1")
    @If(ep1 = "'boy'",ep2 = "'girl'")
    private String colName12;//IF(sex = 1,'boy','girl')

    /**
     * select if(1,'1','0') output 1
     * select if(0,'1','0') output 0
     */
    @ColName("sex")
    @IfNull("1")
    @If(ep1 = "'boy'", ep2 = "'girl'")
    private String colName13;// IF(IFNULL(sex,1),'boy','girl')

    @ColName("sex")
    @IfEq(eq = "1",ep1 = "'boy'", ep2 = "'girl'")
    private String colName14; //IF(sex = 1,'boy','girl')

    @ColName("money")
    @IfNotNull("'rich'")
    private String colName15; // IF(money is not null ,'rich',null)

    @ColName("money")
    @IfNotNull(value = "'rich'",ifNull = "'poor'")
    private String colName16; //IF(money is not null ,'rich','poor')

    @ColName("money")
    @IfNotNull(value = "'rich'",ifNull = "'poor'")
    @IfEq(eq = "'rich'",ep1 = "'i want to marry him'", ep2 = "'i want to break up with him'")
    private String colName17; //IF(IF(money is not null ,'rich','poor') = 'rich','i want to marry him','i want to break up with him')

    @ColName("money")
    @IfGt(gt = "100000",ep1 = "'rich'", ep2 = "'poor'")
    @IfEq(eq = "'rich'",ep1 = "'i want to marry him'", ep2 = "'i want to break up with him'")
    private String colName18; //IF(IF(money > 100000,'rich','poor') = 'rich','i want to marry him','i want to break up with him')

    @ColName("name")
    @Trim
    private String colName19; //TRIM(name)

    @ColName("name")
    @Concat(value = "'hello'", position = -1)
    private String colName20;//CONCAT('hello',name)

    @ColName("name")
    @Concat(value = "'hello'", position = 1)
    private String colName21; //CONCAT('hello',name)

    @ColName("name")
    @Concat(value = {"'hello'"," 'how are you?' "}, position = 1)
    private String colName22;//  CONCAT('hello',name, 'how are you?' )


    @ColName("name")
    @GroupConcat
    private String colName23;//GROUP_CONCAT( name )

    @ColName("name")
    @GroupConcat(distinct = true)
    private String colName24;//GROUP_CONCAT( distinct name )

    @ColName("name")
    @GroupConcat(distinct = true, orderBy = @GroupConcat.OrderBy(colName = "age",sort = GroupConcat.Sort.ASC) )
    private String colName25;//GROUP_CONCAT( distinct name  order by age ASC)

    @ColName("name")
    @GroupConcat(distinct = true, orderBy = @GroupConcat.OrderBy(colName = "age",sort = GroupConcat.Sort.DESC) ,separator = "-")
    private String colName26;//GROUP_CONCAT( distinct name  order by age DESC separator '-')

    @ColName("name")
    @Concat("age")
    @GroupConcat(distinct = true, orderBy = @GroupConcat.OrderBy(colName = "age",sort = GroupConcat.Sort.DESC) ,separator = "-")
    private String colName27;//GROUP_CONCAT( distinct CONCAT(name,age)  order by age DESC separator '-')
    
    @CaseWhen(whens = {
            @CaseWhen.When(when = "score > 80", then = "'Grand'"),
            @CaseWhen.When(when = "score < 80 and score > 50", then = "'General'"),
            @CaseWhen.When(when = "score < 50 and score > 10", then = "'noGood'"),
    }, elseEnd = @CaseWhen.Else("'VeryBad'"))
    private String scoreDescription;

}
```
#### 自动类型转换
<p>
内置大量常用类型转换器。比如数据库字段birthday是datetime/int、Number/varchar和枚举类之间的转换. 枚举类通常和@EnumValue一起使用,用于标识枚举类唯一的属性,该属性会和表中的字段进行自动关联.(sample of enum : 

https://github.com/javaoffers/brief/blob/develop/brief-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperInsert.java

). 
</p>

```
   String2DoubleConvert  
    DateOne2DateTwoConvert  
    String2DateConvert  
    Boolean2StringConvert  
    Date2OffsetDateTimeConvert  
    Date2LongConvert  
    Number2SQLDateConvert  
    String2ByteConvert  
    ByteArray2StringConvert2  
    Number2DateConvert  
    Date2LocalDateTimeConvert  
    String2LocalDateConvert  
    String2OffsetDateTimeConvert  
    Number2StringConvert  
    String2FloatConvert  
    Date2StringConvert  
    String2BooleanConvert  
    String2ShortConvert  
    PrimitiveNumber2PrimitiveNumberConvert  
    String2LongConvert  
    LocalDate2StringConvert  
    String2CharConvert  
    Character2StringConvert  
    String2IntegerConvert  
    Number2LocalDateConvert  
    Number2PrimitiveConvert  
    String2LocalDateTimeConvert  
    Date2LocalDateConvert  
    String2SQLDateConvert  
    ByteArray2StringConvert  
    String2BigDecimalConvert  
    Number2BooleanConvert  
    String2BigIntegerConvert  
    Number2LocalDateTimeConvert
    Number2EnumConvert
    String2EnumConvert

```

#### 拦截器模式
<p>
sql和参数在真正执行前会被拦截器所拦截. 可以在自己定义的拦截器中进行二次处理。 自定义拦截器非常简单，你只需要实现接口
<code>JqlInterceptor</code>，然后调用 <code>InterceptorLoader.init()</code> 进行初始化自己的拦截器即可。
</p>

```java
 LogInterceptor logInterceptor = new LogInterceptor();
 ArrayList<JqlInterceptor> jqlInterceptors = new ArrayList<>();
 jqlInterceptors.add(logInterceptor);
 InterceptorLoader.init(jqlInterceptors);
```


#### 支持自动加密和解密
<p>
   当我们需要添加一个数据库表中的某些字段进行解密。Mybatis JQL提供了一个简单的配置可以做;
   我们只需要指定一个关键(长度为32个十六进制)。然后指定表和表中的字段。
   我们指定一个私钥 “FFFFFFFFAAAAAAAAAAAAFFFFFAFAFAFA”是关键
   并给出了encrypt_num加密。在表encrypt_data配置如下: 
</p>
  <p>
  加密和解密模块被设计为一个独立的模块。 
  使用这个功能服务,您需要添加mvn引用。如下
  </p> 

```java
<dependency>
  <groupId>com.javaoffers</groupId>
  <artifactId>brief-encipher</artifactId>
  <version>${brief.version}</version>
</dependency>
```

```java
  /**
     * Configure the tables and fields that need to be decrypted.
     * the key Is the length of 32 hexadecimal;
     */
    @AesEncryptConfig(key = "FFFFFFFFAAAAAAAAAAAAFFFFFAFAFAFA", encryptTableColumns = {
            @EncryptTableColumns(tableName = "encrypt_data", columns = {"encrypt_num"})
    })
    @Configuration
    static class EncryptConfig{ }
```
```java
    EncryptData encryptData = new EncryptData();
    String encryptNum = "1234567890";
    encryptData.setEncryptNum(encryptNum);
     //加密后在db里存储的数据是 396195EAF65E740AEC39E6FFF0714542
    Id id = this.crudEncryptDataMapper.general().save(encryptData);
    //查询的时候会自动解密
    encryptDatas = this.crudEncryptDataMapper.general().queryByIds(id); 
    print(encryptDatas); //[{"id":10,"encryptNum":"1234567890"}]
    // 查询时直接指定铭文即可. 铭文查询,底部将转换成密文和查询
    EncryptData ex = this.crudEncryptDataMapper.select().colAll()
    .where().eq(EncryptData::getEncryptNum, encryptNum).ex();
    print(ex);//{"id":10,"encryptNum":"1234567890"}
```

#### 字段脱敏
<p>
支持字段脱敏. 只需要在model类上加上@EmailBlur注解可可以类。 注意被加上的注解的字段必须是String类型.
</p>

```
   @EmailBlur
   private String email; // 12345678@outlook.com加密后的数据为12***678@outlook.com
```  
<p>
更多案例： https://github.com/javaoffers/brief/tree/master/brief-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/blur
</p>

#### Code contributions are welcome
<p>
该项目已在内部使用。大大提高了开发效率和代码整洁度。如果觉得不错，请点个小星星鼓励一下
</p>
