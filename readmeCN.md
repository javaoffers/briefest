<p align="center">
  <img src="note-doc/img/logo.svg" alt="Brief" width="300">
</p>

<p align="center">
  <strong>高性能、零配置的 ORM 框架——用流畅的 Java 写 SQL</strong>
</p>

<p align="center">
  <a href="https://github.com/javaoffers/briefest/blob/develop/readme.md">English</a>
  &nbsp;·&nbsp;
  <a href="https://central.sonatype.com/artifact/com.javaoffers/brief-speedier">Maven Central</a>
  &nbsp;·&nbsp;
  <a href="https://github.com/javaoffers/briefest">GitHub</a>
</p>

来自八股文社区的朋友们记得点个 Star ⭐

---

## Brief 是什么

Brief 是一款轻量级 ORM 框架，核心理念只有一条：**写 SQL 应该像写 Java 一样自然**。

你不再需要手写 SQL 字符串、不再和 XML 映射文件纠缠，而是通过一套强类型、可由 IDE 智能提示的链式 API——**JQL（Java Query Language）**——来组装查询。编译器帮你拦截错误，IDE 引导你写下一个调用，最终代码读起来就像一条 Stream 管道。

```java
// 一条完整的查询，从头到尾——没有 SQL 字符串，没有 XML，没有映射配置
List<User> users = userMapper
        .select()
        .col(User::getName)
        .col(User::getBirthday)
        .where()
        .eq(User::getId, 1)
        .limitPage(1, 10)
        .exs();
```

Brief 不是 MyBatis 或 JPA 的封装层，而是一套独立的持久层引擎，提供**两种使用模式**：

- **独立使用**——`brief-speedier`，不依赖任何框架。
- **增强 MyBatis**——`brief-mybatis`，在不改变现有 MyBatis 工程的前提下为其注入 Brief 能力。

---

## 特性总览

- **JQL 链式 API**——用类型化的 Java Stream 组装 SELECT / INSERT / UPDATE / DELETE。每一步的返回类型会收窄下一步可用的方法，IDE 等于在帮你写查询。
- **零配置多表关联**——支持 left/inner/right join，自动完成一对一、一对多、多对多映射，无需 resultMap、无需 XML 配置。
- **SQL 函数注解化**——50+ 个 MySQL 函数（`LEFT`、`CONCAT`、`IFNULL`、`CASE WHEN`、`DATE_FORMAT`、`GROUP_CONCAT`……）外加一整套 JSON 函数（`JSON_EXTRACT`、`JSON_UNQUOTE`、`JSON_CONTAINS`、`JSON_OBJECT`……）以字段级注解表达，堆叠即嵌套，Brief 负责渲染 SQL。
- **自动类型转换**——内置 30+ 种转换器，覆盖 `Date`、`LocalDateTime`、枚举、数字、字符串、JSON 之间的互转。
- **批处理自动识别**——插入和更新在条件满足时自动提升为 JDBC 批处理执行。
- **逻辑删除与乐观锁**——以标记字段（`IsDel`、`Version`）声明，框架透明执行。
- **字段透明加解密**——列级 AES 加解密，甚至支持对密文做 `LIKE` 模糊查询。
- **字段脱敏**——`@EmailBlur`、`@PhoneNumBlur`、`@IdCardBlur` 在读取时自动遮掩敏感字段。
- **分表**——可插拔的分表策略（如按月分表），覆盖插入、查询、更新、删除全场景。
- **JSON 字段**——实现 `JsonColumn` 接口，字段自动序列化/反序列化。
- **流式查询**——大数据量结果集流式处理，无需全部载入内存。
- **拦截器与过滤器 SPI**——可在 JQL 层或 JDBC 层接入 SQL 管道。
- **多方言**——MySQL、H2、Oracle、SQL Server、ClickHouse、SQLite、PostgreSQL。
- **性能**——在内部基准测试中，Brief 在同等负载下约为 **MyBatis 的 2 倍**。

---

## 快速开始

### 独立使用模式（`brief-speedier`）

不需要 Spring、不需要 MyBatis，只要一个 `DataSource`。

**Maven**

```xml
<properties>
    <brief.version>3.6.11</brief.version>
</properties>

<dependency>
    <groupId>com.javaoffers</groupId>
    <artifactId>brief-speedier</artifactId>
    <version>${brief.version}</version>
</dependency>
```

**使用**

```java
BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
BriefMapper<User> userMapper = speedier.newDefaultBriefMapper(User.class);

List<User> users = userMapper
        .select()
        .colAll()
        .where()
        .limitPage(1, 10)
        .exs();
```

