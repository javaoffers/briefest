package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudEncryptDataMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.EncryptData;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.encrypt.anno.AesEncryptConfig;
import com.javaoffers.batis.modelhelper.encrypt.anno.EncryptTableColumns;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
public class SpringSuportCrudEncryptMapperEncryptData implements InitializingBean {
    public static boolean status = true;
    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    CrudEncryptDataMapper crudEncryptDataMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudEncryptMapperEncryptData.class, args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testEncrypt();

        if(status){
            System.exit(0);
        }

    }

    private void testEncrypt() throws JsonProcessingException {
        List<EncryptData> query = this.crudEncryptDataMapper.general().query(1, 10000);
        if(query != null){
            Set<Integer> ids = query.stream().map(EncryptData::getId).collect(Collectors.toSet());
            crudEncryptDataMapper.general().removeByIds(ids);
        }
        EncryptData encryptData = new EncryptData();
        encryptData.setEncryptNum("1234567890");
        Id id = this.crudEncryptDataMapper.general().save(encryptData); //The value of encrypt_num in the table is C3F41B512C08D900DBBB74E9379279DD
        List<EncryptData> encryptData1 = this.crudEncryptDataMapper.general().queryByIds(id); //Query will be decrypted automatically
        print(encryptData1);
    }

    public void print(Object user) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(user));
    }

    /**
     * Configure the tables and fields that need to be decrypted.
     */
    @AesEncryptConfig(key = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAF", encryptTableColumns = {
            @EncryptTableColumns(tableName = "encrypt_data", columns = {"encrypt_num"})
    })
    @Configuration
    static class EncryptConfig{

    }
}
