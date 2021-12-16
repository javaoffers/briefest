```$xslt
               Mybatis                                  java                                     SQL

               integer                          int OR Integer                              INTEGER

               long                              long OR java.lang.Long               BIGINT

               short                             short OR java.lang.Short             SMALLINT

               float                              float OR java.lang.Float               FLOAT

               double                          double OR java.lang.Double        DOUBLE

               big_decimal                  java.math.BigDecimal                  NUMERIC

               character                      java.lang.String                            CHAR(1)

               string                            java.lang.String                             VARCHAR

               byte                              byte OR java.lang.Byte                 TINYINT

               boolean                        boolean OR java.lang.Boolean     BIT

               yes_no                         boolean OR java.lang.Boolean     CHAR(1) ('Y' OR 'N')

               true_false                     boolean OR java.lang.Boolean     CHAR(1) ('Y' OR 'N')

               date                              java.util.Date OR java.sql.Date     DATE

               time                              java.util.Date OR java.sql.Time     TIME

               timestamp                    java.util.Date OR java.sql.TimeStamp    TIMESTAMP

               calendar                       java.util.Calendar                           TIMESTAMP   

               calendar_date              java.util.Calendar                           DTAE

               binary                           byte[]                                              VARBINARY OR BLOB

               text                               java.lang.String                              CLOB

               serializable                   java.io.Seriailzable                         VARBINARY OR BLOB

               clob                              java.sql.Clob                                   CLOB

               blob                              java.sql.Blob                                   BLOB

               class                            java.lang.Class                               VARCHAR                                

               locale                           java.util.Locale                               VARCHAR 

               timezone                      java.util.TimeZone                          VARCHAR  

               currency                       java.util.Currency                           VARCHAR

```
```text

提示 从 3.4.5 开始，MyBatis 默认支持 JSR-310（日期和时间 API） 。
类型处理器	Java 类型	JDBC 类型
BooleanTypeHandler	java.lang.Boolean, boolean	数据库兼容的 BOOLEAN
ByteTypeHandler	java.lang.Byte, byte	数据库兼容的 NUMERIC 或 BYTE
ShortTypeHandler	java.lang.Short, short	数据库兼容的 NUMERIC 或 SMALLINT
IntegerTypeHandler	java.lang.Integer, int	数据库兼容的 NUMERIC 或 INTEGER
LongTypeHandler	java.lang.Long, long	数据库兼容的 NUMERIC 或 BIGINT
FloatTypeHandler	java.lang.Float, float	数据库兼容的 NUMERIC 或 FLOAT
DoubleTypeHandler	java.lang.Double, double	数据库兼容的 NUMERIC 或 DOUBLE
BigDecimalTypeHandler	java.math.BigDecimal	数据库兼容的 NUMERIC 或 DECIMAL
StringTypeHandler	java.lang.String	CHAR, VARCHAR
ClobReaderTypeHandler	java.io.Reader	-
ClobTypeHandler	java.lang.String	CLOB, LONGVARCHAR
NStringTypeHandler	java.lang.String	NVARCHAR, NCHAR
NClobTypeHandler	java.lang.String	NCLOB
BlobInputStreamTypeHandler	java.io.InputStream	-
ByteArrayTypeHandler	byte[]	数据库兼容的字节流类型
BlobTypeHandler	byte[]	BLOB, LONGVARBINARY
DateTypeHandler	java.util.Date	TIMESTAMP
DateOnlyTypeHandler	java.util.Date	DATE
TimeOnlyTypeHandler	java.util.Date	TIME
SqlTimestampTypeHandler	java.sql.Timestamp	TIMESTAMP
SqlDateTypeHandler	java.sql.Date	DATE
SqlTimeTypeHandler	java.sql.Time	TIME
ObjectTypeHandler	Any	OTHER 或未指定类型
EnumTypeHandler	Enumeration Type	VARCHAR 或任何兼容的字符串类型，用来存储枚举的名称（而不是索引序数值）
EnumOrdinalTypeHandler	Enumeration Type	任何兼容的 NUMERIC 或 DOUBLE 类型，用来存储枚举的序数值（而不是名称）。
SqlxmlTypeHandler	java.lang.String	SQLXML
InstantTypeHandler	java.time.Instant	TIMESTAMP
LocalDateTimeTypeHandler	java.time.LocalDateTime	TIMESTAMP
LocalDateTypeHandler	java.time.LocalDate	DATE
LocalTimeTypeHandler	java.time.LocalTime	TIME
OffsetDateTimeTypeHandler	java.time.OffsetDateTime	TIMESTAMP
OffsetTimeTypeHandler	java.time.OffsetTime	TIME
ZonedDateTimeTypeHandler	java.time.ZonedDateTime	TIMESTAMP
YearTypeHandler	java.time.Year	INTEGER
MonthTypeHandler	java.time.Month	INTEGER
YearMonthTypeHandler	java.time.YearMonth	VARCHAR 或 LONGVARCHAR
JapaneseDateTypeHandler	java.time.chrono.JapaneseDate	DATE


```