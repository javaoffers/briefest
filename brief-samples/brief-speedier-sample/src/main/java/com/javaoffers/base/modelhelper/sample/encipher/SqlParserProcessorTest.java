package com.javaoffers.base.modelhelper.sample.encipher;

import com.javaoffers.brief.modelhelper.parser.ColNameProcessorInfo;
import com.javaoffers.brief.modelhelper.parser.ConditionName;
import com.javaoffers.brief.modelhelper.parser.SqlParserProcessor;
import com.javaoffers.thrid.jsqlparser.JSQLParserException;
import com.javaoffers.thrid.jsqlparser.schema.Column;
import com.javaoffers.thrid.jsqlparser.schema.Table;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.function.Consumer;

public class SqlParserProcessorTest {

    @Test
    public void testSelectSample() throws JSQLParserException {

        //普通单表查询
        String sqlStr = "" +
                "select " +
                "user.id as id, " +
                "user.name as name, " + // 解密(user.NAME) AS name
                "user.birthday as birthday," +
                "name as name2  " + //  解密(NAME) AS name2
                "from  user  " +
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(user.name, 10) "; //left(解密(user.NAME), 10)
        //parseSql(sqlStr);

        sqlStr = "" +
                "select " +
                "user.id as id, " +
                "`user`.`name` as name, " + // 解密(user.NAME) AS name
                "user.birthday as birthday," +
                "`name` as name2,  " + //  解密(NAME) AS name2
                "(case when name = 1000 then 'y' else 'n' end) " + // 解密
                "from  `user`  " +
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(user.`name`, 10) " + //left(解密(user.NAME), 10)
                "and if( name = 1000, 'y', 'n') "; //if(解密(NAME) = 1000, 'y', 'n')
        //parseSql(sqlStr);


        //普通但表查询 (关联子查询),
        sqlStr = "" +
                "select " +
                "if(name = 0, 'z', 'v'), " + //会更改name
                "user.id as id, " +
                "user.name as name, " + //会更改name
                "user.birthday as birthday," +
                "(select name from arder where arder.xx = user.xx  limit 1) as name2,  " + //不会更改name
                "(select name from arder o where o.qq = user.xx  limit 1) as name3   " + //不会更改name.
                "from  user  " +
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(user.name, 10) "; //会更改name
        //parseSql(sqlStr);

        sqlStr = "" +
                "select" +
                " id, " +
                " name, " + //会更改name. 这种会自动加上别名:  解密(NAME) AS name
                " (name), "+ //会更改name. 这种会自动加上别名: (解密(NAME)) AS name
                " (left(name,10)), "+ //会更改name
                " name as nameX," + //会更改name
                " left(name, 10)," + //会更改name
                " if(name is null, 'x', 'xx')," + //会更改name
                " (select name from user where xx=xx)  "+ //会更改name: (SELECT 解密(NAME) AS name FROM user WHERE xx = xx)
                "from  user  " +
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(user.name, 10) "; //会更改name
        //parseSql(sqlStr);


        sqlStr = "select  " +
                "name," +
                "if(name = 0, 'z', 'v'), "+
                //子查询中没有join, 则子查询的colName 识别为子查询的from tableName
                "(select u.name from user u where u.xx = arder.xx) as name, " + //会更改name, 内部会加别名
                "(select name from user  where name = o.xx) as name ," +//会更改name, 内部会加别名
                "(case when name = 1000 then 'y' else 'n' end) " +
                "from arder o " +
                "where left(arder.name, 10) = xx " + //不会更改name
                "and left(name, 10) = xx "; //不会更改name

        //parseSql(sqlStr);

        sqlStr = "select " +
                " name," + //解密
                " age ," + //解密
                " a.name " +
                " from arder a inner join user b where a.name = b.name " + //b.name 会解密
                " ";
        parseSql(sqlStr);

        sqlStr = "" +
                "select " +
                "user.id as id, " +
                "user.name as name, " + //会更改name
                "a.name as name, " + //会更改name
                "user.birthday as birthday," +
                "age  " +
                "from  user a  inner join arder b on b.userId = a.id and arder.name = ? " +//不会更改name
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(a.name, 10) " + //会更改name
                "and left(user.name, 10) "; //会更改name

        //parseSql(sqlStr);

        // where / on 缺失表名的
        sqlStr = "" +
                "select " +
                "user.id as id, " +
                "user.name as name, " + //会更改name
                "user.birthday as birthday," +
                "age  , " +
                "name "+ //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的) . 会加别名
                "from  user   inner join arder  on userId = id and name = ? " + //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的)
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(user.name, 10) "; //会更改name

        //parseSql(sqlStr);

        // where / on 缺失表名的
        sqlStr = "" +
                "select " +
                "user.id as id, " +
                "user.name as name, " + //会更改name
                "user.birthday as birthday," +
                "age  , " +
                "name "+ //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的) . 会加别名
                "from  user a   inner join arder b  on b.userId = a.id and a.name = ? " + //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的)
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(user.name, 10) "; //会更改name

        //parseSql(sqlStr);

        // 子查询中的name解析
        sqlStr = "" +
                "select " +
                "user.id as id, " +
                "(select name from arder where userId = id) as name, " + //不会更改name
                "user.birthday as birthday," +
                "age  , " +
                "name " + //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的) 会加别名
                "from  user inner join arder on userId = id and name = ? and xxb = xxa  " + //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的)
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(name, 10) " +  //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的)
                "and left(user.name, 10) "; //会更改name

        //parseSql(sqlStr);

        // 子查询中的name解析
        sqlStr = "" +
                "select " +
                "user.id as id, " +
                "(select name from `arder` where userId = id) as name, " + //不会更改name
                "user.birthday as birthday," +
                "age ," +
                "a.name , " + //不会更改name
                "a.name as arderName, " + //不会更改name
                "name "+ //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的)
                "from  user  inner join (select name from `arder` where userId = id  and name = arderXX ) a  " + //会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的)
                "where  1=1  " +
                "and user.birthday  between  ? and  ? " +
                "and left(a.name, 10)"+ //不会更改name
                "and left(name, 10)"+//会更改name (如果user & arder 都有name则这条sql是有问题的，前置条件：当我们指定了user.name时， user表中是肯定存在name的)
                "and left(user.name, 10) "; //会更改name

        //parseSql(sqlStr);

        // 子查询中的name解析
        sqlStr = "select " +
                "a.name ," + //解密
                "b.name ," +// 不会解密,因为子查询内部已经解密
                "name " + //解密
                "from user  a inner join (select name , id from user ) b " + //子查询name解密
                "on a.id = b.id and  a.name = b.name " + //a.name 需要解密 , b.name 不需要解密(因为在子查询中已经解密了)
                "where a.name = b.name "; //a.name 需要解密. b.name 不需要解密 (因为在子查询中已经解密了).

        //parseSql(sqlStr);

        // 子查询中的name解析
        sqlStr = "select " +
                "a.name ," + //解密
                "b.xx ," +
                "xx ," +
                "name " + //解密
                "from user  a inner join (select xx , id from tablex ) b " + // xx 会被解密
                "on " +
                "a.id = b.id " +
                "and  a.name = b.xx " + //a.name加密, b.xx 不加密 (因为在子查询中已经解密).
                "where " +
                "a.name = xx "; // a.name 解密.  b.xx 不加密 (因为在子查询中已经解密).

        parseSql(sqlStr);


    }

