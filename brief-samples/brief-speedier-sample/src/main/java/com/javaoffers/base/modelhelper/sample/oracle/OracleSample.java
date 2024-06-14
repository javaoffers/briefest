package com.javaoffers.base.modelhelper.sample.oracle;

import com.javaoffers.base.modelhelper.sample.mapper.BriefUserMapper;
import com.javaoffers.base.modelhelper.sample.model.Teacher;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;

import javax.sql.DataSource;
import java.util.List;

public class OracleSample {
    public static void main(String[] args) {
        DataSource dataSource = null;
        BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
        BriefMapper<Teacher> teacherBriefMapper = speedier.newDefaultCrudMapper(Teacher.class);
        List<Teacher> list = teacherBriefMapper.general().query(1, 1);
        LOGUtils.printLog(list);

    }
}
