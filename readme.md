
## mybatis model helper
- summary
  <p>
Modelhelper is designed with Batis as the core. The main idea is to simplify the mapping between data and avoid writing SQL The model mapping we provide is very powerful. Only
Two annotations are required to implement the model class mapping between one-to-one and one to many to many. At the same time, we also provide crudmapper basic interface. Avoid mixing in java interface
SQL statement. This makes writing SQL like writing java code. Here we call it JQL. And form a set of JQL API process specifications. It won't be as complicated as SQL. And JQL API will make
The SQL error rate is reduced. JQL aims to decompose complex SQL into simple SQL Therefore, JQL supports at most two table Association queries during design. We do not recommend more than 2 tables
join。 This will reduce the readability and maintainability of SQL. Don't think that complex nested SQL is a bull's SQL It's of no value except to pretend. And in the modelhelper
New writing formats are supported in. The default method can be written in the java interface and the JQL API can be directly operated internally (provided that it inherits crudmapper).
Currently under development, welcome to participate in this project. Let me write JQL in Java stream to improve development efficiency. Less code and more fluent writing form. I'm sure you'll love her 
  <p>
- 教程
  - 一个普通的查询   
  <p>
    List<User> users =  = crudUserMapper
                .select()
                .colAll()
                .where()
                .exs(); 这个JQL 最终会翻译为 select * from user . 这里的colAll 表示查询所有表字段。如果你想查询指定的字段，比如查询name和 birthday字段，则可以这样：
    List<User> users = crudUserMapper
                .select()
                .col(User::getBirthday)
                .col(User::getName)
                .where()
                .exs(); 可以通过col() 指定具体想查询的字段，这里的where()和 sql中的关键字where一样，比如查询id值为1的用户，则可以这样写:
    User user = crudUserMapper.select()
                .col(User::getBirthday)
                .col(User::getName)
                .where()
                .eq(User::getId, 1)
                .ex(); 在这三个案例中你会发现有两个特殊函数 exs(), ex(). 这两个函数表示触发执行。exs() 通常用于查询的数据较多，返回的结果为List<T>,而ex() 用于只返回一个结果 T.
    
    
  </p>  

    
 
- demo crud:
  - demo ：https://github.com/caomingjie-code/Mybatis-ModelHelper/tree/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring
    
