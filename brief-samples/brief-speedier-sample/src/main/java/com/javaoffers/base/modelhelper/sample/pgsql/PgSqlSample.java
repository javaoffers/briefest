package com.javaoffers.base.modelhelper.sample.pgsql;

import com.javaoffers.base.modelhelper.sample.model.Teacher;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.anno.derive.flag.RowStatus;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedierDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * docker run -d --name pgsql --privileged=true -p 5432:5432 -e POSTGRES_PASSWORD=123asd postgres
 *
 * CREATE TABLE teacher (
 *     id SERIAL PRIMARY KEY,
 *     name VARCHAR(100),
 *     status INT
 * );
 */
public class PgSqlSample {

    static String jdbc = "jdbc:postgresql://localhost:5432/postgres";
//    static Properties props = new Properties();
    static BriefSpeedier instance;
    static BriefMapper<Teacher> briefMapper;
    static {
//        props.put("user", "postgres");
//        props.put("password", "123asd");
        BriefSpeedierDataSource dataSource = BriefSpeedierDataSource.getInstance("", jdbc, "postgres", "123asd");
        instance = BriefSpeedier.getInstance(dataSource);
        briefMapper = instance.newDefaultBriefMapper(Teacher.class);

    }

    @Test
    public void testConnection() throws Exception {
        String ddlSQL = briefMapper.general().ddlSQL(
                "CREATE TABLE teacher5 (\n" +
                        "    id SERIAL PRIMARY KEY,\n" +
                        "    name VARCHAR(100),\n" +
                        "    status INT\n" +
                        ");");
        LOGUtils.printLog(ddlSQL);
    }

    @Test
    public void testInsert() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("Hello");
        teacher.setStatus(RowStatus.PRESENCE);
        Id save = briefMapper.general().save(teacher);
        LOGUtils.printLog(save);
    }
}
