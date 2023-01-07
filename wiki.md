### 摘要 mybatis-jql 是什么? 解决了什么? 
<p>

 mybatis-jql 可以看作是一个依附于myabtis之上并提供mybatis所不具备的一些特性 (完全支持原生Mybatis). 
 目的是快速开发. 简化代码. 增强代码易读性. 提高项目可维护性. 比如
 不用写xml文件, 不用写原生的字符sql,直接在接口的defalut方法中编写jql即可(java stream sql).
 这种方式即加快了sql的编写又起到了规范作用. 常用的crud操作已经集成
 不需要二次开发. 一对一.一对多,多对多映射关系不需要配置. 为了提高项目的可维护性 , 设计 jql api时避免了复杂的结构语句.
 不会使项目中出现复杂的sql.可将复杂的sql拆解为多个jql (项目中杜绝复杂sql. 经历过的都明白). 为了提高可读性,jql的
 形式接近原生sql. 为了提高编写效率,jql采用java stream的编写风格. 还有丰富的类型自动转换器. 比如表中的字段为long,我们
 可以在实体类中直接定义Date/LoacalDateTime (具体可查看支持的类型转换.). 更多特性这里不一一介绍了.
 接下来让我们进入mybatis-jql 
    
</p>
  
### @BaseModel, @BaseUnique
<p>
  @BaseModel 表示是一个Model类，mybatis-jql会自动解析映射表字段. mybatis-jql并不会向mysql发送ddl语句（这是非常危险的）.
  mybatis-jql会根据@BaseModel解析出表名称（将Model类名解析为下划线格式作为数据库表名，如果表名和Model类型不一样则可以使用
   @BaseModel的value属性进行指定），然后查询此表的字段信息并与Model类中的属性相关联. 关联的策略是将属性名称（驼峰形式）
  转化成下划线并与表字段进行匹配，匹配成功才会进行关联并缓存这种关联关系. 所以Model类可以出现非表字段属性.提高了Model类的灵活
  性. 非表字段属性不会被解析成sql. @BaseUnique 表示此表中具有唯一属性的字段（通常用于主键id）。 案例如下：
</p>

```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `birthday` datetime DEFAULT NULL
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```

```java
@BaseModel
@Data
@Builder
@AllArgsConstructor
public class User {
    
    @BaseUnique
    private Integer id;     
    
    private String name;
    
    private Date birthday;
    
}
```

### CrudMapper<T> 接口
<p>
mybatis-jql 以简单快速开发为理念，因此mybatis-jql几乎没有学习成本. 我们只需要将我们的接口继承 CrudMapper 接口即可. T 为泛型Model类
（标记@BaseModel注解的类）。这样我们的Mapper接口在mybatis的基础上又拥有了jql的能力了. 在CrudMapper接口中主要有5个方法：select(),
insert(), update(), delete(), general(). 就是这这么简单. 案例如下
</p>

```java
public interface CrudUserMapper extends CrudMapper<User> {
    
        //查询全表数据
        default List<User> queryAll(){
            return select()
                    .colAll()
                    .where()
                    .exs();
        }
        
        //增加user
        default int addUser(User user){
            Id ex = insert().colAll(user).ex();
            return ex.toInt();
        }   
        //.... 这里不一一举例了， 
}
```

<p>
当然上面两个常用的方法其实你是不需要写的，因为在常用api中都已经实现了. 我们通过general()来调用常用api.
</p>

```java
@Service
public class UserServiceImpl {
    @Resource
    CrudUserMapper crudUserMapper;
    
    public List<User> queryAll(int pageNum, int size){
        return crudUserMapper.general().query(pageNum, size);
    }

}
```

### crud 操作划分
<p>
  我们的数据库主要就是查询数据，新增数据，更新数据以及删除数据. 因此设计了4个核心方法以及1个常用的api方法。
</p>

- select()
<p>
    查询数据的入口方法
<p>

```java
    public interface CrudUserMapper extends CrudMapper<User>{
        
        default List<User> queryUserByName(String name){
             List<User> exs = crudUserMapper
                                    .select()
                                    .col(User::getName)
                                    .col(User::getSex)
                                    .where()
                                    .eq(User::getName, name)
                                    .exs();
        }   

    }
```

- insert()
<p>
    新增数据的入口方法
</p>

```java
    public interface CrudUserMapper extends CrudMapper<User>{
        
        default Integer saveUsers(User user){
             Id id = crudUserMapper.insert()
                                   .col(User::getName, user.getName())
                                   .col(User::getSex, user.getName())
                                   .where()
                                   .ex();
             return id.toInt();
        }   

    }
```
  
- update()
<p>
    更新数据的入口方法
</p>  

```java
    public interface CrudUserMapper extends CrudMapper<User>{
           
           default Integer updateUserNameById(User user){
                Id id = crudUserMapper.update()
                                      .col(User::getName, user.getName())
                                      .where()
                                      .eq(User::getId, user.getId())
                                      .ex();
                return id.toInt();
           }   
   
       }
```

- delete()
<p>
    删除数据的入口方法
</p>

```java
    public interface CrudUserMapper extends CrudMapper<User>{
           
           default Integer deleteUserById(User user){
                Id id = crudUserMapper.update()
                                      .col(User::getName, user.getName())
                                      .where()
                                      .eq(User::getId, user.getId())
                                      .ex();
                return id.toInt();
           }   
   
    }
```

