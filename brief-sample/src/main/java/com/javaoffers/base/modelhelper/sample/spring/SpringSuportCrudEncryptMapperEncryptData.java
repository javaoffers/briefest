package com.javaoffers.base.modelhelper.sample.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.spring.mapper.BriefEncryptDataMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.EncryptData;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.encrypt.anno.AesEncryptConfig;
import com.javaoffers.brief.modelhelper.encrypt.anno.EncryptTableColumns;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
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
    BriefEncryptDataMapper crudEncryptDataMapper;

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
        List<EncryptData> encryptDatas = this.crudEncryptDataMapper.general().query(1, 10000);
        if(encryptDatas != null){
            Set<Integer> ids = encryptDatas.stream().map(EncryptData::getId).collect(Collectors.toSet());
            crudEncryptDataMapper.general().removeByIds(ids);
        }
        EncryptData encryptData = new EncryptData();
        String encryptNum = "1234567890";
        encryptData.setEncryptNum(encryptNum);
        Id id = this.crudEncryptDataMapper.general().save(encryptData); //The value of encrypt_num in the table is C3F41B512C08D900DBBB74E9379279DD
        encryptDatas = this.crudEncryptDataMapper.general().queryByIds(id); //Query will be decrypted automatically
        print(encryptDatas);
        // Inscription query, the bottom will be converted into ciphertext and query.
        EncryptData ex = this.crudEncryptDataMapper.select().colAll().where().eq(EncryptData::getEncryptNum, encryptNum).ex();
        print(ex);
        //Support like queries
        EncryptData ex1 = this.crudEncryptDataMapper.select().colAll().where().like(EncryptData::getEncryptNum, encryptNum).ex();
        print(ex1);

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
