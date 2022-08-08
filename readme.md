
## mybatis model helper
- summary
  <p>
Modelhelper is designed with Batis as the core. The main idea is to simplify the mapping between data and avoid writing SQL The model mapping we provide is very powerful. Only
Two annotations are required to implement the model class mapping between one-to-one and one to many to many. At the same time, we also provide crudmapper basic interface. Avoid mixing in java interface
SQL statement. This makes writing SQL like writing java code. Here we call it JQL. And form a set of JQL API process specifications. It won't be as complicated as SQL. And JQL API will make
The SQL error rate is reduced. JQL aims to decompose complex SQL into simple SQL Therefore, JQL supports at most two table Association queries during design. We do not recommend more than 2 tables
join。 This will reduce the readability and maintainability of SQL. Don't think that complex nested SQL is a bull's SQL It's of no value except to pretend. And in the modelhelper
New writing formats are supported in. The default method can be written in the java interface and the JQL API can be directly operated internally (provided that it inherits crudmapper).
Currently under development, welcome to participate in this project.

  <p>

 
- demo crud:
  - demo ：https://github.com/caomingjie-code/Mybatis-ModelHelper/tree/master/model-sample/src/main/java/com/javaoffers/base/modelhelper/sample/spring
    