### 增强 MyBatis 模式（`brief-mybatis`）

如果你的项目已经在用 MyBatis，引入 `brief-mybatis`，让 Mapper 继承 `BriefMapper` 即可获得 JQL 能力。原有 MyBatis 行为完全保留——Brief 只做增强，不做改变。

**Maven**

```xml
<dependency>
    <groupId>com.javaoffers</groupId>
    <artifactId>brief-mybatis</artifactId>
    <version>${brief.version}</version>
</dependency>
```

```java
// 继承 BriefMapper 即可获得 JQL——你原有的 MyBatis 方法照常工作
public interface UserMapper extends BriefMapper<User> {

    default User queryUserById(Number id) {
        return select()
                .colAll()
                .where()
                .eq(User::getId, id)
                .ex();
    }
}
```

---

## 模块架构

Brief 以分层 Maven Reactor 组织，每个模块职责单一，按需引入即可。

```
brief
├── brief-helper          底层辅助工具
├── brief-common          注解、上下文、JDBC 执行器、工具类（基础层）
├── brief-core            JQL 链式 API 引擎、SQL 渲染、BriefMapper 代理
├── brief-sqlstatement    内置并重定位的 JSQLParser（SQL AST 解析）
├── brief-encipher        字段透明 AES 加解密
├── brief-sharding        分表策略与路由
├── brief-speedier        独立使用入口（不依赖任何框架）
├── brief-support         方言与框架适配
│   ├── brief-mybatis         增强 MyBatis
│   ├── brief-spring-jdbc     Spring JDBC 适配
│   ├── brief-oracle           Oracle 方言
│   ├── brief-sqlserver        SQL Server 方言
│   ├── brief-clickhouse       ClickHouse 方言
│   ├── brief-pgsql            PostgreSQL 方言
│   └── brief-sqlite           SQLite 方言
└── brief-samples         可运行示例
```

**依赖流向：** `helper` / `common` → `core` → `speedier` · `brief-mybatis` · `sharding` · 各方言模块

---

## 建模

Brief 基于注解驱动和约定优于配置。用 `@BaseModel` 标记的类会按驼峰转下划线规则映射到表名；`@BaseUnique` 标记主键列（联合主键时可标记多个）。

```java
@BaseModel
public class User {

    @BaseUnique
    private Long id;

    private String name;
    private Date birthday;

    private Work work;            // 一对一，自动映射
    private IsDel isDel;          // 逻辑删除标记
    private Version version;      // 乐观锁标记

    @CaseWhen(whens = {
        @CaseWhen.When(when = "money < 10", then = "'pool'"),
        @CaseWhen.When(when = "money > 10000", then = "'rich'")
    }, elseEnd = @CaseWhen.Else("'civilian'"))
    private String moneyDes;       // CASE WHEN … 由注解渲染

    private ExtraInfo extraInfo;   // JSON 字段——实现 JsonColumn 接口

    private List<UserOrder> orders; // 一对多，零配置关联

    // getter / setter …
}
```

JSON 字段只需实现 `JsonColumn` 接口：

```java
public class ExtraInfo implements JsonColumn {
    private String nickName;
    private Integer age;

    // getter / setter …
}
```

---

## 用 JQL 查询

### 字段选择

```java
// 查询全部列
List<User> users = userMapper.select().colAll().where().exs();

// 通过方法引用查询指定列
List<User> users = userMapper
        .select()
        .col(User::getBirthday)
        .col(User::getName)
        .where()
        .exs();
```

### 条件查询与单行返回

```java
User user = userMapper
        .select()
        .colAll()
        .where()
        .eq(User::getId, 1)
        .ex();   // ex() 返回单条；exs() 返回 List
```

> **为什么 `where()` 是必经环节：** Brief 要求在 `ex()`/`exs()` 之前必须经过 `where()`，作为防止意外全表扫描的安全护栏。如果你确实要查询全表数据，显式写 `.where().exs()` 即可。

### 分页查询

```java
List<User> users = userMapper
        .select()
        .col(User::getBirthday)
        .col(User::getName)
        .where()
        .limitPage(1, 10)   // 第 1 页，每页 10 条
        .exs();
```

### 聚合与多表关联

多表 join 无需任何映射配置。用 `leftJoin`/`innerJoin` 传入构造器引用（`UserOrder::new`），再用 `on().oeq(...)` 声明关联条件。