- general()
<p>
    集成常用crud方法
</p>

```java
    public class UserServiceImpl {
           
          public User queryUserById(User user){
                 User user = crudUserMapper.general().queryById(save);
                 return user;
          }

          public Integer saveUser(User user){
                long id = crudUserMapper.general().save(general).toLong();
                return id;
          } 
          
          public int updateUserById(User user){
                return crudUserMapper.general().modifyById(user);
          }   
          
          public void deleteUserById(int id){
               crudUserMapper.general().removeById(id);
          }     
   
    }    
```

### col() 和 colAll()
  - col()
    <p>
     col()方法用于指定具体的字段。
    </p>
  - colAll()
    <p>
    colAll()方法在select()的时候查询所有字段，包括表字段和函数sql语句(指标有函数注解的)。
    在insert() update() 时插入表所有字段.
    </p>

### where() 这里简单介绍一下，因为都是接近原生sql. 所以学习成本比较低.
  - eq()
    - 表示等于
  - unite()
    - 表示括号逻辑，（xxxxx）
  - or()
    - 默认是and,通过or() 方法指定为or.
  - groupBy
    - 分组
  - having
    - 处理分组后的统计逻辑
    
   
### 自动识别表字段和函数语句与model类进行关联映射
<p>
   字段主要分为表字段和sql函数语句。 表字段通过Model类的属性自动和表字段进行映射（属性转换为下划线格式后与表字段进行映射）。
   如果表字段和Model类中的属性名称（属性转换为下划线格式后）不相同时。可以通过@ColName进行指定具体的表字段。
</p>
  - 案例

  ```java
   @BaseModel
   @Data
   @Builder
   @AllArgsConstructor
   public class User {
       
       @BaseUnique
       private Integer id;     
       
       private String name;
       
       private Date birthday;
       
       //通过@ColName 指定 birthday 字段与 birthdayStr属性进行关联。  
       @ColName("birthday") 
       private String birthdayStr;
      
   }
   
   public interface CrudUserMapper{
   
        default User queryUserBirthdayById(int id){
           // SQL: select user.birthday as birthdayStr from user where 1=1 and user.id = #{id} 
           return crudUserMapper.select().col(User::getBirthdayStr).where().eq(User::getId, id).ex();
        }
   }

   
  ```

  - enum枚举支持
    <p>
    当Model类的属性是枚举时 ，jql对枚举类支持的也非常好. 如果枚举类中存在属性时我们可以通过@EnumValue来指定枚举类中的属性。
    该属性的值作为表字段属性的值。 如果没有@EnumValue则默认将枚举类的 ordinal 作为表属性的默认值。
    </p>
    
    ```java
     @BaseModel
     @Data
     @Builder
     @AllArgsConstructor
     public class User {
       
         @BaseUnique
         private Integer id;     
       
         private String name;
       
         private Sex sex;
    
         private Work work;
     
     }   
    
     public enum Sex {
         Girl,
         Boy
     }
    
     public enum Work {
     
         JAVA(1,"JAVA"),
         PYTHON(2,"PYTHON")
         ;
    
         @EnumValue
         private int code;
         private String workName;
     
         Work(int code, String workName){
             this.code = code;
             this.workName = workName;
         }
     }
    
    ```
    
  - @ColName 
    <p>
    @ColName 非常灵活，不仅可以指定表字段名称。还可以指定sql语句。本质上是将@ColName的
    值最后将会被解析到要执行的sql语句中。@ColName 与 Sql函数注解经常配合使用。
    </p>
    


### sql函数注解（通常和@ColName联合使用的比较多当属性名称和表字段不一致时），sql函数注解比较多这里举例部分。
<p>
注解类名称与mysql数据库中的函数名称保持一致。多个sql注解可以组合使用。 
多个组合sql函数注解最后会解析为嵌套的sql函数逻辑。组合注解解析的顺序
是从上向下开始解析。
</p>
  - 有参函数
    - 通用函数
      - @if
      - @ifEq
      - xxxx  
    - 字符函数
      - @CharLength
      - Concat
      - xxxx
    - 时间函数
      - DateFormat
      - Dayname    
      - xxxx  
    - 数学函数
      - @Abs
      - @Ceil
  - 无参函数 不需要和@ColName进行配合使用
    - 时间函数
      - @Curdate
      - CurrentDate
      - xxxx
    - 数学函数
      - @Rand

```java
     @BaseModel
     @Data
     @Builder
     @AllArgsConstructor
     public class User {
       
         @BaseUnique
         private Integer id;     
         
         @Trim()
         private String name; //trim(name)

         private Sex sex;
        
         @ColName("name")
         @Trim()
         @Concat("sex")
         private String concatName; // concat(trim(name),sex)

     }   
```
#### 多表
  
<p>
jql 支持多表映射，而且非常简单没有复杂的配置。 一对一， 一对多，多对多。用Model类表示
则为类与类（一对一），类与集合的关系（一对多，多对多）。解析来我们看一下案例
</p> 
  - 一对一

```java

```  

  - 一对多

```java

``` 

  - 多对多

```java

```               

#### join 
  - left join
  - right join
  - inner join

#### 通用api
    

#### 接口default方法中编写jql  

#### 内置类型转换器
  - Number2DateConvert
  - Date2StringConvert
  - xxx