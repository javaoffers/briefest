package com.javaoffers.base.modelhelper.sample.spring.blur;

import com.javaoffers.base.modelhelper.sample.spring.SpringSuportCrudUserMapperUpdate;
import com.javaoffers.base.modelhelper.sample.spring.mapper.CrudUserMapper;
import com.javaoffers.base.modelhelper.sample.spring.model.User;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mingJie
 */
@SpringBootApplication
@RequestMapping
@MapperScan("com.javaoffers.base.modelhelper.sample.spring.mapper")
public class SpringSuportCrudUserMapperBlur  implements InitializingBean {

    @Resource
    CrudUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperBlur.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<User> exs = crudUserMapper.select().col(User::getMoneyBlur).where().isNotNull(User::getMoneyBlur).exs();
        LOGUtils.printLog(exs);
        System.exit(0);
    }
}
