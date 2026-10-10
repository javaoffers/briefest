<p align="center">
  <h1 align="center">Brief</h1>
  <p align="center">A high-performance, zero-configuration ORM framework that lets you write SQL in fluent Java.</p>
</p>

<p align="center">
  <a href="https://github.com/javaoffers/briefest/blob/develop/readmeCN.md">中文文档</a>
  &nbsp;·&nbsp;
  <a href="https://central.sonatype.com/artifact/com.javaoffers/brief-speedier">Maven Central</a>
  &nbsp;·&nbsp;
  <a href="https://github.com/javaoffers/briefest">GitHub</a>
</p>

---

## Why Brief

Brief is a lightweight ORM framework built around a single idea: **writing SQL should feel like writing Java**.

Instead of hand-writing SQL strings or wrestling with XML mappers, you compose queries through a strongly-typed, IDE-hintable fluent API called **JQL** (Java Query Language). The compiler catches your mistakes; the IDE guides your next keystroke; and the resulting code reads like a stream pipeline.

```java
// The entire query, end to end — no SQL string, no XML, no mapping config.
List<User> users = userMapper
        .select()
        .col(User::getName)
        .col(User::getBirthday)
        .where()
        .eq(User::getId, 1)
        .limitPage(1, 10)
        .exs();
```

Brief is not a wrapper over MyBatis or JPA. It is an independent persistence engine that ships in **two modes**:

- **Standalone** — `brief-speedier`, with zero framework dependencies.
- **MyBatis Enhancement** — `brief-mybatis`, which augments an existing MyBatis setup without breaking anything.

---

## Highlights

- **JQL fluent API** — Compose SELECT / INSERT / UPDATE / DELETE as typed Java streams. The return type of each step narrows the next available calls, so the IDE effectively writes the query for you.
- **Zero-configuration multi-table joins** — Left/inner/right joins with automatic one-to-one, one-to-many, and many-to-many mapping. No result maps, no `association`/`collection` XML.
- **SQL functions as annotations** — 50+ MySQL functions (`LEFT`, `CONCAT`, `IFNULL`, `CASE WHEN`, `DATE_FORMAT`, `GROUP_CONCAT`, …) expressed as field-level annotations. Compose them by stacking; Brief renders the SQL.
- **Automatic type conversion** — 30+ built-in converters between `Date`, `LocalDateTime`, enums, numbers, `String`, JSON, and more.
- **Batch execution auto-detection** — Inserts and updates are automatically promoted to JDBC batch execution when beneficial.
- **Logical delete & optimistic locking** — Declared as marker fields (`IsDel`, `Version`), enforced transparently.
- **Transparent field encryption** — AES encryption/decryption at the column level, including `LIKE` queries against ciphertext.
- **Field-level desensitization** — `@EmailBlur`, `@PhoneNumBlur`, `@IdCardBlur` mask sensitive fields on read.
- **Table sharding** — Pluggable sharding strategies (e.g. by month) for insert, query, update, and delete.
- **JSON columns** — Implement `JsonColumn` and the field is (de)serialized automatically.
- **Streaming query support** — Process large result sets without loading them into memory.
- **Interceptor & filter SPI** — Hook the SQL pipeline at the JQL level or the JDBC level.
- **Multi-dialect** — MySQL, H2, Oracle, SQL Server, ClickHouse, SQLite, PostgreSQL.
- **Performance** — In internal benchmarks, Brief runs roughly **2× faster than MyBatis** on equivalent workloads.

---

## Quick Start

### Standalone mode (`brief-speedier`)

No Spring, no MyBatis — just a `DataSource`.

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

**Usage**

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

### MyBatis enhancement mode (`brief-mybatis`)

If your project already uses MyBatis, add `brief-mybatis` and let your mappers extend `BriefMapper`. Existing MyBatis behavior is fully preserved — Brief only adds capability.

**Maven**

```xml
<dependency>
    <groupId>com.javaoffers</groupId>
    <artifactId>brief-mybatis</artifactId>
    <version>${brief.version}</version>
</dependency>
```

