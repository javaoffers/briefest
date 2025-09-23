package com.javaoffers.base.modelhelper.sample.mysql;

import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.model.Teacher;
import com.javaoffers.base.modelhelper.sample.model.UserTeacher;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import org.junit.Test;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/9/4
 */
public class MysqlSample {
    static String jdbc = "jdbc:mysql://127.0.0.1:3306/data_base?useUnicode=true&characterEncoding=utf8";
    static BriefSpeedier speedier;
    static BriefMapper<Teacher> briefMapper;
    static {
        try {
            speedier = MockBriefSpeedier.mockBriefSpeedier(jdbc);
            briefMapper = speedier.newDefaultBriefMapper(Teacher.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //fix #50 test case
    @Test
    public void testOnFun(){
        System.out.println("-------");
        briefMapper.select()
                .colAll()
                .innerJoin(UserTeacher::new)
                .on()
                .oeq(Teacher::getId, UserTeacher::getTeacherId)
                .where()
                .exs();
        /**
         * print log:
         * 2025-09-04 10:19:30 - SQL:   select teacher.id as teacher__id, teacher.name as teacher__name, teacher.status as teacher__status  from  teacher   inner join  user_teacher   on 1=1 and  teacher.id  =  user_teacher.teacher_id  where  1=1
         * 2025-09-04 10:19:30 - PAM: [{}]
         * 2025-09-04 10:19:30 - COST TIME : 8, SIZE: 0
         */

    }

    @Test
    public void testStreamReturn(){
        int c = briefMapper.select().colAll().where().stream(dto->{
            System.out.printf("dto");
        });
        System.out.println(c);
    }
}