    //只给占位符加密插入.
    @Test
    public void testInsertSample() throws Exception{
        String sql = "insert into user (name, create_time, age) values( ?, now(), ? )";
        //parseSql(sql);

        sql = "insert into user (name, create_time, age) value( ?, now(), ? )"; //value 会被转换为values
        //parseSql(sql);

        sql = "insert into user (name, age, create_time) values( ?, ? , ? ), ( ?, ?, ? ) ";
        //parseSql(sql);

        sql = "insert into user (name, age, create_time) values( ?, ? , 'xxx' ), ( ?, ?, 'xxa' ) ";
        //parseSql(sql);

        sql = "insert into user (name, age, create_time) values( ?, 'xxx' , 'xxx' ), ( ?, ?, 'xxa' ) "; //不是占位符不加密
        //parseSql(sql);

        sql = "insert into user_temp select name , age, create_time from user   "; //
        //parseSql(sql);

        sql = "insert into user select name , age, create_time from tablex where xx = name   "; //这个语法是错误的, 不能访问 user中的name.
        //parseSql(sql);

        sql = "insert into user_temp (select name , age, create_time from user)   ";//不会解密查询
        //parseSql(sql);

        sql = "insert into user_temp (select name , age, create_time from user where name = 'xx' and name = ?)   ";//where 部分解密
        //parseSql(sql);

        sql = "insert into user_temp select name , age, create_time from user where name = 'xx'   ";//where 部分解密
        //parseSql(sql);

        sql = "insert into user_temp select name , age, create_time from user where name = ?   ";// where 部分解密
        //parseSql(sql);

        sql = "insert into user_temp (name, age, create_time ) select name , age, create_time from user where name = ?   ";// where 部分解密
        //parseSql(sql);

        sql = "insert into user_temp (name, age, create_time ) select name , age, create_time from user_x where name = ?   ";// 测试两张表都不是要拦截的表. 不会进行加解密处理
        //parseSql(sql);

        sql = "insert into user_temp (name, age, create_time ) select name , (select age from user where name = 'xx' ), create_time from user where name = ?   ";// where 部分解密
        //parseSql(sql);

        sql = "insert into user_temp (name, age, create_time ) values ((select name from user where id = 1 and age = 20 ), 2, now())  ";// where 部分解密
        //parseSql(sql);

        sql = "insert into user_temp (name, age, create_time ) values " +
                "((select name from user where id = 1 and age = 20 and name = (select xx from tablex where name = xx ) ), 2, now())  ";// where 部分解密
        parseSql(sql);

    }

