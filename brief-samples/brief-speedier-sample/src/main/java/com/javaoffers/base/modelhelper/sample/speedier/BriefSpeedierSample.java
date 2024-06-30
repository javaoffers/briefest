package com.javaoffers.base.modelhelper.sample.speedier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.constant.Work;
import com.javaoffers.base.modelhelper.sample.mapper.BriefEncryptDataMapper;
import com.javaoffers.base.modelhelper.sample.mapper.BriefUserMapper;
import com.javaoffers.base.modelhelper.sample.model.EncryptData;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.base.modelhelper.sample.model.UserOrder;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.encrypt.BriefEncipher;
import com.javaoffers.brief.modelhelper.fun.crud.WhereSelectFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.LeftWhereSelectFunImpl;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedierDataSource;
import com.javaoffers.brief.modelhelper.speedier.transaction.SpeedierTransactionManagement;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BriefSpeedierSample {
    ObjectMapper objectMapper = new ObjectMapper();
    public static String driver6 = "com.mysql.cj.jdbc.Driver";//mysql connection 6
    public static String url6 = "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowMultiQueries=true";//6 需要带时区
    static String  jdbcUrl = "jdbc:mysql://127.0.0.1:3306/base"+url6;
    static String username = "root";
    static String password = "zqbxcmj";
    static BriefSpeedierDataSource dataSource = BriefSpeedierDataSource.getInstance(driver6, jdbcUrl, username, password);
    BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
    //Using custom mapper
    BriefUserMapper crudUserMapper = speedier.newCustomBriefMapper(BriefUserMapper.class);
    //Use the default mapper
    BriefMapper<User> userBriefMapper = speedier.newDefaultBriefMapper(User.class);
    //Use the default mapper
    BriefMapper<UserOrder> userOrderBriefMapper = speedier.newDefaultBriefMapper(UserOrder.class);

    BriefEncryptDataMapper briefEncryptDataMapper = speedier.newCustomBriefMapper(BriefEncryptDataMapper.class);

    @Test
    public void testAll(){
        //testGenMapper();
        //testAutoUpdate();
        testBriefSpeedier();
        testBriefEncrypt();
        testTransaction(true);
        testTransaction(false);
    }

    //自动更新功能下掉.
    @Deprecated
    public void testTransaction(boolean isCommit){
//        SpeedierTransactionManagement transactionManagement = speedier.getTransactionManagement();
//        transactionManagement.openTransaction();
//        User user = new User();
//        user.setName("testTransaction : "+isCommit);
//        userBriefMapper.general().save(user);
//
//        UserOrder userOrder = new UserOrder();
//        userOrder.setOrderName("testTransactionOrder : "+isCommit);
//        userOrderBriefMapper.general().save(userOrder);
//        if(isCommit){
//            transactionManagement.commitTransaction();
//        }else{
//            transactionManagement.rollbackTransaction();
//        }
    }

    public void testGenMapper(){
        BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
        BriefSpeedier speedier2 = BriefSpeedier.getInstance(dataSource);

        BriefMapper<User> userBriefMapper = speedier.newDefaultBriefMapper(User.class);
        BriefMapper<User> userBriefMapper2 = speedier2.newDefaultBriefMapper(User.class);

        print(userBriefMapper.hashCode());
        print(userBriefMapper2.hashCode());
    }


    public void testBriefSpeedier(){

        List<User> userList = crudUserMapper.select().colAll().where().limitPage(1, 10).exs();
        print(userList);

        User user = new User();
        user.setName("testBriefSpeedier");
        user.setBirthdayDate(new Date());
        user.setWork(Work.JAVA);
        Id save = crudUserMapper.general().save(user);
        print(save.toLong());
//        Number countNum = crudUserMapper.general().count();
//        //数据量不要太大. 如果想测试一下数据量无限制可以将countNum.intValue() < 10000 条件注释掉.
//        for(int i=0; i<1 && countNum.intValue() < 10000; i++) {
//            List<User> users = crudUserMapper.queryAll();
//            print("size "+users.size());
//            print(user);
//        }

        userList = userBriefMapper.select().colAll().where().limitPage(1, 10).exs();
        print(userList);

        Number count = userBriefMapper.general().count();
        print(count);
    }


    public void testBriefEncrypt(){
        String key = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAF";
        BriefEncipher
                .getInstance()
                .addEncryptConfig(key, "encrypt_data", new String[]{"encrypt_num"})
                .initEncryptConfig();

        EncryptData encryptData = new EncryptData();
        encryptData.setEncryptNum("12345678");
        Id id = this.briefEncryptDataMapper.general().save(encryptData);
        EncryptData encryptData1 = this.briefEncryptDataMapper.general().queryById(id);
        print(encryptData1);

    }

    public void print(Object user) {
        try {
            System.out.println(objectMapper.writeValueAsString(user));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
