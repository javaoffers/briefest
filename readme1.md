
## mybatis model helper
- Summary
  <p>
Modelhelper 是以 Batis 为核心设计的。主要思想是简化数据之间的映射，避免编写 SQL 我们提供的模型映射非常强大。只需要两个注解就可以实现一对一和一对多对多的模型类映射。同时，我们还提供 crudmapper 基础接口。避免在java接口SQL语句中混用。这使得编写 SQL 就像编写 java 代码一样。这里我们称之为 JQL。并形成一套 JQL API 流程规范。它不会像 SQL 那样复杂。而 JQL API 将使 SQL 错误率降低。 JQL 旨在将复杂的 SQL 分解为简单的 SQL，因此 JQL 在设计时最多支持两个表关联查询。我们不建议加入超过 2 个表。这样会降低 SQL 的可读性和可维护性。不要以为复杂的嵌套 SQL 是牛的 SQL，除了伪装，没有任何价值。并且在modelhelper中支持了新的编写格式。默认方法可以写在java接口中，内部可以直接操作JQL API（前提是继承了crudmapper）。目前正在开发中，欢迎参与本项目。让我在 Java 流中编写 JQL 以提高开发效率。更少的代码和更流畅的书写形式。我相信你会爱上她的 <p>
    
- 教程
 <p>
   普通查询案例
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
    .colAll() 
    .where() 
    .exs(); 
 ```
 
  <p>
这个 JQL 最终会被翻译为 select * from user。这里的 colall 表示查询所有表字段。如果要查询指定的字段，例如姓名、生日等字段，可以这样做：
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
更多查询案例：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperSelete.java
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
                .colAll(h1, h2)
                .colAll(h3)
                .ex();
        print(ex);
```

<p>

我们可以插入整个模型对象，表示要查询所有字段，分批分层。性能非常好。更多案例请参考：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/ SpringSuportCrudUserMapperInsert.java
</p>

<p>
    一种新的编码方式。我们可以在Mapper 接口中编写default方法。
</p>

- demo crud:
  - demo ：https://github.com/caomingjie-code/Mybatis-ModelHelper/tree/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring
    
