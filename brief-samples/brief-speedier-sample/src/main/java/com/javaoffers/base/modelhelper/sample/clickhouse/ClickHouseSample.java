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
        String showTables = briefMapper.general().nativeSQL("show tables ");
        LOGUtils.printLog(showTables);

        String databases = briefMapper.general().nativeSQL("show databases;");
        LOGUtils.printLog(databases);

        String data = briefMapper.general().nativeSQL("select * from my_first_table");
        LOGUtils.printLog(data);

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("message","Insert a lot of rows per batch");
        data = briefMapper.general().nativeSQL("select * from my_first_table where message = #{message}", map);
        LOGUtils.printLog(data);
    }
}
