
## mybatis-jql
<p>
让复杂的屎山sql消失, 让开发效率最大化. 让代码量更少， 让代码的阅读性更高。 让项目的维护性可持续。 这是我写<code>mybatis-jql</code>的核心目标.
</p>

## mybatis-jql 和 mybatis的关系
<p>
<code>mybatis-jql</code> 是在 <code>mybatis</code> 之上进行开发. 只做增强不破坏mybaits源码。 所以<code>mybatis-jql</code> 完全兼容 <code>mybatis</code>.
如果你的项目中使用的是<code>mybatis</code> 那么你可以直接引入 <code>mybatis-jql</code> 依赖即可. 只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑。无需任何配置。
</p>

##### QQ群  310283131 技术交流，欢迎提供宝贵的意见。点击star鼓励一下

- 特征
  - 高性能查询和插入
  - 不必编写本机 SQL。可以按照Java的stream api来写。
  - SQL函数注解，简单易用
  - 新的写法，支持mapper接口类写default默认方法。
  - 强大的自动类型转换功能。
  - 插入/更新自动识别优化为批处理执行
  - 多表查询不需要额外配置。自动映射类
  - 集成了常用的API，无需开发即可直接使用。
  - 目前只支持mysql语法标准
  - 表字段自动加解密（支持like模糊查询）.
  - 字段查询模糊脱敏
  - sql拦截器， 可自由定制
  - sql过滤器，可自由定制
  - 慢sql监控. 可自定义慢sql标准。
  
- 概要
  <p>
 简化开发。让编写 SQL 就像编写 Java 代码一样。这里我们称之为JQL。并形成一套JQL API流程来降低SQL错误率。
 JQL 旨在将复杂的 SQL 分解为简单的 SQL，这是开发mybatis-jql的核心。我们不建议加入超过 3 个表。
 这降低了 SQL 的可读性和可维护性。 mybatis-jql支持新的书写格式。默认方法可以写在java接口中，内部可以直接操作JQL API（前提是继承了crudmapper）。
 集成了常用的crud操作，可以直接使用 api。让我用Java流写JQL，提高开发效率。更少的代码和更流畅的写作。
</p>

- 对比市面上流行的框架

|框架|优点|缺点|
|---|---|---|
|mybatis|需要编写原生sql,自由度非常高|半orm, 配置比较多|
|mybatis-plus|集成常用的api， 函数时编程|不支持join查询, 函数式编程需要太多new. 不方便集中管理，在service层会出现大量sql函数表达式|
|mybatis-jql|集成常用的api， 函数时编程, 支持join. 支持在default方法中编写jql. 方便集中管理 | 不支持复杂sql的编写，目前只支持mysql语法|
|fluent-sql|支持函数式编程，支持join，| 需要生成额外的mapper. 函数式需要new.  不能集中管理，在service层会出现大量sql函数表达式|

<p>
mybatis, mybatis-plus, fluent-sql都是比较优秀的框架。我在设计mybatis-jql时也借鉴了他们的优点同时又做了一些取舍。mybatis-jql的核心目标是
写更少的代码， 消除复杂的sql. 提高项目的可维护性。所以mybatis-jql没有任何学习成本。 mybatis-jql不会重复造轮子。比如分库分表这些功能市面上已经有了
（比如：ShardingSphere ）。因此mybatis-jql将不会提供这些重复的功能。
</p>
 
- 项目实战，已在内部进行了使用。效果非常好. 
![](note-doc/img/2220967059897.png)


- maven
  ```java
    <!-- https://mvnrepository.com/artifact/com.javaoffers/mybatis-model-spring-support/3.5.11.4 -->
   <dependency>
       <groupId>com.javaoffers</groupId>
       <artifactId>mybatis-model-spring-support</artifactId>
       <version>3.5.11.12</version>
   </dependency>

  ```
