- 摘要 mybatis-jql 是什么? 解决了什么? 

- @BaseModel, @BaseUnique

- CurdMapper<T> 接口

- crud 操作划分
  
- 一个普通查询

- 条件查询

- select()
  - col()

  - colAll()

- where()
  - eq()
  - xxx
  - unite()
  - or()
  - groupBy
  - having
   
- 自动识别表字段和函数语句与model类进行关联映射
  - 案例
  - enum枚举支持

- @ColName 使用

- sql函数注解
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
- join 
  - left join
  - right join
  - inner join

- 通用api
    

- 接口default方法中编写jql  

- 内置类型转换器
  - Number2DateConvert
  - Date2StringConvert
  - xxx