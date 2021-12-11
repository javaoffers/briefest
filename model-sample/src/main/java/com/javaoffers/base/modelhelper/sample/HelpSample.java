package com.javaoffers.base.modelhelper.sample;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;


import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class HelpSample {

    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void test1() throws JsonProcessingException {

        InputStream is = HelpSample.class.getResourceAsStream("/sqlMapConfig.xml");
        Configuration configuration = new XMLConfigBuilder(is, null, null).parse();
        //以下的这种办法不行，因为在 上面的parse()方法执行过程中就已经触发resultType=model类型未找到，因此需要在配置文件中指定。
        // 本人没有指定。利用byteBuddy进行修改字节操作，如下案例
//        TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
//        typeAliasRegistry .registerAlias("model", HashMap.class);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sf.openSession();
        List<Object> objects = sqlSession.selectList("com.mh.others.mybatis.basesmapple.queryTestData");
        LOGUtils.printLog(objectMapper.writeValueAsString(objects));

    }

    @Test
    public void test() throws Exception {
        ConvertRegisterSelectorDelegate delegate = new ConvertRegisterSelectorDelegate();
        String s = delegate.converterObject(String.class, new Short((short) 1));
        LOGUtils.printLog(s);

        Integer integer = delegate.converterObject(Integer.class, "23");
        LOGUtils.printLog(integer);

        Date date = delegate.converterObject(Date.class, "2021-09-23 23:01:03");
        LOGUtils.printLog(date);
    }
}
