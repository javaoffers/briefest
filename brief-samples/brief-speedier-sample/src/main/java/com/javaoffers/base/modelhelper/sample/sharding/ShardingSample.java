package com.javaoffers.base.modelhelper.sample.sharding;

import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.speedier.BriefSpeedierSample;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class ShardingSample {

    static String jdbc = "jdbc:oracle:thin:@localhost:1521:orcl";
    static BriefSpeedier speedier;

    static {
        try {
            speedier = MockBriefSpeedier.mockShardingBriefSpeedier(jdbc, ShardingUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BriefMapper<ShardingUser> userBriefMapper = speedier.newDefaultBriefMapper(ShardingUser.class);

    @Test
    public void testShardingSampleEq(){
        for(int i=0;i<100;i++){
            userBriefMapper.select().colAll().where()
                    .eq(ShardingUser::getId,1)
                    .eq(ShardingUser::getBirthday, DateUtils.addDays(new Date(), -1 * (int)(Math.random() * 100.0)))
                    .exs();
        }
    }

    @Test
    public void testShardingSampleIn(){
        List<ShardingUser> exs = userBriefMapper.select().colAll().where()
                //这两个一样，只会触发一个sql
                .in(ShardingUser::getBirthday, new Date(), DateUtils.addDays(new Date(), -1 * (int)(Math.random() * 100.0)))
//                .eq(ShardingUser::getId, 1)
                .exs();
        System.out.println(exs.size());
    }

    @Test
    public void testShardingSampleIn2(){
        List<ShardingUser> exs = userBriefMapper.select().colAll().where()
                //会触发2个分片，因为有两个月
                .in(ShardingUser::getBirthday, new Date(), DateUtils.addDays(new Date(), -31))
                .exs();
        System.out.println(exs.size());
    }

    @Test
    public void testShardingSampleGt(){
        List<ShardingUser> exs = userBriefMapper.select().colAll().where()
                .gtEq(ShardingUser::getBirthday,  DateUtils.addDays(new Date(), -31))
//                .ltEq(ShardingUser::getBirthday,  new Date()) //重复分片会自动报错
                .exs();
        System.out.println(exs.size());
    }

    @Test
    public void testShardingSampleIsNotNull(){
        userBriefMapper.select().colAll().where().isNotNull(ShardingUser::getBirthday).ex();
    }

    @Test
    public void testShardingSampleBetween(){
        List<ShardingUser> exs = userBriefMapper.select().colAll().where()
                .between(ShardingUser::getBirthday,  DateUtils.addDays(new Date(), -31), new Date())
                .exs();
        System.out.println(exs.size());
    }
}
