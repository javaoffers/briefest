package com.javaoffers.base.modelhelper.sample;

import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import com.javaoffers.brief.modelhelper.speedier.SpeedierBriefContext;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class MockBriefSpeedier {
    public static BriefSpeedier mockBriefSpeedier(String jdbc) throws Exception {
        DataSource dataSource = Mockito.mock(DataSource.class);
        Connection connection = Mockito.mock(Connection.class);
        DatabaseMetaData databaseMetaData = Mockito.mock(DatabaseMetaData.class);


        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.getMetaData()).thenReturn(databaseMetaData);
        Mockito.when(databaseMetaData.getURL()).thenReturn(jdbc);


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
        return speedier;
    }
}
