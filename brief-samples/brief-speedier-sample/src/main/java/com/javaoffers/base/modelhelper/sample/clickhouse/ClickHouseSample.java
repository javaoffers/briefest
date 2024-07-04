package com.javaoffers.base.modelhelper.sample.clickhouse;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * https://clickhouse.com/docs/en/getting-started/quick-start
 */
public class ClickHouseSample {

    static String url = "jdbc:ch://localhost:8123/default";
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("username","default");
        properties.put("password","");//密码不用填写
        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
        BriefSpeedier briefSpeedier = BriefSpeedier.getInstance(dataSource);
        BriefMapper<MyFirstTable> briefMapper = briefSpeedier.newDefaultBriefMapper(MyFirstTable.class);
        List<MyFirstTable> query = briefMapper.general().query(1, 10);
        LOGUtils.printLog(query);

        briefMapper.general().saveOrModify(query);

        query = briefMapper.general().query(1, 10);
        LOGUtils.printLog(query);

        System.exit(0);

    }
}