```java
List<User> users = userMapper
        .select()
        .col(User::getId)
        .innerJoin(UserTeacher::new)
            .col(UserTeacher::getTeacherId)
            .on()
            .oeq(User::getId, UserTeacher::getId)   // 跨表关联谓词
        .innerJoin(Teacher::new)
            .col(Teacher::getId)
            .col(AggTag.MAX, Teacher::getName)       // MAX(teacher.name)
            .on()
            .oeq(UserTeacher::getTeacherId, Teacher::getId)
        .where()
        .gt(User::getId, 0)
        .groupBy(Teacher::getId)
        .groupBy(UserTeacher::getTeacherId)
        .groupBy(User::getId)
        .having()
        .gt(AggTag.MAX, User::getId, 0)
        .orderA(User::getId)
        .orderA(Teacher::getId)
        .exs();
```

---

## 插入、更新、删除

### 插入

```java
// 逐列插入
Id id = userMapper
        .insert()
        .col(User::getBirthday, new Date())
        .col(User::getName, "Jom")
        .ex();

// 整对象插入——传入集合时自动优化为批处理
List<Id> ids = userMapper
        .insert()
        .colAll(user)
        .ex();
```

### 更新

Brief 区分**修改**（跳过 `null` 字段）与**更新**（写入 `null` 字段），让你对部分更新有精确控制。

```java
// modifyById 语义：null 字段不写入
userMapper.update().npdateNull()        // null → 不更新
        .col(User::getName, null)
        .where().eq(User::getId, id).ex();

// updateById 语义：null 字段也写入
userMapper.update().updateNull()
        .col(User::getName, null)
        .where().eq(User::getId, id).ex();
```

### 通用 API

对于 80% 的常见场景，`general()` 方法无需手写 JQL 即可完成 CRUD：

```java
userMapper.general().save(user);                   // 插入
userMapper.general().saveOrModify(user);            // INSERT … ON DUPLICATE KEY UPDATE
userMapper.general().saveOrUpdate(user);            // 存在则更新，否则插入
userMapper.general().saveOrReplace(user);          // REPLACE INTO
userMapper.general().saveBatch(collection);         // 批量插入

userMapper.general().modifyById(user);              // 部分更新（跳过 null）
userMapper.general().updateById(user);              // 全量更新（含 null）
userMapper.general().modifyBatchById(collection);   // 批量部分更新
userMapper.general().vsModifyById(user);            // 部分更新 + 版本号校验

userMapper.general().queryById(id);
userMapper.general().queryByIds(id1, id2);
userMapper.general().count();

userMapper.general().removeById(id);                // 物理删除
userMapper.general().logicRemoveById(id);           // 逻辑删除（@IsDel）
```

---

## SQL 函数注解

与其在 SQL 字符串里写函数调用，不如把函数声明在字段上，由 Brief 组装表达式。注解堆叠即函数嵌套——顺序很重要，正如函数的组合顺序一样。

```java
@ColName("name") @Left(10)               // LEFT(name, 10)
private String shortName;

@ColName("name") @Left(10) @Concat({"age"})  // CONCAT(LEFT(name, 10), age)
private String composed;

@ColName("name") @IfNull("'unknown'")    // IFNULL(name, 'unknown')
private String safeName;

@ColName("money")
@IfGt(gt = "100000", ep1 = "'rich'", ep2 = "'poor'")  // IF(money > 100000, 'rich', 'poor')
private String wealthLevel;

@ColName("name")
@GroupConcat(distinct = true,
    orderBy = @GroupConcat.OrderBy(colName = "age", sort = GroupConcat.Sort.DESC),
    separator = "-")
// GROUP_CONCAT(DISTINCT name ORDER BY age DESC SEPARATOR '-')
private String concatenated;
```

### JSON 函数

Brief 提供了一组 MySQL JSON 函数注解，让你可以完全用注解查询和构造 JSON 文档——无需手写原生 SQL。字符串字面量参数（路径、候选值）需自带引号，与其他函数注解保持一致。

