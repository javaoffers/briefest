package com.javaoffers.base.modelhelper.sample.spring;

import com.javaoffers.base.modelhelper.sample.mapper.ShardingUserMapper;
import com.javaoffers.base.modelhelper.sample.model.ShardingUser;
import com.javaoffers.base.modelhelper.sample.sharding.ShardingUserTableMonthStrategy;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.javaoffers.base.modelhelper.sample.spring.SpringSuportCrudUserMapperSelect.print;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/10/6
 */
@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.mapper")
@Configuration
public class SpringSuportCrudShardingMapperOp implements InitializingBean {
    public static boolean status = true;

    @Resource
    ShardingUserMapper shardingUserMapper;

    @Resource
    public ShardingUserTableMonthStrategy getShardingUserMapper(DataSource dataSource) {
        ShardingUserTableMonthStrategy shardingUserTableMonthStrategy = new ShardingUserTableMonthStrategy();
        shardingUserTableMonthStrategy.setConnection(DataSourceUtils.getConnection(dataSource));
        return shardingUserTableMonthStrategy;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudShardingMapperOp.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testAll();
        if (status) {
            System.exit(0);
        }
    }

    private void testAll() throws Exception {
        List<Date> dates = testShardingInsertBatch();
        testShardingInsert();
        testShardingQuery(dates);
        testShardingDelete(dates);
    }

    private void testShardingDelete(List<Date> dates) throws Exception {
        Integer ex = this.shardingUserMapper
                .delete()
                .where()
                .in(ShardingUser::getBirthday, dates)
                .ex();
        print(ex);
    }

    private void testShardingQuery(List<Date> list) throws Exception {
        List<ShardingUser> exs = this.shardingUserMapper.select()
                .colAll()
                .where()
                .in(ShardingUser::getBirthday, list)
                .exs();
        print(exs);


        List<ShardingUser> userList = this.shardingUserMapper.select()
                .colAll()
                .where()
                .in(ShardingUser::getBirthday, list)
                .limitPage(1, 5)
                .exs();
        print(userList);

        userList = this.shardingUserMapper.select()
                .colAll()
                .where()
                .in(ShardingUser::getBirthday, list)
                .orderD(ShardingUser::getBirthday)
                .limitPage(1, 5)
                .exs();
        print(userList);

        userList = this.shardingUserMapper.select()
                .colAll()
                .where()
                .in(ShardingUser::getBirthday, list)
                .orderD(ShardingUser::getBirthday, ShardingUser::getName)
                .limitPage(1, 5)
                .exs();
        print(userList);

        ArrayList<Date> dates = new ArrayList<>();
        dates.add(list.get(0));
        userList = this.shardingUserMapper.select()
                .colAll()
                .where()
                .in(ShardingUser::getBirthday, dates)
                .limitPage(1, 5)
                .exs();
        print(userList);

    }

    public void testShardingInsert(){
        ShardingUser shardingUser = new ShardingUser();
        Date date = DateUtils.addDays(new Date(), random());
        date.setTime((date.getTime() / 1000) * 1000); // 清除毫秒
        shardingUser.setName("name:"+Math.random() * 1000);
        shardingUser.setBirthday(date);
        Id save = this.shardingUserMapper.general().save(shardingUser);
        testUpdate(date);
    }

    public void testUpdate(Date date){

        ShardingUser shardingUser = this.shardingUserMapper
                .select()
                .colAll()
                .where()
                .eq(ShardingUser::getBirthday, date)
                .limitPage(1, 1)
                .ex();

        this.shardingUserMapper.update().npdateNull()
                .col(ShardingUser::getName, "xxx")
                .where()
                .in(ShardingUser::getId, shardingUser.getId())
                .in(ShardingUser::getBirthday, date)
                .ex();
    }

    public List<Date> testShardingInsertBatch(){
        ShardingUser shardingUser = new ShardingUser();
        ArrayList<ShardingUser> list = Lists.newArrayList();
        List<Date> dates = Lists.newArrayList();
        for(int i=0;i<10;i++){
            shardingUser = new ShardingUser();
            shardingUser.setName("name:"+i);
            Date date = DateUtils.addDays(new Date(), random());
            date.setTime((date.getTime() / 1000) * 1000); // 清除毫秒
            dates.add(date);
            shardingUser.setBirthday(date);
            list.add(shardingUser);
        }
        List<Id> exs = shardingUserMapper.insert().colAll(list).exs();
        print(exs);
        return dates;
    }

    public static int random(){
        return  ((int)((Math.random() * 100)) * -1);
    }
}
