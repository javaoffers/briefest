
## mybatis model helper
- Summary
  <p>
 
JQL 是以 Batis 为核心设计的。主要思想是简化数据之间的映射，避免编写 SQL 我们提供的模型映射非常强大。只需要两个注解就可以实现一对一和一对多对多的模型类映射。同时，我们还提供 crudmapper 基础接口。避免在java接口SQL语句中混用。这使得编写 SQL 就像编写 java 代码一样。这里我们称之为 JQL。并形成一套 JQL API 流程规范。它不会像 SQL 那样复杂。而 JQL API 将使 SQL 错误率降低。 JQL 旨在将复杂的 SQL 分解为简单的 SQL，因此 JQL 在设计时最多支持两个表关联查询。我们不建议加入超过 2 个表。这样会降低 SQL 的可读性和可维护性。并且在modelhelper中支持了新的编写格式。默认方法可以写在java接口中，内部可以直接操作JQL API（前提是继承了crudmapper）。让我在 Java 流中编写 JQL 以提高开发效率。更少的代码和更流畅的书写形式。
</p>
 
- maven
  ```java
    <!-- https://mvnrepository.com/artifact/com.javaoffers/mybatis-model-spring-support/3.5.11.3 -->
   <dependency>
      <groupId>com.javaoffers</groupId>
      <artifactId>mybatis-model-spring-support</artifactId>
      <version>3.5.11.3</version>
   </dependency>

  ```
    
- 基本使用教程
 <p>
    普通查询
 </p>
 <p>
    在看操作之前，我们先看一下数据结构：这里有两个关键的注解。 @BaseModel 用于表示该类属于模型类（类名与表名相同，ModelHelp最终会将驼峰类名转换为下划线表名，属性相同），@ BaseUnique 表示类中唯一的属性（对应表中的唯一属性，当表中使用联合主键时可以是多个）。我们将在最后详细解释注解的使用。这里是基本用法
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
    .colAll() //查询所有字段
    .where() 
    .exs(); 
 ```
 
  <p>
这个 JQL 最终会被翻译为 select * from user。这里的 colall 表示查询所有表字段。如果要查询指定的字段，例如姓名、生日等字段，可以这样做：
 </p>
 
 ```java
 List<User> users = crudusermapper
     .select()
     .col (user:: getbirthday) //指定查询子字段
     .col (user:: getname)
     .where()
    .exs();
 ```
 
 <p>
 
您可以通过 col() 指定要查询的字段。这里的 where() 与 SQL 中的关键字 where 相同。比如要查询一个ID值为1的用户，可以这样写： 
 </p>
 
 ```java
 User user = crudusermapper
 .select() 
 .col(User::getBirthday) 
 .col(User::getName) 
 .where() 
 .eq(User::getId, 1) 
 .ex();
 ```
 <p>
在这三种情况下，你会发现有两个特殊的函数 exs(), ex() 这两个函数代表触发器执行。 exs()通常用于查询更多数据，返回结果为list，而ex()用于只返回一个结果T； JQL 必须通过才能触发 where 和 ex/exs 。在大多数工作场景中，过滤条件都会在WHERE之后添加，除了特殊的count all table data之外，这个设计也是一个很好的提醒，记得填写WHERE条件，当然如果你不需要添加任何WHERE条件为了查询所有表数据，可以使用where().ex(), where().exs()
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

一个简单的插入语句，它返回一个包装类 ID，它通常是新插入数据的主键。插入操作就是这么简单。还有一种更简单的插入数据的方法。插入对象。并支持多个。形成逻辑针对批处理进行了优化。例如下面的案例
    
</p>

```java
String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        User h1 = User.builder().name("Jom1").birthday(date).build();
        User h2 = User.builder().name("Jom2").birthday(date).build();
        User h3 = User.builder().name("Jom3").birthday(date).build();
        List<Id> ex = crudUserMapper.insert()
                .colAll(h1, h2, h3)
                .ex();
        print(ex);
```

<p>

 我们可以插入整个模型对象，表示要查询所有字段，分批分层。性能非常好。更多案例请参考：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperInsert.java

</p>

<p>

更新操作，更新操作有两种模式，允许更新空值updateNull和不允许更新空值npdateNull，请看以下案例
</p>

```java
crudUserMapper
        .update().npdateNull()
                 .col(User::getBirthday, new Date())
                 //name not will update . because its npdateNull
                 .col(User::getName,null)
                 .where()
                 .eq(User::getId, id)
                 .ex();

crudUserMapper
        .update().updateNull()
                 .col(User::getBirthday, new Date())
                 //name  will update . because its updateNull
                 .col(User::getName,null)
                 .where()
                 .eq(User::getId, id)
                 .ex();