    /**
     * 只给占位符加密更新.
     */
    @Test
    public void testUpdateSample() throws JSQLParserException {
        String sql = "update user set name = ? , age= ? , create_time = now() where id = ? and name = ? "; // set 部分加密, where 部分解密
        //parseSql(sql);

        sql = "update user set name = ? , age = ? , create_time = now() where id = ? and exists (select 1 from tablex where xx = user.name) ";// set 部分加密, where 部分解密
        //parseSql(sql);

        sql = "update user set name = ? , age = ? , create_time = now() where id = ? and exists (select name from tablex where xx = name) ";// set 部分加密, where 部分解密
        //parseSql(sql);

        sql = "update user set user.name = ? , age = ? , create_time = now() where id = ? and exists (select 1 from tablex where xx = name and name = 'xxx') ";// set 部分加密, where 部分解密
        //parseSql(sql);

        sql = "update user set name = ? , age = ? , create_time = now() where id = ? and name = 'xx' ";// set 部分加密, where 部分解密
        //parseSql(sql);

        // set 部分加密, where 部分解密. 但是 set name = 'xxx'  不会加密, 因为不是 name = ? 这种占位符号的形式
        sql = "update user set name = 'xxx' ,  age = ? , create_time = now() where id = ? and name = 'xx' ";
        //parseSql(sql);

        sql = "update user set name = (select name from user where id = xx and name = xx) ,  age = ? , create_time = now() where id = ? and name = 'xx' ";
        //parseSql(sql);

        //
        sql = "update user set name = (select name from user where id = xx and name = xx) ,  age = ? , create_time = now() " +
                "where id = ? and name = (select xx from tablex where xx = 'xa') ";
        //parseSql(sql);

        sql = "update user set name = (select name from user where id = xx and name = xx) ,  age = ? , create_time = now() " +
                "where id = ? and name = (select xx from tablex where xx = name) ";
        parseSql(sql);

    }

    @Test
    public void testDeleteSample() throws Exception {
        String sql  = "delete from user where name = 'xxx' ";
        //parseSql(sql);

        sql  = "delete from user where left(name) = 'xxx' ";
        //parseSql(sql);

        sql  = "delete from user where left(user.name) = ? and age = ?  ";
        //parseSql(sql);

        sql  = "delete from user where left(name) = ? and create_time = 'xxx' and age = ?  ";
        //parseSql(sql);

        sql  = "delete from user where right(left(name)) = ? and create_time = 'xxx' and age = ?  ";
        //parseSql(sql);

        sql  = "delete from user where left(name) = 'xx' and create_time = 'xxx' and age = ?  ";
        //parseSql(sql);

        sql  = "delete from user where left(name) = 'xx' and (create_time = 'xxx' and age = ?)  ";
        //parseSql(sql);

        sql  = "delete from user where if( name is null , 0 , 1) = 'xx' and create_time = 'xxx' and age = ?  ";
        //parseSql(sql);

        sql  = "delete from user where if( name = 'xxx' , 0 , 1) = 'xx' and create_time = 'xxx' and age = ?  ";
        //parseSql(sql);

        sql  = "delete from user where if( name = 'xxx' , 0 , 1) = 'xx' and create_time = 'xxx' and age = ?" +
                " and exists(select name from user where name = xx )" +
                " and name = (select xx from tablex where xx = NAME )" +
                " AND NAME = (select (select xx from tablex where xx = name ) from tablex where xx = name )";
        parseSql(sql);

    }

    private void parseSql(String sqlStr) throws JSQLParserException {

        Consumer<ColNameProcessorInfo> processor = colNameProcessorInfo->{
            Column column = colNameProcessorInfo.getColumn();
            ConditionName conditionName = colNameProcessorInfo.getConditionName();
            String columnName = column.getColumnName();
            if(ConditionName.SELECT_COL_NAME == conditionName || ConditionName.isWhereOnName(conditionName)){
                if(column.getTable() != null && StringUtils.isNotBlank(column.getTable().getName())){
                    column.setColumnName("解密("+column.getTable().getName()+"."+columnName.toUpperCase()+")");
                    column.setTable(new Table(""));
                }else{
                    column.setColumnName("解密("+columnName.toUpperCase()+")");
                }

            } else if(ConditionName.VALUES == conditionName){
                column.setColumnName("加密("+columnName+")");
            } else if(ConditionName.UPDATE_SET == conditionName){
                column.setColumnName("加密("+columnName+")");
            }

        };

        String parsedSql = SqlParserProcessor.builder()
                .addProcessor("user", processor)
                .addColName("user", "name")
                .addColName("user", "age")
                .addProcessor("tablex", processor)
                .addColName("tablex", "xx")
                .build().parseSql(sqlStr);
        System.out.println(parsedSql);
        //System.exit(0);
    }

}