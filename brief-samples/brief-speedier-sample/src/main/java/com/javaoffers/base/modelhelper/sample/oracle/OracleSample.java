package com.javaoffers.base.modelhelper.sample.oracle;

import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;

import java.util.List;

public class OracleSample {

    static String jdbc = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static void main(String[] args) throws Exception {

        BriefSpeedier speedier = MockBriefSpeedier.mockBriefSpeedier(jdbc);

        BriefMapper<User> userBriefMapper = speedier.newDefaultBriefMapper(User.class);
        List<User> query = userBriefMapper.general().query(2, 100);

        User oracle = User.builder().name("oracle").build();

        userBriefMapper.general().saveOrModify(oracle);

        oracle.setId(1L);
        userBriefMapper.general().saveOrModify(oracle);

        userBriefMapper.insert().col(User::getName, "oracle").dupUpdate().ex();

        userBriefMapper.insert().col(User::getName, "oracle").col(User::getId, 2).dupUpdate().ex();

        System.exit(0);
    }

}
