package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.constant.Sex;
import com.javaoffers.base.modelhelper.sample.constant.Work;
import com.javaoffers.base.modelhelper.sample.mapper.BriefUserMapper;
import com.javaoffers.base.modelhelper.sample.mapper.EncryptDataAuMapper;
import com.javaoffers.base.modelhelper.sample.model.EncryptDataAutoUpdate;
import com.javaoffers.base.modelhelper.sample.model.User;
import com.javaoffers.brief.modelhelper.core.Id;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.mapper")
public class SpringSuportCrudUserMapperUpdate implements InitializingBean {

    ObjectMapper objectMapper = new ObjectMapper();
    public static boolean status = true;
    @Resource
    BriefUserMapper crudUserMapper;

    @Resource
    EncryptDataAuMapper encryptDataAuMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperUpdate.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testAutoUpdate();
        testNpdateNull();
        testUpdate();
        testBatchUpdate();
        if(status){
            System.exit(0);
        }

    }

    public void testAutoUpdate() throws JsonProcessingException {
        EncryptDataAutoUpdate encryptDataAutoUpdate = new EncryptDataAutoUpdate();
        encryptDataAutoUpdate.setEncryptNum("12345678");
        Id id = this.encryptDataAuMapper.general().save(encryptDataAutoUpdate);

        EncryptDataAutoUpdate autoUpdate = this.encryptDataAuMapper.general().queryById(id);
        print(autoUpdate);

        //Update tracking difference
        autoUpdate.setEncryptNum("12345678"); //Not updated, because there is no difference
        autoUpdate.setEncryptNum("0987654321");//he data will be real-time updated differences

        //Cancel the update tracking differences
        autoUpdate.setId(null);
        autoUpdate.setEncryptNum("1987654320");//Not updated update thought has cancelled differences



    }

    public void testNpdateNull(){
        try {
            crudUserMapper.update()
                    .npdateNull()
                    .col(User::getName, null)
                    .where()
                    .eq(User::getId, 1)
                    .ex();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void testBatchUpdate(){
        List<User> users = crudUserMapper.queryAll();
        for(User user : users){
            user.setSex(Sex.Boy);
        }
        crudUserMapper.update().npdateNull()
                .col(User::getWork, Work.JAVA)
                .where()
                .eq(User::getId, users.get(0).getId())
                .addBatch()
                .col(User::getWork, Work.PYTHON)
                .where()
                .eq(User::getId, users.get(1).getId())
                .ex();
    }


    public void testUpdate() throws JsonProcessingException {
        Long id = 109L;
        List<User> exs = crudUserMapper.select()
                .colAll()
                .where()
                .isNotNull(User::getId)
                .exs();
        id = exs.get(0).getId();


        crudUserMapper.update()
                .npdateNull()
                .col(User::getName,"ling")
                .where()
                .eq(User::getId,id)
                .ex();

        crudUserMapper.update().npdateNull()
                .col(User::getBirthday, new Date())
                //name not will update . because its null
                .col(User::getName,null)
                .where()
                .eq(User::getId, id)
                .ex();
        User user = crudUserMapper.queryUserById(id);
        print(user);

        crudUserMapper.update()
                .updateNull()
                .col(User::getBirthday, new Date())
                .col(User::getId, id)
                .where()
                .eq(User::getId,id)
                .ex();

        user = crudUserMapper.select()
                .colAll()
                .where()
                .eq(User::getId, id)
                .ex();

        crudUserMapper.update()
                .npdateNull()
                .colAll(user)
                .where()
                .eq(User::getId,id)
                .ex();
        try {
            crudUserMapper.update()
                    .npdateNull()
                    .colAll(new User())
                    .where()
                    .eq(User::getId,id)
                    .ex();

        }catch (Exception e){
            print(e.getMessage());
        }

        crudUserMapper.update()
                .npdateNull()
                .col(User::getName,"zhou")
                .where()
                .eq(User::getId,1)
                .addBatch()
                .colAll(user)
                .where()
                .eq(User::getId,2)
                .ex();

        print(user);
    }

    public void testUpdateUser(){
        User user = User.builder().name("Jom").birthday(null).build();
        crudUserMapper.update().npdateNull()
                .colAll(user)
                .where()
                .eq(User::getId,1)
                .ex();

        crudUserMapper.update().updateNull()
                .colAll(user)
                .where()
                .eq(User::getId, 1)
                .ex();


    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }
}