```

<p>

此方法对于模型对象实例非常有用。比如User对象中有些属性有值（不为null），有些属性没有值（null），那么没有值的属性是否应该更新呢？可以通过npdateNull（不更新）updateNull（更新属性为null），比如下面的情况
</p>

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

通过上面的案例，我们可以很好的控制业务中字段的更新。当我使用模型类时。
</p>

<p>

一种新的编码方式。我们可以在 Mapper 接口中编写默认方法。比如下面的案例我们推荐使用这种风格

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

当我的接口继承了 CrudMapper 接口时，我们可以默认编写我们的 JQL 逻辑。这避免了在 Mapper 接口上编写本机 SQL 语句的传统方法。 .更多案例请看：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring /mapper/CrudUserMapper.java
</p>

- demo crud:
  - demo ：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/mybatis-model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring
    
#### 进阶
- 这部分主要介绍如何使用JQL来表达一些复杂的查询语句
<p>
在上面的基础部分，我们解释了一些常见和最基本的用途。接下来，我们将介绍一些实际项目中的场景。一些稍微复杂的用例。主要包括join查询、group查询、统计查询、常用的通用操作。
</p>

<p>
JQL 提供了丰富的常用 API。例如 >= 、 <= 、 in 、 between、like、likeLeft、likeRight、exists 等。还有一种组合 unite 主要将多个条件合二为一，如 (xx > xx 或 xx < xx ) 对待两个关联条件为一。同时我们让你写原生sql的入口，比如col(sql)，condSQL(sql)，虽然我们通常不推荐使用原生sql。因为尽量不要用sql进行复杂的逻辑处理，比如截取一些字符串。或者拼接等。建议在业务层处理这些操作。我们先来看一个简单的join JQL案例：我们推荐在接口类中写JQL
</p>

```java
public interface CrudUserMapper extends CrudMapper<User> {
    
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
}
```
<p>
这个JQL是查询user和userOrder满足关系的数据，并自动映射一对多关系。 User类的结构如下：</p>

```java
 @BaseModel     
 public class User {

    @BaseUnique
    private Long id;

    private String name;

    private String birthday;

    private String createTime;

    private List<UserOrder> orders; //It will automatically map the data into it
      
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
    使用 left join , group by , limitPage  
</p>   
      
```java
 crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //Group by main table
                .groupBy(User::getName, User::getId)
                //Group according to sub-table
                .groupBy(UserOrder::getUserId)
                .limitPage(1,10)
                .exs();

```

<p>
使用 left join , group by , having,  limitPage 
</p>

```java
crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                .groupBy(User::getName, User::getId)//按主表分组
                .groupBy(UserOrder::getUserId) //按子表分组
                .having()
                //主表统计功能
                .eq(AggTag.COUNT,User::getName,1)
                .or()
                // 意思 (xx and xx)
                .unite(unite->{
                    unite.in(AggTag.COUNT, UserOrder::getUserId,1 )
                        .in(AggTag.COUNT, UserOrder::getUserId,1);
                })
                //子表统计功能
                .gt(AggTag.COUNT,UserOrder::getOrderId,1)
                .limitPage(1,10)
                .exs();

```
<p>
use left join , group by  , order by, limitPage
</p>

```java
crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .leftJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                .where()
                //按主表分组
                .groupBy(User::getName, User::getId)
                //按子表分组
                .groupBy(UserOrder::getUserId)
                // 按主表排序
                .orderA(User::getName)
                //按子表排序
                .orderA(UserOrder::getUserId)
                .limitPage(1,10)
                .exs();
```

<p>
在 unite 上使用内部联接，其中 unite group by ，具有 unite、order by、limitPage
</p>

```java
crudUserMapper.select()
                .col(AggTag.MAX, User::getName)
                .innerJoin(UserOrder::new)
                .col(AggTag.MAX, UserOrder::getOrderName)
                .on()
                .oeq(User::getId, UserOrder::getUserId)
                //支持 and（xxx or xxx）
                .unite(unite->{
                        unite.eq(UserOrder::getUserId,1)
                            .or()
                            .eq(UserOrder::getUserId, 2);
                })
                .where()
                //support and (xxx or xxx )
                .unite(unite->{
                         unite.eq(User::getId,1)
                            .or()
                            .eq(User::getId,2);
                })
                //根据主分组
                .groupBy(User::getName, User::getId)
                //按子表分组
                .groupBy(UserOrder::getUserId)
                .having()
                // 按主表排序 （升序）
                .orderA(User::getName)
                //按子表排序 （倒序)
                .orderD(UserOrder::getUserId)
                .limitPage(1,10)
                .exs();

```

####  常用

<p>
    我封装了一些常用的函数，使用起来非常简单。而且代码也非常简洁明了。如通过id查询或更改。
</p>

<p>
    常用的api只需调用general()方法即可。比如通过id查询数据
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
按id删除指定数据
</p>

```java
crudUserMapper.general().removeById(1);
```

<p>
    更简单常用的API如下。
</p>

```java
    /**
     * save model
     * @param model class
     * @return primary key id
     */
    public long save(T model);

    /**
     * save model
     * @param models class
     * @return primary key id
     */
    public List<Long> saveBatch(Collection<T> models);

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
```
### sql函数注解
<p>
   我们可以通过在类的字段上使用注释来使用 sql 函数。以下是一些用例：
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
}
```

### 强大的类型转换器
<p>
 内置大量常用的类型转换器。 比如 数据库的字段 birthday 是datetime / int , 而我们的User 类中的为 String birthday, 转换器会自动转换为格式为
 yyyy-MM-dd HH:mm:ss 格式的. 常用的类型转换器如下：
</p>

```
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
    String2BooleanConvert (com.javaoffers.batis.modelhelper.convert)
    String2ShortConvert (com.javaoffers.batis.modelhelper.convert)
    PrimitiveNumber2PrimitiveNumberConvert (com.javaoffers.batis.modelhelper.convert)
    String2LongConvert (com.javaoffers.batis.modelhelper.convert)
    LocalDate2StringConvert (com.javaoffers.batis.modelhelper.convert)
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
    Number2BooleanConvert (com.javaoffers.batis.modelhelper.convert)
    String2BigIntegerConvert (com.javaoffers.batis.modelhelper.convert)
    Number2LocalDateTimeConvert (com.javaoffers.batis.modelhelper.convert)
```

#### 欢迎代码贡献
<p>
该项目已在内部使用。开发效率和代码整洁度都有了很大的提高。如果觉得不错，请点个小星星鼓励一下
</p>
