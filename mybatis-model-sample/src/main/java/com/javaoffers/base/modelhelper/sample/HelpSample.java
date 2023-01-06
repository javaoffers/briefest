package com.javaoffers.base.modelhelper.sample;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.aggent.InstallModelHelper;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.core.Id;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;


import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class HelpSample {

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before(){
        InstallModelHelper.install();
    }

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
        ConvertRegisterSelectorDelegate delegate =  ConvertRegisterSelectorDelegate.convert;
        String s = delegate.converterObject(String.class, new Short((short) 1));
        LOGUtils.printLog(s);

        Integer integer = delegate.converterObject(Integer.class, "23");
        LOGUtils.printLog(integer);

        Date date = delegate.converterObject(Date.class, "2021-09-23 23:01:03");
        LOGUtils.printLog(date);

        LocalDate localDate1 = delegate.converterObject(LocalDate.class, "2021-09-23 23:01:03");
        String s1 = localDate1.toString();

        LocalDateTime localDateTime = delegate.converterObject(LocalDateTime.class, "2021-09-23 23:01:03");
        String s2 = localDateTime.toString();

        OffsetDateTime offsetDateTime = delegate.converterObject(OffsetDateTime.class, "2021-09-23 23:01:03");
        String s3 = offsetDateTime.toString();
        LOGUtils.printLog(s3);

        date = delegate.converterObject(Date.class, new Date().getTime());
        LOGUtils.printLog(date);

        LocalDate localDate = delegate.converterObject(LocalDate.class, new Date().getTime());
        LOGUtils.printLog(localDate.getYear());

        Integer integer1 = delegate.converterObject(Integer.class, 10l);
        LOGUtils.printLog(integer1);

        byte b = delegate.converterObject(Byte.class, 10l);
        LOGUtils.printLog(b);

        Integer integer2 = delegate.converterObject(Integer.class, new BigDecimal(11));
        LOGUtils.printLog(integer2);

        long l = delegate.converterObject(long.class, new BigDecimal(11));
        LOGUtils.printLog(integer2);

        Time time = delegate.converterObject(Time.class, new Date());
        LOGUtils.printLog(time.toString());

        Timestamp timestamp = delegate.converterObject(Timestamp.class, new Date());
        LOGUtils.printLog(timestamp.toString());

        Date date1 = delegate.converterObject(Date.class, new Timestamp(new Date().getTime()));
        LOGUtils.printLog(date1.toString());

        Timestamp timestamp1 = delegate.converterObject(Timestamp.class, new Date().getTime());
        LOGUtils.printLog(timestamp1.toString());

        byte[] bs = "hello".getBytes();
        String s4 = delegate.converterObject(String.class, bs);
        LOGUtils.printLog(s4);

        Id id = delegate.converterObject(Id.class, 54);
        LOGUtils.printLog(id);
        LOGUtils.printLog(id.toInt());

        id = delegate.converterObject(Id.class, "hellouuxxxx001");
        LOGUtils.printLog(id);
    }
}
