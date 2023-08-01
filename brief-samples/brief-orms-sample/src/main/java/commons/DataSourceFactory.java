package commons;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceFactory {

    private static int i = 0;

    public static DataSource getDataSource() {
//        DataSource dataSource = new EmbeddedDatabaseBuilder()
//                .setName("db" + (i++))
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("schema.sql")
//                .build();

        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setName("db" + (i++))
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();


        initData(dataSource);

        return dataSource;

//        P6DataSource retDatasource = new P6DataSource(dataSource);
//        return retDatasource;
    }

    public static void initData(DataSource dataSource) {
        for (int i = 1; i <= 20000; i++) {
            try (Connection connection = dataSource.getConnection()) {
                String userName = "admin" + i;

                String sql = "INSERT INTO tb_account (" +
                        "user_name, password, salt, nickname, email, mobile, avatar, type, status, created)" +
                        " VALUES " +
                        "('" + userName + "', 'ba95c7c416de308531529c43', 'BgS1qIxWd_5-RFmePSs9BJmLQ5ejxZyt', 'Michael', 'michael@gmail.com', NULL, NULL, NULL, NULL, NULL);";
                Statement statement = connection.createStatement();
                statement.execute(sql);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