```java
// Extend BriefMapper to gain JQL — your existing MyBatis methods keep working.
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

## Module Architecture

Brief is organized as a layered Maven reactor. Each module has a single responsibility, so you only pull in what you need.

```
brief
├── brief-helper          Low-level helpers
├── brief-common          Annotations, context, JDBC executors, utilities (foundation)
├── brief-core            JQL fluent API engine, SQL rendering, BriefMapper proxy
├── brief-sqlstatement    Bundled & relocated JSQLParser (SQL AST)
├── brief-encipher        Transparent AES field encryption/decryption
├── brief-sharding        Table-sharding strategies and routing
├── brief-speedier        Standalone entry point (no framework deps)
├── brief-support         Dialect & framework adapters
│   ├── brief-mybatis         Enhances MyBatis with Brief
│   ├── brief-spring-jdbc     Spring JDBC adapter
│   ├── brief-oracle           Oracle dialect
│   ├── brief-sqlserver        SQL Server dialect
│   ├── brief-clickhouse       ClickHouse dialect
│   ├── brief-pgsql            PostgreSQL dialect
│   └── brief-sqlite           SQLite dialect
└── brief-samples         Runnable examples
```

**Dependency flow:** `helper` / `common` → `core` → `speedier` · `brief-mybatis` · `sharding` · dialects

---

## Modeling

Brief is annotation-driven and convention-based. A class annotated `@BaseModel` maps to a table by camelCase→snake_case conversion; `@BaseUnique` marks the primary key column(s).

```java
@BaseModel
public class User {

    @BaseUnique
    private Long id;

    private String name;
    private Date birthday;

    private Work work;            // one-to-one, auto-mapped
    private IsDel isDel;          // logical-delete marker
    private Version version;      // optimistic-lock marker

    @CaseWhen(whens = {
        @CaseWhen.When(when = "money < 10", then = "'pool'"),
        @CaseWhen.When(when = "money > 10000", then = "'rich'")
    }, elseEnd = @CaseWhen.Else("'civilian'"))
    private String moneyDes;       // CASE WHEN … rendered from the annotation

    private ExtraInfo extraInfo;   // JSON column — implements JsonColumn

    private List<UserOrder> orders; // one-to-many, zero-config join

    // getters / setters …
}
```

---

## Querying with JQL

### Field selection

```java
// Select all columns
List<User> users = userMapper.select().colAll().where().exs();

// Select specific columns via method references
List<User> users = userMapper
        .select()
        .col(User::getBirthday)
        .col(User::getName)
        .where()
        .exs();
```

### Conditional & single-row

```java
User user = userMapper
        .select()
        .colAll()
        .where()
        .eq(User::getId, 1)
        .ex();   // ex() returns one; exs() returns a List
```

> **Why `where()` is mandatory:** Brief requires an explicit `where()` before `ex()`/`exs()` as a safety guard against accidental full-table scans. For a deliberate full-table query, call `.where().exs()`.

### Pagination

```java
List<User> users = userMapper
        .select()
        .col(User::getBirthday)
        .col(User::getName)
        .where()
        .limitPage(1, 10)   // page 1, page size 10
        .exs();
```

### Aggregation & multi-table join

Joins need no mapping configuration. Use `leftJoin`/`innerJoin` with a constructor reference (`UserOrder::new`), then chain `on().oeq(...)` to express the join predicate.

```java
List<User> users = userMapper
        .select()
        .col(User::getId)
        .innerJoin(UserTeacher::new)
            .col(UserTeacher::getTeacherId)
            .on()
            .oeq(User::getId, UserTeacher::getId)   // cross-table predicate
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

## Insert, Update, Delete

### Insert

```java
// Column-by-column
Id id = userMapper
        .insert()
        .col(User::getBirthday, new Date())
        .col(User::getName, "Jom")
        .ex();

// Whole-model insert — auto-optimized to batch when given a collection
List<Id> ids = userMapper
        .insert()
        .colAll(user)
        .ex();
```