```java
// --- 提取（从 JSON 列中读出值）---

@ColName("extra_info") @JsonExtract("'$.nickName'")
// JSON_EXTRACT(extra_info, '$.nickName')

@ColName("extra_info") @JsonExtract("'$.nickName'") @JsonUnquote
// JSON_UNQUOTE(JSON_EXTRACT(extra_info, '$.nickName'))  ← 常见的"取干净字符串"组合

@ColName("extra_info") @JsonExtract({"'$.nickName'", "'$.age'"})
// JSON_EXTRACT(extra_info, '$.nickName', '$.age')

// --- 检视（JSON 文档的元信息）---

@ColName("extra_info") @JsonLength              // JSON_LENGTH(extra_info)
@ColName("extra_info") @JsonLength("'$.tags'")  // JSON_LENGTH(extra_info, '$.tags')
@ColName("extra_info") @JsonKeys                // JSON_KEYS(extra_info)
@ColName("extra_info") @JsonType                // JSON_TYPE(extra_info)  → "OBJECT"、"ARRAY"…
@ColName("extra_info") @JsonValid               // JSON_VALID(extra_info) → 1 或 0
@ColName("extra_info") @JsonDepth               // JSON_DEPTH(extra_info)
@ColName("name") @JsonQuote                    // JSON_QUOTE(name)

// --- 包含判断（常用于 WHERE 条件）---

@ColName("tags") @JsonContains("'java'")
// JSON_CONTAINS(tags, 'java')
@ColName("extra_info") @JsonContains("{\"k\":\"v\"}", path = "'$.meta'")
// JSON_CONTAINS(extra_info, '{"k":"v"}', '$.meta')

// --- 构造（生成 JSON 值）---

@ColName("name") @JsonObject(value = {"'name'"}, position = 1)
// JSON_OBJECT('name', name)

@ColName("tags") @JsonArray(value = {"'prefix'"}, position = 1)
// JSON_ARRAY('prefix', tags)
```

---

## 高级特性

### 字段透明加解密（`brief-encipher`）

声明一个 AES 密钥和待加密的列，Brief 在写入时加密、读取时解密——甚至会将 `LIKE` 谓词针对密文进行改写。

```xml
<dependency>
    <groupId>com.javaoffers</groupId>
    <artifactId>brief-encipher</artifactId>
    <version>${brief.version}</version>
</dependency>
```

```java
@AesEncryptConfig(key = "FFFFFFFFAAAAAAAAAAAAFFFFFAFAFAFA", encryptTableColumns = {
    @EncryptTableColumns(tableName = "encrypt_data", columns = {"encrypt_num"})
})
@Configuration
static class EncryptConfig { }
```

```java
// 存储时自动加密；用明文查询时透明工作
EncryptData result = encryptDataMapper.select().colAll()
        .where().eq(EncryptData::getEncryptNum, "1234567890")
        .ex();
```

### 字段脱敏

在读取时自动遮掩敏感字段，只需在字段上标注注解。被标注的字段必须是 `String` 类型。

```java
@EmailBlur
private String email;   // 12345678@outlook.com → 12***678@outlook.com
```

### 分表（`brief-sharding`）

实现 `ShardingTableStrategy` 并标注分表字段，Brief 自动将插入、查询、更新、删除路由到正确的物理表。

```xml
<dependency>
    <groupId>com.javaoffers</groupId>
    <artifactId>brief-sharding</artifactId>
    <version>${brief.version}</version>
</dependency>
```

```java
public class ShardingTableMonthStrategy implements ShardingTableStrategy<Date> {
    @Override
    public String shardingExactly(ShardingParams<Date> params) {
        return params.getTableName() + "_"
                + DateFormatUtils.format(params.getValueOne(), "yyyy_MM");
    }
    // shardingRange(...) 用于跨分片查询 …
}

@BaseModel("sharding_user")
public class ShardingUser {
    @BaseUnique
    private Long id;
    private String name;

    @ShardingStrategy(ShardingTableMonthStrategy.class)
    private Date birthday;   // 路由键
}
```

```sql
-- Brief 自动将每行路由到对应的分片表：
INSERT INTO sharding_user_2025_08 (name, birthday) VALUES (#{name}, #{birthday})
INSERT INTO sharding_user_2025_10 (name, birthday) VALUES (#{name}, #{birthday})
```

### 拦截器

接入 SQL 管道，添加日志、审计或慢 SQL 处理。实现 `JqlInterceptor` 并注册即可；在 Spring 环境下，`@Component` Bean 会被自动装配。

```java
@Component
public class LogInterceptor implements JqlInterceptor {
    @Override
    public void handler(BaseSQLInfo info) {
        log.info("SQL: {}  参数: {}", info.getSql(), info.getParams());
    }
}
```

---

## 版本与兼容性

| Brief | JDK | MyBatis | Spring Boot |
|-------|-----|--------|-------------|
| 3.6.x | 8+  | 3.5.11 | 2.7.x |

变更记录见 [version.md](version.md)。

---

## 许可证

Brief 基于 [Server Side Public License](LICENSE) 发布。

---

## 参与贡献

Brief 已在内部生产环境中经过实战检验，切实提升了开发效率与代码整洁度。欢迎提交 Bug 报告、功能建议和 Pull Request。如果 Brief 对你有帮助，给仓库点个 ⭐ 是最好的鼓励。
