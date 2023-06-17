package com.javaoffers.base.modelhelper.sample.spring.blur;

import com.javaoffers.base.modelhelper.sample.spring.constant.Work;
import com.javaoffers.base.modelhelper.sample.spring.mapper.BriefUserMapper;
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

    public static boolean status = true;

    @Resource
    BriefUserMapper crudUserMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringSuportCrudUserMapperBlur.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<User> exs = crudUserMapper.select().col(User::getMoneyBlur).where().isNotNull(User::getMoneyBlur).exs();
        LOGUtils.printLog(exs);

        exs = crudUserMapper.select().colAll().where().isNotNull(User::getMoneyBlur).exs();
        LOGUtils.printLog(exs);

        exs = crudUserMapper.select().colAll().where().isNotNull(User::getMoneyBlur).isNotNull(User::getBirthdayDateBlur).exs();
        LOGUtils.printLog(exs);

        exs = crudUserMapper.select().colAll().where().isNotNull(User::getMoneyBlur).isNotNull(User::getBirthdayDateBlur).limitPage(1,1).exs();
        User user = exs.get(0);

        user.setWork(Work.JAVA);
        user.setBirthday(null);
        user.setBirthdayDate(null);
        crudUserMapper.general().saveOrModify(user); //will not birthdayDateBlur inserted into the db
        LOGUtils.printLog(exs);

        user = crudUserMapper.select().col(User::getBirthday).col(User::getBirthdayDateBlur).where().isNotNull(User::getBirthday).ex();
        LOGUtils.printLog(user);

        exs = crudUserMapper.general().query(user);
        LOGUtils.printLog(exs);

        user.setBirthday(null);
        exs = crudUserMapper.general().query(user);
        LOGUtils.printLog(exs);

        if(status)
            System.exit(0);

    }
}
