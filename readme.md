
## mybatis model helper
- 概要
  ```
  ModelHelper 以batis为核心进行设计。主旨是简化数据之间的映射和避免在java代码中直接写sql. 我们提供的model映射非常强大只
  需要两个注解即可实现一对一，一对多 多对多之间的Model类映射。同时我们还提供了CRUDMapper基础接口。避免在java接口中夹杂着
  sql语句。使写sql如同写java代码一样,这里我们称为JQL。并且形成一套JQL API流程规范。不会像SQL那样很杂。并且JQL Api 会使
  sql出错率降低。JQL主旨将复杂的sql分解为简单的sql. 因此JQL 在设计的时候最多支持两张表关联查询。我们不建议2张表以上进行
  join。这样会使sql可读性降低易维护性降低。不要以为复杂的嵌套的sql就是牛逼的sql. 除了装逼没有任何价值。并且在ModelHelper
  中支持新的书写格式。在Java的接口中可以写default方法并在内部直接可以操作JQL API (前提是继承CRUDMapper)。
   目前正在开发中，欢迎大家参与这个项目。
 
  ```
- 使用环境mvn
  ```
  
  ```  
- demo案例
  - demo1 ：https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportCrudUserMapper.java
  - demo2 : https://github.com/caomingjie-code/Mybatis-ModelHelper/blob/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring/SpringSuportUserMapper.java
    
