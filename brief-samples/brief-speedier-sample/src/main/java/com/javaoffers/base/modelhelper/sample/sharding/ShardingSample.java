package com.javaoffers.base.modelhelper.sample.sharding;

import com.javaoffers.base.modelhelper.sample.MockBriefSpeedier;
import com.javaoffers.base.modelhelper.sample.speedier.BriefSpeedierSample;
import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.core.CrudSQLStatement;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShardingSample {

    static String jdbc = "jdbc:mysql:thin:@localhost:1521:orcl";
    static BriefSpeedier speedier;

    static {
        try {
            speedier = MockBriefSpeedier.mockShardingBriefSpeedier(jdbc, ShardingUser.class,mockBriefJdbcExecutor -> {
                ArrayList<ShardingUser> list = Lists.newArrayList();
                for(int i=0;i<100;i++){
                    ShardingUser shardingUser = new ShardingUser();
                    shardingUser.setId((long)(Math.random() * 1000));
                    shardingUser.setName("name:"+i);
                    shardingUser.setBirthday(DateUtils.addDays(new Date(), -1 * (int)(Math.random() * 100)));
                    list.add(shardingUser);
                }
                Mockito.when(mockBriefJdbcExecutor.queryList(Mockito.any())).thenReturn(list);
            });
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

    @Test
    public void testLimitPage(){

        List<ShardingUser> exs = userBriefMapper.select().colAll().where()
                .between(ShardingUser::getBirthday,  DateUtils.addDays(new Date(), -31), new Date())
                .orderA(ShardingUser::getBirthday)
                .limitPage(2,10)
                .exs();
        System.out.println(exs.size());
        //单条sharding不做逻辑分页分
        exs = userBriefMapper.select().colAll().where()
                .in(ShardingUser::getBirthday,new Date())
                .orderA(ShardingUser::getBirthday)
                .limitPage(2,10)
                .exs();
        System.out.println(exs.size());

    }

    @Test
    public void testShardingSave(){
        ShardingUser shardingUser = new ShardingUser();
        shardingUser.setId(1L);
        shardingUser.setName("name:"+1);
        shardingUser.setBirthday(new Date());
        userBriefMapper.insert().colAll(shardingUser).ex();
    }

    @Test
    public void testShardingSaveBatch(){
        ShardingUser shardingUser = new ShardingUser();
        ArrayList<ShardingUser> list = Lists.newArrayList();
        for(int i=0;i<100;i++){
            shardingUser = new ShardingUser();
            shardingUser.setId(i+1L);
            shardingUser.setName("name:"+1);
            shardingUser.setBirthday(DateUtils.addDays(new Date(), random()));
            list.add(shardingUser);
        }
        List<Id> exs = userBriefMapper.insert().colAll(list).exs();
    }

    @Test
    public  void testShardingSaveCol(){
        this.userBriefMapper.insert()
                .col(ShardingUser::getName,"name:"+1)
                .col(ShardingUser::getBirthday,DateUtils.addDays(new Date(),-1))
                .ex();
        ;
    }

    @Test
    public void testShardingDelete(){
        this.userBriefMapper.delete()
                .where()
                .eq(ShardingUser::getBirthday, DateUtils.addDays(new Date(),random()))
                .ex();
        this.userBriefMapper.delete()
                .where()
                .in(ShardingUser::getBirthday, DateUtils.addDays(new Date(),random()),DateUtils.addDays(new Date(),random()))
                .ex();
    }

    @Test
    public void testShardingUpdate(){
        ShardingUser shardingUser = new ShardingUser();
        for(int i=0;i<10;i++){
            shardingUser = new ShardingUser();
            shardingUser.setId(i+1L);
            shardingUser.setName("name:"+1);
            shardingUser.setBirthday(DateUtils.addDays(new Date(), random()));
            this.userBriefMapper.update().updateNull().colAll(shardingUser)
                    .where().in(ShardingUser::getBirthday,shardingUser.getBirthday()).ex();

        }
    }

    @Test
    public void testShardingUpdateCol(){
        this.userBriefMapper.update().updateNull().col(ShardingUser::getName,"name:"+1)
                .col(ShardingUser::getBirthday,DateUtils.addDays(new Date(),random()))
                .where()
                .eq(ShardingUser::getBirthday, DateUtils.addDays(new Date(),random()))
                .ex();
    }

    @Test
    public void testShardingAll(){
        testShardingSampleEq();
        testShardingSampleIn();
        testShardingSampleIn2();
        testShardingSampleGt();
        testShardingSampleIsNotNull();
        testShardingSampleBetween();
        testLimitPage();
        testShardingSave();
        testShardingSaveBatch();
        testShardingSaveCol();
        testShardingDelete();
        testShardingUpdate();
        testShardingUpdateCol();
    }

    public static int random(){
       return  ((int)((Math.random() * 1000)) * -1);
    }

}
