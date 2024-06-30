package com.javaoffers.base.modelhelper.sample.sqlserver;

import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;

public class SqlServerSample {

    static String jdbc = "jdbc:sqlserver://[serverName[instanceName][:portNumber]][;property=value[;property=value]]";
    public static void main(String[] args) throws Exception {

        BriefSpeedier briefSpeedier = MockBriefSpeedier.mockBriefSpeedier(jdbc);
        BriefMapper<User> briefMapper = briefSpeedier.newDefaultBriefMapper(User.class);
        briefMapper.general().query(2,100);

        briefMapper.general().saveOrModify(User.builder().name("sql server").build());

        briefMapper.general().saveOrModify(User.builder().name("sql server").id(1L).build());

        System.exit(0);
    }
}
