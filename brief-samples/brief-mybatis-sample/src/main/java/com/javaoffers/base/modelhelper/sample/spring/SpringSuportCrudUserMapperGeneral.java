package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.constant.Sex;
import com.javaoffers.base.modelhelper.sample.constant.Work;
import com.javaoffers.base.modelhelper.sample.mapper.BriefUserMapper;
import com.javaoffers.base.modelhelper.sample.mapper.BriefTeacherMapper;
import com.javaoffers.base.modelhelper.sample.model.Teacher;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.anno.derive.flag.RowStatus;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.javaoffers.brief.modelhelper.utils.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.mapper")
public class SpringSuportCrudUserMapperGeneral implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();
    public static boolean status = true;
    @Resource
    BriefUserMapper crudUserMapper;

    @Resource
    BriefTeacherMapper crudTeacherMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperGeneral.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        testVsModify();
        testIsDel();
        testGeneral();
        testBatchUpdate();
        testCountDistinct();
        testSaveModify();
        testSaveUpdate();
        if(status){
            System.exit(0);
        }



    }
    public void testVsModify() throws JsonProcessingException {
        User user = this.crudUserMapper.general().query(1, 1).get(0);
        user.setWork(Work.JAVA);
        this.crudUserMapper.general().vsModifyById(user);
        user = this.crudUserMapper.general().queryById(user.getId());
        User user2 = this.crudUserMapper.general().queryById(user.getId());

        user.setBirthdayDate(new Date());
        user2.setWork(Work.PYTHON);


        boolean ok = this.crudUserMapper.general().vsModifyById(user) == 1;
        print(ok);

        ok = this.crudUserMapper.general().vsModifyById(user2) == 1;
        print(ok);
    }

    public void testIsDel() throws JsonProcessingException {
        //IsDel sample
        User user = this.crudUserMapper.general().query(1, 1).get(0);
        User logicUser = new User();
        logicUser.setId(user.getId());
        int i = this.crudUserMapper.general().logicRemove(logicUser);
        User newUser = crudUserMapper.general().query(logicUser).get(0);
        print(user);
        print(newUser);

        //RowStatus sample
        Teacher teacher = crudTeacherMapper.general().query(1, 1).get(0);
        print(teacher);
        teacher.setStatus(RowStatus.PRESENCE);
        crudTeacherMapper.general().replaceById(teacher);
        Teacher teacher1 = crudTeacherMapper.general().queryById(teacher.getId());
        print(teacher1);
        crudTeacherMapper.general().logicRemoveById(teacher.getId());
        Teacher teacher2 = crudTeacherMapper.general().queryById(teacher.getId());
        print(teacher2);

    }

    public void testSaveUpdate(){
        List<User> query = this.crudUserMapper.general().query(1, 2);
        User user = query.get(0);
        Long id = user.getId();
        this.crudUserMapper.general().saveOrReplace(user);
        user.setWork(null);
        this.crudUserMapper.general().saveOrReplace(user);
        user.setWork(Work.JAVA);
        user.setIdImpl(null);
        user.setId(null);
        this.crudUserMapper.general().saveOrReplace(user);
        this.crudUserMapper.general().saveOrReplace(query);

        this.crudUserMapper.general().replaceById(user);// not update, because id has no value
        user.setId(id);
        user.setWork(null);
        this.crudUserMapper.general().replaceById(user);// will update
    }

    public void testSaveModify(){
        List<User> query = this.crudUserMapper.general().query(1, 1);
        User user = query.get(0);
        LOGUtils.printLog(user);
        if(user.getWork() == Work.JAVA){
            user.setWork(Work.PYTHON);
        }else{
            user.setWork(Work.JAVA);
        }

        this.crudUserMapper.general().saveOrModify(user);
        user.setId(null);
        user.setIdImpl(null);
        crudUserMapper.general().saveOrModify(user);


    }

    private void testCountDistinct() throws Exception {
        Number count = crudUserMapper.general().count();
        print(count);

        count = crudUserMapper.general().count(User::getId);
        print(count);

        count = crudUserMapper.general().countDistinct(User::getBirthdayDate);
        print(count);

        User user = new User();
        count = crudUserMapper.general().count(user);
        print(count);

        user.setSex(Sex.Boy);
        count = crudUserMapper.general().count(user);
        print(count);

        count = crudUserMapper.general().count(User::getName, user);
        print(count);

        count = crudUserMapper.general().countDistinct(User::getName, user);
        print(count);

        user.setSex(Sex.Girl);
        count = crudUserMapper.general().count(user);
        print(count);

        count = crudUserMapper.general().count(User::getName, user);
        print(count);

        count = crudUserMapper.general().countDistinct(User::getName, user);
        print(count);

        user.setSex(Sex.Boy);
        user.setWork(Work.JAVA);
        count = crudUserMapper.general().count(user);
        print(count);

        count = crudUserMapper.general().count(User::getName, user);
        print(count);

        count = crudUserMapper.general().countDistinct(User::getName, user);
        print(count);

        count = crudUserMapper.general().countDistinct(User::getWork, user);
        print(count);

    }

    public void testBatchUpdate(){
        List<User> users = crudUserMapper.queryAll();
        for(User user : users){
            user.setSex(Sex.Boy);
        }
        crudUserMapper.general().modifyBatchById(users);
    }

    public void testGeneral() throws JsonProcessingException {
        User general = User.builder().name("general").build();
        int remove = crudUserMapper.general().remove(general);

        //save
        long save = crudUserMapper.general().save(general).toLong();


        //query by id
        /**
         * SQL:  update user set birthday = #{0}, id = #{1}, name = #{4} where  1=1  and id in ( #{0} )
         * PAM： [{0=329839, 1=329839, 4=general}]
         */
        User user = crudUserMapper.general().queryById(save);
        print(user);

        //modify By Id
        user.setBirthday(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        crudUserMapper.general().modifyById(user);
        user = crudUserMapper.general().queryById(save);
        print(user);

        //modify By Id, If id is set to null, the update operation will not be triggered
        user.setId(null);//If id is set to null, the update operation will not be triggered
        user.setIdImpl(null);
        user.setCountId(null);//If id is set to null, the update operation will not be triggered
        user.setName("no update");
        int i = crudUserMapper.general().modifyById(user);
        Assert.isTrue(i == 0);

        List<User> query = this.crudUserMapper.general().query(1, 10);
        print(query);
        query.forEach(user1 -> {
            user1.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        });
        this.crudUserMapper.general().modifyBatchById(query);
        List<Long> collect = query.stream().map(User::getId).collect(Collectors.toList());
        List<User> users = this.crudUserMapper.general().queryByIds(collect);
        print(users);

        users = crudUserMapper.general().query(user);
        print(users);

        long count = this.crudUserMapper.general().count().longValue();
        print(count);

        long moneyCount = this.crudUserMapper.general().count(User::getMoney).longValue();
        print(moneyCount);
    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
