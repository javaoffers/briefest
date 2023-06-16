package com.javaoffers.brief.modelhelper.encrypt.batis;

import com.javaoffers.brief.modelhelper.encrypt.SqlAesProcessor;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author mingJie
 */

public class MybatisEncryptInterceptorConfig {

    @Resource
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Resource
    private List<SqlAesProcessor> sqlAesProcessors;

    @PostConstruct
    public void init(){
        for(SqlSessionFactory sessionFactory : sqlSessionFactoryList){
            sessionFactory.getConfiguration().addInterceptor(new MybatisEncryptInterceptor(sqlAesProcessors));
        }
    }
}
