package com.javaoffers.base.modelhelper.sample.oracle;

import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.base.modelhelper.sample.model.ViewModel;
import com.javaoffers.base.modelhelper.sample.model.WithAsModel;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import org.junit.Test;

import java.util.List;

public class OracleSample {

    static String jdbc = "jdbc:oracle:thin:@localhost:1521:orcl";
    static BriefSpeedier speedier;

    static {
        try {
            speedier = MockBriefSpeedier.mockBriefSpeedier(jdbc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static BriefMapper<User> userBriefMapper = speedier.newDefaultBriefMapper(User.class);

    @Test
    public void testBase() throws Exception {

        List<User> query = userBriefMapper.general().query(2, 100);

        User oracle = User.builder().name("oracle").build();

        userBriefMapper.general().saveOrModify(oracle);

        oracle.setId(1L);
        userBriefMapper.general().saveOrModify(oracle);

        userBriefMapper.insert().col(User::getName, "oracle").dupUpdate().ex();

        userBriefMapper.insert().col(User::getName, "oracle").col(User::getId, 2).dupUpdate().ex();

        System.exit(0);
    }

    @Test
    public void testWithAs(){
        BriefMapper<WithAsModel> with = speedier.newDefaultBriefMapper(WithAsModel.class);
        with.select().colAll().where().exs();

        BriefMapper<ViewModel> mapper = speedier.newDefaultBriefMapper(ViewModel.class);
        mapper.select().colAll().where().exs();

    }

}