### 基础使用    
- 基本使用教程 
 <p>
    一个普通的查询
 </p>
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
    // .... getter setter
}
```

 ```java

 List<User>  users = crudUserMapper 
                    .select() 
                    .colAll() 
                    .where() 
                    .exs(); 
 ```
 
  <p>
这个 JQL 最终会被翻译为 select * from user。这里的colall是查询所有表字段的意思。如果要查询指定的字段，比如姓名和生日字段，可以这样做： 
</p>
 
 ```java
 List<User> users = crudusermapper
                    .select()
                    .col (user:: getbirthday)
                    .col (user:: getname)
                    .where()
                    .exs();
 ```
 
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
 <p>
在这三种情况下，你会发现有两个特殊的函数exs()，ex()这两个函数代表触发执行。 exs()通常用于查询更多的数据，返回结果为list，而ex()用于只返回一个结果T； JQL 必须通过才能触发 where 和 ex/exs 。大多数工作场景下，WHERE后面都会加上过滤条件，除了专门统计所有表数据，这样设计也是很好的提醒大家记得填写WHERE条件，当然如果你不需要加任何WHERE条件为了查询所有表数据，可以使用where().ex(),where().exs()
 </p>  
 <p>
 
   更多查询案例：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperSelete.java
 </p>

<p>
 正常的插入操作
</p> 

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
  更多案例请参考：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperInsert.java

</p>

<p>
更新操作，更新操作有两种模式，允许更新空值updateNull和不允许更新空值npdateNull，请看下面的案例</p>

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

```

<p>
此方法对于模型对象实例非常有用。比如一个User对象中有些属性有值（not null）有些属性没有值（null），那么没有值的属性是否应该更新呢？可以通过npdateNull（不更新）updateNull（更新属性为null），比如下面这种情况</p>

```java
    public void testUpdateUser(){
        
        User user = User.builder().name("Jom").birthday(null).build();
        //npdateNull, birday null will not be updated to the database
        crudUserMapper.update().npdateNull()
                .colAll(user)
                .where()
                .eq(User::getId,1)
                .ex();
        
        //updateNull， birday null will be updated to the database, the value of birday will be changed to null
        crudUserMapper.update().updateNull()
                .colAll(user)
                .where()
                .eq(User::getId, 1)
                .ex();
    }

```

<p>
通过上面的案例，我们可以在业务中很好的控制字段的更新。当我使用模型类时。
</p>



<p>
一种新的编码方式。我们可以在 Mapper 接口中编写默认方法。例如下面的案例我们推荐使用这种风格
</p>

