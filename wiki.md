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
            return this.select()
                       .colAll()
                       .where()
                       .exs();
        }
        
        //增加user
        default int addUser(User user){
            Id ex = this.insert().colAll(user).ex();
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
             
            List<User> exs = this .select()
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
             Id id = this.insert()
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
                Id id = this.update()
                            .npdateNull() 
                            .col(User::getName, user.getName())
                            .where()
                            .eq(User::getId, user.getId())
                            .ex();
                return id.toInt();
           }
            
            
           default Integer updateUserNameById2(User user){
                Id id = this.update()
                            .updateNull() 
                            .col(User::getName, user.getName())
                            .where()
                            .eq(User::getId, user.getId())
                            .ex();
                return id.toInt();
           }


           default Integer updateUserById(User user){
                Id id = this.update()
                            .updateNull() 
                            .colAll(user)
                            .where()
                            .eq(User::getId, user.getId())
                            .ex();
                return id.toInt();
           }

           default Integer updateUserById2(User user){
                Id id = this.update()
                            .npdateNull() 
                            .colAll(user)
                            .where()
                            .eq(User::getId, user.getId())
                            .ex();
                return id.toInt();
           }
   
       }
```            
 
  - col()指定具体要更新的字段
  - colAll() 更新全部字段（映射匹配的字段）
  - updateNull() 会包含 colname = null. 会将字段更为为空.
  - npdateNull() 过滤掉 colname = null. 不会将字段更新为空

- delete()
<p>
    删除数据的入口方法
</p>

```java
    public interface CrudUserMapper extends CrudMapper<User>{
           
           default Integer deleteUserById(User user){
                Id id = this.delete()
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
    import javax.annotation.Resource;
    public class UserServiceImpl {
          
          @Resource
          CrudUserMapper  crudUserMapper;     

          public User queryUserById(User user){
                 User user = crudUserMapper.general().queryById(save);
                 return user;
          }

          public Integer saveUser(User user){
                int id = crudUserMapper.general().save(general).toInt();
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

### Id类
<p>
jql中有一基础Id类， 此类表示主键id. 当新增一条数据时，会返回Id对象. 表主键的类型可能不同。有varchar 或者 int 等. 因此
Id类包装了主键id的数据。我们可以通过toInt, toLong等方法获取自己想要的主键数据，当然如果你想获取原始数据可以通过 value()
方法获取。并且Id类会被jql底层进行解析当Id作为参数时。 比如： 
</p>

```java
// name = hello
User save = new User("hello")

//保存返回Id主键
Id id = crudUserMapper.general().save(save);
//Id主键返回int 类型
int idInt = id.toInt();

//下面这两句是等价的。
User user = crudUserMapper.general().queryById(id);
User user2 = crudUserMapper.general().queryByIda(idInt);


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

### ex() 和 exs()的区别
<p>
ex() 和 exs() 表示使jql触发执行。不同的是ex()返回一条Model数据，而exs() 的执行结果则为集合。返回多条List<Model> 数据.
</p>


### where() 这里简单介绍一下，因为都是接近原生sql. 所以学习成本比较低. 
<p>
在jql中where()代表原生sql中的where. 在jql的设计中，select(), update(), delete() 都需要通过where()方法来触发ex()/exs()执行。
这样设计的目的是避免全表的失误操作 （总是提醒你不要忘记添加where条件）。当然如果你就是想进行全表操作也是可以的。比如where().ex() 或
者 where().exs(); where() 会被解析为 where 1=1 进行执行。 
</p>

  - eq()
    - 表示等于
  - unite()
    - 表示括号逻辑，（xxxxx）
  - condSQL
    - 允许你写原生字符sql.   
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
           return this.select().col(User::getBirthdayStr).where().eq(User::getId, id).ex();
        }
   }

   
  ```

  - enum枚举支持
    <p>
    当Model类的属性是枚举时 ，jql对枚举类支持的也非常好. 如果枚举类中存在属性时我们可以通过@EnumValue来指定枚举类中的属性(具有唯一特性)。
    该属性的值作为表字段属性的值。 如果没有@EnumValue则默认将枚举类的 ordinal() 的值作为表字段的默认值。 注意@EnumValue在枚举类中
    只能指定一个属性.不能指定多个
    </p>
    
    ```java
     @BaseModel
     @Data
     public class User {
       
         @BaseUnique
         private Integer id;     
       
         private String name;
       
         private Sex sex;
    
         private Work work;
     
     }   
    
    //没有属性可以不使用@EnumValue
     public enum Sex {
         Girl,
         Boy
     }
    
    //有属性需要用@NnumValue进行指定.
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
    值最后将会被解析到要执行的sql语句中。@ColName 与 Sql函数注解也经常配合使用（不是必须的）。
    @ColName的excludeColAll属性默认，是否参与select().colAll()查询。（默认参与查询）
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

#### @GroupConcat 函数注解
<p>
 该注解属于分组sql函数注解，当执行select().colAll() 时可能会将该注解对应的sql语句不进行解析. 因为colAll()通常是查询所有字段，
 除非你按照所有字段进行了分组。这种场景不是很多。如果你想让colAll() 包含@GroupConcat进行解析，则只需要将@GroupConcat中的
 excludeColAll 属性改为false即可，默认为true. 另外一种方式是通过col(xxx) 来指定也是可以的（推荐这种方式）
</p>
      
### 多表
  
<p>
jql 支持多表映射，而且非常简单没有复杂的配置。 一对一， 一对多，多对多。用Model类表示
则为类与类（一对一），类与集合的关系（一对多，多对多）。 jql 设计最多3张表进行join. 
如果你想多表(3张以上)可以使用直接写原生myabtis. jql这样设计的原因是为了提高项目的可维
护性和简化sql. (本人经历过超过10张表的join. 后期这个join基本上就没有维护的空间. 而且
随着数据量的上涨sql的执行效率越来越底. 更可怕的是这个大join关联的业务比较多. 最后只能重
写和这个大join关联的所有业务. 大join会将维护成本提高n倍. 所以我们还是尽量避免) 来我们
看一下案例.
</p> 

  - 一对一 （类与类的关系）

```java
 @BaseModel
 @Data
 public class User {
   
     @BaseUnique
     private Integer id;     
     
     private String name; 
    
     private Card card; //一对一。 不需要任何配置就是这么简单

 }   
 
//身份证号
 @BaseModel
 @Data
 public class Card{ 
    //主键id
    @BaseUnique
    private Integer id;
    
    //身份证号
    private String cardNum;
    
    //用户id
    private Interge userId;

 }


```  

  - 一对多 (类与集合的关系)

```java

 @BaseModel
 @Data
 public class User {
   
     @BaseUnique
     private Integer id;     
     
     private String name; 
    
     private List<Order> order; //一对多。 不需要任何配置就是这么简单

 }  

//订单
 @BaseModel
 @Data
 public class Order{ 
    //主键id
    @BaseUnique
    private Integer id;
    
    //订单号
    private String orderNum;
    
    //用户id
    private Interge userId;

 }


``` 

  - 多对多 （类与集合的关系）

```java

 @BaseModel
 @Data
 public class Student {
   
     @BaseUnique
     private Integer id;     
     
     private String name; 
    
     private List<Teacher> teachers; //多对多。 不需要任何配置就是这么简单

 }  

 //老师
 @BaseModel
 @Data
 public class Teacher{ 
    //主键id
    @BaseUnique
    private Integer id;
    
    //老师名称
    private String teacherName;
    
    //学科
    private String subject;

 }

 //中间表
 @BaseModel
 @Data
 public class StudentTeacher{
        
     //主键id
     private Integer id;
     //学生id
     private Integer studentId;
     //老师id
     private Integer teacherId;

 }

```               

### join 支持。
  - left join
  ```java
  List<User> users = crudUserMapper
                    .select()
                    .colAll()
                    .leftJoin(UserOrder::new)
                    .colAll()
                    .on()
                    .oeq(User::getId, UserOrder::getUserId)
                    .where()
                    .exs();

  ```
  - right join
  
  ```java
    List<User> users = crudUserMapper
                      .select()
                      .colAll() //查询user的所有字段和sql函数语句（标有函数注解的也将会被解析）
                      .rightJoin(UserOrder::new)
                      .colAll()
                      .on()
                      .oeq(User::getId, UserOrder::getUserId)//绑定两张表的关系字段。
                      .where()
                      .exs();
  
  ```
    
  - inner join (多对多案例)
  ```java
    List<User> users = crudUserMapper
                       .select()
                       .colAll()//查询user所有字段
                       .innerJoin(UserTeacher::new) //关联中间表
                       .on()
                       .oeq(User::getId, UserTeacher::getUserId) //指定关联关系
                       .innerJoin(Teacher::new)//关联子表
                       .colAll()//查询子表所有字段
                       .on()
                       .oeq(UserTeacher::getTeacherId, Teacher::getId)//指定关联关系
                       .where()
                       .exs();//执行
  
  ```
<p>
 在join 进行关联时，我们可以使用on来指定两张表的关系。 以oxx开头的方法是用来描述两张表之间的关系。
 如果你想指定子表的某一个字段的逻辑关系，可是调用非o开都的方法即可。非常简单。
 
<p/>


### 通用api
<p>
jql中提供了很多常用的api操作，不需要开发人员二次开发。我们可以通过general()方法来使用常用方法。
比如：User user = crudUserMapper.general().queryById(id); 这里我们来举例部分. 您也可以通
过 general() 来查看全部支持的方法。
</p>

- 常用api
  - save() 保存
  - saveOrModify() 保存或更新，解析的sql为 insert into xxx on duplicate key update xxx
  - saveOrReplace() 对应sql中的replace into 
  - saveBatch() 批量保存。进行批处理。效率非常高。
  - remove() 会将model对象属性不为空解析成where条件进行执行delete
  - removeById() 根据id删除
  - modifyById() 根据id执行更新操
  - modifyBatchById() 支持批量更新，底层会进行优化当更新的字段相同时会进行分组，每组作为一个批次进行更新
  - query() 会将model对象属性不为空解析成where条件进行执行 select. 还有各种其他的query()这里只举例部分
  - count() 统计表数量

### 接口default方法中编写jql  
<p>
为了同意jql的编码风格。并且方便项目的维护。方式sql/jql出现的到处都是。因此在设计jql的时候支持了在接口中编写defalue
风格。在接口中的default方法中直接可以编写jql.
</p>

### 内置类型转换器
<p>
在实际的开发中，我们通常把数据查看出来然后再进行格式化转换。为了避免这种问题没，我们支持了自动类型转换。常用的类型
转换器我们已经进行了实现。开发者只需要在Model类中定义自己想要的类型即可。 我们当前支持的类型转换如下。  比如
Number2DateConvert 表示 number数字转换为date日期 .

</p>

- String2DoubleConvert      
- DateOne2DateTwoConvert  
- String2DateConvert  
- Boolean2StringConvert  
- Date2OffsetDateTimeConvert  
- Date2LongConvert  
- Number2SQLDateConvert  
- String2ByteConvert  
- ByteArray2StringConvert2  
- Number2DateConvert  
- Date2LocalDateTimeConvert  
- String2LocalDateConvert  
- String2OffsetDateTimeConvert  
- Number2StringConvert  
- String2FloatConvert  
- Date2StringConvert  
- String2BooleanConvert  
- String2ShortConvert  
- PrimitiveNumber2PrimitiveNumberConvert  
- String2LongConvert  
- LocalDate2StringConvert  
- String2CharConvert  
- Character2StringConvert  
- String2IntegerConvert  
- Number2LocalDateConvert  
- Number2PrimitiveConvert  
- String2LocalDateTimeConvert  
- Date2LocalDateConvert  
- String2SQLDateConvert  
- ByteArray2StringConvert  
- String2BigDecimalConvert  
- Number2BooleanConvert  
- String2BigIntegerConvert  
- Number2LocalDateTimeConvert
- Number2EnumConvert
- String2EnumConvert