### Update

Brief distinguishes between *modifying* (skip `null` fields) and *updating* (write `null` fields), giving you precise control over partial updates.

```java
// modifyById semantics: null fields are NOT written
userMapper.update().npdateNull()        // null → not written
        .col(User::getName, null)
        .where().eq(User::getId, id).ex();

// updateById semantics: null fields ARE written
userMapper.update().updateNull()
        .col(User::getName, null)
        .where().eq(User::getId, id).ex();
```

### Generic API

For the 80% case, the `general()` API covers CRUD without writing JQL by hand:

```java
userMapper.general().save(user);                   // insert
userMapper.general().saveOrModify(user);           // INSERT … ON DUPLICATE KEY UPDATE
userMapper.general().saveOrUpdate(user);           // exists? update : insert
userMapper.general().saveOrReplace(user);         // REPLACE INTO
userMapper.general().saveBatch(collection);        // batch insert

userMapper.general().modifyById(user);             // partial update (skip nulls)
userMapper.general().updateById(user);             // full update (include nulls)
userMapper.general().modifyBatchById(collection);  // batch partial update
userMapper.general().vsModifyById(user);           // partial update + version check

userMapper.general().queryById(id);
userMapper.general().queryByIds(id1, id2);
userMapper.general().count();

userMapper.general().removeById(id);               // physical delete
userMapper.general().logicRemoveById(id);          // logical delete (@IsDel)
```

---

## SQL Functions as Annotations

Rather than embedding function calls in SQL strings, you declare them on fields and Brief composes the expression. Annotations stack to form nested calls — order matters, just like function composition.

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

---

## Advanced Features

### Transparent field encryption (`brief-encipher`)

Declare an AES key and the columns to encrypt; Brief encrypts on write and decrypts on read — including rewriting `LIKE` predicates against ciphertext.

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
// Stored encrypted; querying by plaintext works transparently.
EncryptData result = encryptDataMapper.select().colAll()
        .where().eq(EncryptData::getEncryptNum, "1234567890")
        .ex();
```

### Field desensitization

Mask sensitive fields on read by annotating the column.

```java
@EmailBlur
private String email;   // 12345678@outlook.com → 12***678@outlook.com
```

### Table sharding (`brief-sharding`)

Implement `ShardingTableStrategy` and annotate the sharding field. Brief routes inserts, queries, updates, and deletes to the correct physical table.

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
    // shardingRange(...) for cross-shard queries …
}

@BaseModel("sharding_user")
public class ShardingUser {
    @BaseUnique
    private Long id;
    private String name;

    @ShardingStrategy(ShardingTableMonthStrategy.class)
    private Date birthday;   // routing key
}
```

```sql
-- Brief routes each row to its shard automatically:
INSERT INTO sharding_user_2025_08 (name, birthday) VALUES (#{name}, #{birthday})
INSERT INTO sharding_user_2025_10 (name, birthday) VALUES (#{name}, #{birthday})
```

### Interceptors

Hook the SQL pipeline to add logging, auditing, or slow-query handling. Implement `JqlInterceptor` and register it; in a Spring environment, `@Component` beans are wired automatically.

```java
@Component
public class LogInterceptor implements JqlInterceptor {
    @Override
    public void handler(BaseSQLInfo info) {
        log.info("SQL: {}  Params: {}", info.getSql(), info.getParams());
    }
}
```

---

## Versioning & Compatibility

| Brief | JDK | MyBatis | Spring Boot |
|-------|-----|--------|-------------|
| 3.6.x | 8+  | 3.5.11 | 2.7.x |

See [version.md](version.md) for the changelog.

---

## License

Brief is distributed under the [Server Side Public License](LICENSE).

---

## Contributing

Brief is battle-tested in internal production and has measurably improved development velocity and code clarity. Contributions — bug reports, feature requests, and pull requests — are welcome. If Brief works for you, a ⭐ on the repo goes a long way.