```java
public interface CrudUserMapper extends CrudMapper<User> {

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
当我的接口继承了CrudMapper接口后，我们就可以默认编写我们的JQL逻辑了。这避免了传统的在 Mapper 接口上编写原生 SQL 语句的方法。.
更多案例请查看:https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/mapper/CrudUserMapper.java
</p>

- 演示 crud:
  - demo ：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring
    
#### 进阶 
- 这部分主要介绍如何使用JQL来表达一些复杂的查询语句
<p>
  在上面的基础部分，我们解释了一些常见和最基本的用途。接下来，我们将介绍一些实际项目中的场景。一些稍微复杂的用例。主要包括连接查询、分组查询、统计查询和常用的通用操作。
</p>

<p>
    JQL 提供了丰富的常用 API。比如 >= , <= , in , between, like, likeLeft, likeRight, exists等。还有一个combination unite，主要是把多个条件组合成一个，比如(xx > xx or xx < xx )把两个关联条件为一。同时我们让你写原生sql的入口，比如col(sql), condSQL(sql)，虽然我们通常不推荐使用原生sql。因为尽量不要用sql进行复杂的逻辑处理，比如截取一些字符串。或者拼接等，这些操作建议在业务层处理。先来看一个简单的join JQL案例：推荐在接口类中写JQL
</p>

```java
public interface CrudUserMapper extends CrudMapper<User> {
    
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
<p>
这个JQL就是查询user和userOrder满足关系的数据，并且自动映射一对多关系。 User类的结构如下：</p>

```java
 @BaseModel     
 public class User {

    @BaseUnique
    private Long id;

    private String name;

    private String birthday;

    private String createTime;

    private List<UserOrder> orders; //它会自动将数据映射到其中
      
    //getter, setter methods 
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

<p>
    use left join , group by , limitPage  
</p>   
      
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
     * @return primary key id
     */
    public List<Id> saveOrModify(T model);

    /**
     * save or replace
     * sql: replace into
     * @param model class
     * @return primary key id
     */
    public List<Id> saveOrReplace(T model);

    /**
     * save model
     * @param models class
     * @return primary key id
     */
    public List<Id> saveBatch(Collection<T> models);

    /**
     * delete model.Where conditions will be generated based 
        on properties of the model
     * class for which there is a value.
     * @param model
     */
    public int remove(T model);

    /**
     * delete model by id


     */
    public int removeById(Serializable id );

    /**
     * delete model by ids
     */
    public int removeByIds(Serializable... ids );

    /**
     * delete model by ids
     */
    public int removeByIds(Collection<Serializable> ids);

    /**
     * Update the model, note that the update condition is 
        the property marked with the Unique annotation.
     * Only properties with values ​​are updated.
     * In other words, the @BaseUnique annotation will generate 
        a Where condition, and other non-null properties will
     * generate a set statement
     * @param model model
     * @return The number of bars affected by the update
     */
    public int modifyById(T model);

    /**
     * batch update
     * @param models models
     * @return Affect the number of bars
     */
    public int modifyBatchById(Collection<T> models);

    /**
     * Query the main model, be careful not to include child models. 
        Non-null properties will generate a where statement.
     * <>Note that properties such as Collection<Model> will be ignored, 
        even if they are not null </>
     * @param model model
     * @return return query result
     */
    public List<T> query(T model);

    /**
     * Query the main model, be careful not to include child models. 
        Non-null properties will generate a where statement.
     * <>Note that properties such as Collection<Model> will be ignored, 
        even if they are not null </>
     * @param model model
     * @param pageNum page number
     * @param pageSize Number of bars displayed per page
     * @return return query result
     */
    public List<T> query(T model,int pageNum,int pageSize);

    /**
     * Paging query full table data
     * @param pageNum page number, If the parameter is less than 1, 
        it defaults to 1
     * @param pageSize Number of bars displayed per page， 
        If the parameter is less than 1, it defaults to 10
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
    public List<T> queryByIds(Collection<Serializable> ids);

    /**
     * Map<String,Object>. String: Field names of the table. 
        The value corresponding to the Object field
     * @param param Parameters. key database field name, value field value
     * @return model
     */
    public List<T> queryByParam(Map<String,Object> param);

    /**
     * Map<String,Object>. String: Field names of the table. 
        The value corresponding to the Object field
     * @param param Parameters. key database field name, value field value
     * @param pageNum page number
     * @param pageSize Number of bars displayed per page
     * @return model
     */
    public List<T> queryByParam(Map<String,Object> param,int pageNum,int pageSize);


   /**
     * The number of statistical tables
     * @return
     */
    public long count();

    /**
     * The number of statistical tables, through the specified field
     * @return
     */
    public long count(C c);

    /**
     * The number of statistical tables, through the specified field
     * Statistical results after deduplication. count(DISTINCT c)
     * @return
     */
    public long countDistinct(C c);


    /**
     * The number of statistical tables.  Will use the model as the where condition
     * @return
     */
    public long count(T model);

    /**
     * The number of statistical tables, through the specified field.
     * Will use the model as the where condition
     * @return
     */
    public long count(C c,T model);

    /**
     * The number of statistical tables, through the specified field
     * Statistical results after deduplication. count(DISTINCT c).
     * Will use the model as the where condition
     * @return
     */
    public long countDistinct(C c,T model);

```
### sql函数注解
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
### Powerful type converter
<p>
内置大量常用类型转换器。比如数据库字段birthday是datetime/int、Number/varchar和枚举类之间的转换. 枚举类通常和@EnumValue一起使用,用于标识枚举类唯一的属性,该属性会和表中的字段进行自动关联.(sample of enum : 
https://github.com/caomingjie-code/mybatis-jql/blob/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperInsert.java
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

### 拦截器模式
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


### 支持自动加密和解密
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
  <artifactId>mybatis-model-encipher</artifactId>
  <version>3.5.11.11</version>
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

### 字段脱敏
<p>
支持字段脱敏. 只需要在model类上加上@EmailBlur注解可可以类。 注意被加上的注解的字段必须是String类型.
</p>

```
   @EmailBlur
   private String email; // 12345678@outlook.com加密后的数据为12***678@outlook.com
```  
<p>
更多案例： https://github.com/javaoffers/mybatis-jql/tree/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/blur
</p>

#### Code contributions are welcome
<p>
该项目已在内部使用。大大提高了开发效率和代码整洁度。如果觉得不错，请点个小星星鼓励一下
</p>
