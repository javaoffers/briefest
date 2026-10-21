package com.javaoffers.base.modelhelper.sample.sharding;

import com.javaoffers.brief.modelhelper.sharding.derive.ShardingTableMonthStrategy;

import java.sql.Connection;
import java.util.List;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/10/6
 */
public class ShardingUserTableMonthStrategy extends ShardingTableMonthStrategy {

    String sql = "CREATE TABLE if not exists `%s` (\n" +
            "  `id` int NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,\n" +
            "  `birthday` datetime DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=2059 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;";

    volatile static Connection connection;

    @Override
    public void shardingAfter(List<String> shardingTables) {
        if (connection != null) {
            try {
                for (String tableName : shardingTables) {
                    connection.createStatement().execute(String.format(sql, tableName));
                }
            }catch (Exception e) {

            }
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
