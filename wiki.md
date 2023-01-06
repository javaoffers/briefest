#### 摘要 mybatis-jql 是什么? 解决了什么? 
<p>
 mybatis-jql 可以看作是一个依附于myabtis之上并提供mybatis所不具备的一些特性. 
 目的是快速开发. 简化代码. 增强代码易读性. 提高项目可维护性. 比如
 不用写xml文件, 不用写原生的字符sql,直接在接口的defalut方法中编写jql即可(java stream sql).
 这种方式即加快了sql的编写数据又规范了编写方式(jql只在default方法中). 常用的crud操作已经集成
 不需要二次开发. 一对一.一对多,多对多映射关系不需要配置. 为了提高项目的可维护性 , 设计 jql api时避免了复杂的结构语句.
 不会使项目中出现复杂的sql.可将复杂的sql拆解为多个jql (项目中杜绝复杂sql. 经历过的都明白). 为了提高可读性,jql的
 书写形式接近原生sql. 为了提高编写效率,jql采用java stream的编写风格. 还有丰富的类型自动转换器. 比如表中的字段为long,我们
 可以在实体类中直接定义Date/LoacalDateTime (具体可查看支持的类型转换.).  
   
   
    
</p>
  
#### @BaseModel, @BaseUnique

#### CurdMapper<T> 接口

#### crud 操作划分
  
#### 一个普通查询

#### 条件查询

#### select()
  - col()

  - colAll()

#### where()
  - eq()
  - xxx
  - unite()
  - or()
  - groupBy
  - having
   
#### 自动识别表字段和函数语句与model类进行关联映射
  - 案例
  - enum枚举支持

#### @ColName 使用

#### sql函数注解
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
  - 无参函数
    - 时间函数
      - @Curdate
      - CurrentDate
      - xxxx
    - 数学函数
      - @Rand      
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