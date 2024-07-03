package com.javaoffers.base.modelhelper.sample.clickhouse;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

public class ClickHouseSample {

    static String url = "jdbc:ch://localhost:8123/system";
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
        try (Connection conn = dataSource.getConnection("default", "94309d50-4f52-5250-31bd-74fecac179db");
             Statement stmt = conn.createStatement()) {
            LOGUtils.printLog(stmt.getFetchSize());
        }
    }
}
