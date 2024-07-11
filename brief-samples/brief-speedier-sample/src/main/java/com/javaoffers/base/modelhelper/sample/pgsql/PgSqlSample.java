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
import java.util.List;
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
                "CREATE TABLE teacher (\n" +
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
        briefMapper.general().saveOrModify(teacher);

        briefMapper.insert()
                .col(Teacher::getId, 1)
                .col(Teacher::getName, "GOOD")
                .dupUpdate()
                .ex();

        briefMapper.insert()
                .col(Teacher::getId, 2)
                .col(Teacher::getName, "GOOD")
                .dupUpdate()
                .ex();

        briefMapper.insert()
                .col(Teacher::getId, Math.random()*10000)
                .col(Teacher::getName, "GOOD")
                .ex();

        //Id save = briefMapper.general().save(teacher);
//        LOGUtils.printLog(save);
    }

    @Test
    public void testUpdate() throws Exception {
        List<Teacher> list = briefMapper.general().query(1, 10);
        if(list.size() == 0){
            testInsert();
            list = briefMapper.general().query(1, 10);
        }

        Teacher teacher = list.get(0);
        teacher.setName("Hello Word");

        briefMapper.update().npdateNull().colAll(teacher).where().eq(Teacher::getId, teacher.getId()).ex();

        Teacher teacher1 = briefMapper.general().queryById(teacher.getId());
        LOGUtils.printLog(teacher1);
    }
}
