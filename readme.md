
## mybatis model helper
- Summary
  <p>
Modelhelper is designed with Batis as the core. The main idea is to simplify the mapping between data and avoid writing SQL The model mapping we provide is very powerful. Only
Two annotations are required to implement the model class mapping between one-to-one and one to many to many. At the same time, we also provide crudmapper basic interface. Avoid mixing in java interface
SQL statement. This makes writing SQL like writing java code. Here we call it JQL. And form a set of JQL API process specifications. It won't be as complicated as SQL. And JQL API will make
The SQL error rate is reduced. JQL aims to decompose complex SQL into simple SQL Therefore, JQL supports at most two table Association queries during design. We do not recommend more than 2 tables
join。 This will reduce the readability and maintainability of SQL. Don't think that complex nested SQL is a bull's SQL It's of no value except to pretend. And in the modelhelper
New writing formats are supported in. The default method can be written in the java interface and the JQL API can be directly operated internally (provided that it inherits crudmapper).
Currently under development, welcome to participate in this project. Let me write JQL in Java stream to improve development efficiency. Less code and more fluent writing form. I'm sure you'll love her 
  <p>
    
- Tutorial 
 <p>
    A Normal Query
 </p>

 ```java
 List users = crudUserMapper 
    .select() 
    .colAll() 
    .where() 
    .exs(); 
 ```
 
  <p>
This JQL will eventually be translated as select * from user. Here, colall means to query all table   fields. If you want to query the specified fields, such as the name and birthday fields, you can do this: 
 </p>
 
 ```java
 List users = crudusermapper
     .select()
     .col (user:: getbirthday)
     .col (user:: getname)
     .where()
    .exs();
 ```
 
 <p>
 You can specify the field you want to query through col(). The where() here is the same as the keyword where in SQL. For example, if you want to query a user whose ID value is 1, you can write this: 
 </p>
 
 ```java
 user user = crudusermapper
 .select() 
 .col(User::getBirthday) 
 .col(User::getName) 
 .where() 
 .eq(User::getId, 1) 
 .ex();
 ```
 <p>In these three cases, you will find that there are two special functions exs(), ex() These two functions represent trigger execution. exs() is usually used to query more data, and the returned result is list, while ex() is used to return only one result T; JQL have to pass to trigger the where and ex/exs . In most work scenarios, filter conditions will be added after WHERE, in addition to the special count all table data, this design is also a good reminder to remember to fill in the WHERE conditions, of course, if you do not need to add any WHERE conditions in order to query all table data, you can use where().ex(), where().exs()
  </p>  
  <p>
    More query cases：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperSelete.java
  </p>

<p>
  A Normal Insert Operation
</p> 
```java

````

- demo crud:
  - demo ：https://github.com/caomingjie-code/Mybatis-ModelHelper/tree/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring
    
