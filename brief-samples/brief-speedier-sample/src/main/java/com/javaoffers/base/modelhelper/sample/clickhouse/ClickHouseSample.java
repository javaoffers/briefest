package com.javaoffers.base.modelhelper.sample.clickhouse;

import com.clickhouse.client.internal.google.common.collect.Maps;
import com.clickhouse.jdbc.ClickHouseDataSource;
import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * https://clickhouse.com/docs/en/getting-started/quick-start
 */
public class ClickHouseSample {

    static String url = "jdbc:ch://localhost:8123/default";
    static Properties properties = new Properties();
    static ClickHouseDataSource dataSource = null;
    static BriefSpeedier briefSpeedier = null;
    static BriefMapper<MyFirstTable> briefMapper;
    static {
        try {
            properties.put("username","default");
            properties.put("password","");//密码不用填写
            dataSource = new ClickHouseDataSource(url, properties);
            briefSpeedier = BriefSpeedier.getInstance(dataSource);
            briefMapper = briefSpeedier.newDefaultBriefMapper(MyFirstTable.class);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public  void main() throws Exception {

        List<MyFirstTable> query = briefMapper.general().query(1, 10);
        LOGUtils.printLog(query);
        query.forEach(myFirstTable -> {
            myFirstTable.setTimestamp(new Date());
            myFirstTable.setUserId(100L);
            myFirstTable.setMessage("GOOD");
        });

        briefMapper.general().saveOrModify(query);

        query = briefMapper.general().query(1, 10000);
        LOGUtils.printLog(query);

        System.exit(0);

    }

    @Test
    public void testNative(){
        String showTables = briefMapper.general().ddlSQL("show tables ");
        LOGUtils.printLog(showTables);

        String databases = briefMapper.general().ddlSQL("show databases;");
        LOGUtils.printLog(databases);

        String data = briefMapper.general().ddlSQL("select * from my_first_table");
        LOGUtils.printLog(data);

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("message","Insert a lot of rows per batch");
        data = briefMapper.general().ddlSQL("select * from my_first_table where message = #{message}", map);
        LOGUtils.printLog(data);

        String explain = briefMapper.general().ddlSQL("explain select * from my_first_table");
        LOGUtils.printLog(explain);

    }

    @Test
    public void testInsertNative(){
        String insertInfo = briefMapper.general().ddlSQL("INSERT INTO my_first_table (user_id, message, timestamp, metric) VALUES\n" +
                "    (201, 'Hello, ClickHouse!',                                 now(),       -1.0    ),\n" +
                "    (302, 'Insert a lot of rows per batch',                     yesterday(), 1.41421 ),\n" +
                "    (402, 'Sort your data based on your commonly-used queries', today(),     2.718   ),\n" +
                "    (501, 'Granules are the smallest chunks of data read',      now() + 5,   3.14159 )");
        LOGUtils.printLog(insertInfo);

        List<MyFirstTable> exs = briefMapper.select().colAll().where().eq(MyFirstTable::getUserId, 402).exs();
        LOGUtils.printLog(exs);
    }

    @Test
    public void testNativeSql(){
        String table = briefMapper.general().ddlSQL("show create table my_first_table");
        LOGUtils.printLog(table);
    }
}
