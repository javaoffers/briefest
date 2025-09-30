package com.javaoffers.base.modelhelper.sample;

import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorMetadata;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import com.javaoffers.brief.modelhelper.speedier.SpeedierBriefContext;
import com.javaoffers.thrid.jsqlparser.expression.Function;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.function.Consumer;

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

    public static BriefSpeedier mockShardingBriefSpeedier(String jdbc, Class modelClass, Consumer<MockBriefJdbcExecutor> consumer) throws Exception {
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

        MockBriefJdbcExecutor mockBriefJdbcExecutor = Mockito.mock(MockBriefJdbcExecutor.class);
        Mockito.when(mockBriefJdbcExecutor.getMetadata()).thenReturn(new JdbcExecutorMetadata(dataSource, modelClass));
        if(consumer != null){
            consumer.accept(mockBriefJdbcExecutor);
        }

        MockBriefJdbcExecutorFactory mockBriefJdbcExecutorFactory = Mockito.spy(new MockBriefJdbcExecutorFactory());
        Mockito.when(mockBriefJdbcExecutorFactory.createJdbcExecutor(Mockito.any(DataSource.class),Mockito.any() ))
                .thenReturn(mockBriefJdbcExecutor);


        SpeedierBriefContext spy = Mockito.spy(speedierBriefContext);
        Mockito.when(spy.getJdbcExecutorFactory()).thenReturn(mockBriefJdbcExecutorFactory);
        briefContextField.set(speedier,spy);

        JdbcExecutorFactory jdbcExecutorFactory = spy.getJdbcExecutorFactory();
        LOGUtils.printLog(jdbcExecutorFactory);
        spy.fresh();
        return speedier;
    }
}
