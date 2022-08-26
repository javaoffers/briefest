
## mybatis model helper
- 中文：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/readme1.md 
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
    
- Basic Usage Tutorial 
 <p>
    A Normal Query
 </p>
 <p>
    Before looking at the operation, let's take a look at the data structure: there are two key annotations here.
     @BaseModel is used to indicate that the class belongs to the model class (the class name is the same as the table name,
      ModelHelp will eventually convert the camel case class name into an underscore table name, and the attributes are the same), 
      @BaseUnique indicates the only attribute in the class (corresponding to A unique attribute in a table, which can be multiple 
      when a federated primary key is used in the table). We will explain the use of annotations in detail at the end. Here is the basic use
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
This JQL will eventually be translated as select * from user. Here, colall means to query all table   fields. If you want to query the specified fields, such as the name and birthday fields, you can do this: 
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
 You can specify the field you want to query through col(). The where() here is the same as the keyword where in SQL. For example, if you want to query a user whose ID value is 1, you can write this: 
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
 In these three cases, you will find that there are two special functions exs(), ex() These two functions represent trigger execution. exs() is usually used to query more data, and the returned result is list, while ex() is used to return only one result T; JQL have to pass to trigger the where and ex/exs . In most work scenarios, filter conditions will be added after WHERE, in addition to the special count all table data, this design is also a good reminder to remember to fill in the WHERE conditions, of course, if you do not need to add any WHERE conditions in order to query all table data, you can use where().ex(), where().exs()
 </p>  
 <p>
   More query cases：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperSelete.java
 </p>

<p>
  A Normal Insert Operation
</p> 

```java
Id exOne = crudUserMapper
                .insert()
                .col(User::getBirthday, new Date())
                .col(User::getName, "Jom")
                .ex();
```
<p>
    A simple insert statement that returns a wrapper class Id, which is usually the primary key of the newly inserted data. An insert operation is so simple.
    There is also a simpler way to insert data. Insert object. and supports multiple. Formation logic is optimized for batch processing. 
    For example the following case
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
  We can insert the entire model object, indicating that all fields are to be queried, and the stratum is batched. Performance is very good.
  For more cases, please refer to：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapperInsert.java
</p>

<p>
Update operation, update operation has two modes, allowing to update null value updateNull and not allowing to update null value npdateNull, please see the following case
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
   This method is very useful for model object instances. For example, some properties in a User object have values ​​(not null) and some properties have no value (null), so should the properties without values ​​be updated? You can pass npdateNull (do not update) updateNull (update property is null), such as the following case
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
 Through the above case, we can control the update of the field very well in the business. When I use the model class.
</p>



<p>
A new way of encoding. We can write default method in Mapper interface. For example the following case</p>

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
When my interface inherits the CrudMapper interface, we can write our JQL logic in default. This avoids the traditional method of writing native SQL statements on the Mapper interface.
. For more cases, please see:https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/mapper/CrudUserMapper.java
</p>

- demo crud:
  - demo ：https://github.com/caomingjie-code/Mybatis-ModelHelper/tree/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring
    
