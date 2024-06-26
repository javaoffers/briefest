package com.javaoffers.base.modelhelper.sample.oracle;

import com.javaoffers.base.modelhelper.sample.MockBriefJdbcExecutorFactory;
import com.javaoffers.base.modelhelper.sample.mapper.BriefUserMapper;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutor;
import com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedierDataSource;
import com.javaoffers.brief.modelhelper.speedier.SpeedierBriefContext;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;

public class OracleSample {
    public static void main(String[] args) throws Exception {

        DataSource dataSource = Mockito.mock(DataSource.class);
        Connection connection = Mockito.mock(Connection.class);
        DatabaseMetaData databaseMetaData = Mockito.mock(DatabaseMetaData.class);


        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.getMetaData()).thenReturn(databaseMetaData);
        Mockito.when(databaseMetaData.getURL()).thenReturn("jdbc:oracle:thin:@localhost:1521:orcl");


        BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
        Field briefContextField = speedier.getClass()
                .getDeclaredField("briefContext");
        briefContextField.setAccessible(true);
        SpeedierBriefContext speedierBriefContext = (SpeedierBriefContext)briefContextField.get(speedier);

        SpeedierBriefContext spy = Mockito.spy(speedierBriefContext);
        Mockito.when(spy.getJdbcExecutorFactory()).thenReturn(new MockBriefJdbcExecutorFactory());
        briefContextField.set(speedier,spy);

        JdbcExecutorFactory jdbcExecutorFactory = spy.getJdbcExecutorFactory();
        LOGUtils.printLog(jdbcExecutorFactory);
        spy.fresh();

        BriefMapper<User> userBriefMapper = speedier.newDefaultCrudMapper(User.class);
        List<User> query = userBriefMapper.general().query(2, 100);

    }
}
