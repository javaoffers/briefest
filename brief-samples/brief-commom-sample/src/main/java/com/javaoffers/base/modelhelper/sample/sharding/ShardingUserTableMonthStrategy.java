package com.javaoffers.base.modelhelper.sample.sharding;

import com.javaoffers.brief.modelhelper.sharding.derive.ShardingParams;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingTableMonthStrategy;
import org.omg.CORBA.StringHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    public void shardingBefore(ShardingParams<Date> shardingParams) {
        if (connection != null) {
            try {
                Set<String> tables = shardingRange(shardingParams);
                for (String tableName : tables) {
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
