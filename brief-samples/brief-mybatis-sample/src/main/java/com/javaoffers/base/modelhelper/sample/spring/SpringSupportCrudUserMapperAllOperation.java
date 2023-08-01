package com.javaoffers.base.modelhelper.sample.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 14:14
 */
@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.mapper")
public class SpringSupportCrudUserMapperAllOperation {
    @Resource
    SpringSuportCrudUserMapperSelete selete;
    @Resource
    SpringSuportCrudUserMapperInsert insert;
    @Resource
    SpringSuportCrudUserMapperUpdate update;
    @Resource
    SpringSuportCrudUserMapperDelete delete;
    @Resource
    SpringSuportCrudUserMapperGeneral general;
    @Resource
    SpringSuportCrudUserMapperBlur blur;


    public static void main(String[] args) {
        SpringSuportCrudUserMapperSelete.status = false;
        SpringSuportCrudUserMapperInsert.status = false;
        SpringSuportCrudUserMapperUpdate.status = false;
        SpringSuportCrudUserMapperDelete.status = false;
        SpringSuportCrudUserMapperGeneral.status = false;
        SpringSuportCrudUserMapperBlur.status = false;
        SpringSuportCrudEncryptMapperEncryptData.status=false;
        SpringApplication.run(SpringSupportCrudUserMapperAllOperation.class, args);
        System.exit(1);

    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        selete.testSelect();
//        insert.testInsert();
//        update.testUpdate();
//        delete.testDelete();
//        general.testGeneral();
//    }
}
