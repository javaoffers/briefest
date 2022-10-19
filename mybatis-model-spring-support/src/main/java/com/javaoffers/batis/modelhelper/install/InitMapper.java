//package com.javaoffers.batis.modelhelper.install;
//
//import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
//import com.javaoffers.batis.modelhelper.utils.TableHelper;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
///**
// * @Description:
// * @Auther: create by cmj on 2022/5/3 17:27
// */
//public class InitMapper {
//    private static DataSource bean;
//    private static InitMapper initMapper;
//    public InitMapper(DataSource bean) {
//        synchronized (InitMapper.class){
//            if(!isInited()){
//                this.bean = bean;
//                JdbcTemplate jdbcTemplate = new JdbcTemplate( bean);
//                BaseBatisImpl.baseBatis.setJdbcTemplate(jdbcTemplate);
//                new TableHelper( bean);
//                initMapper = this;
//            }
//        }
//
//    }
//    public static boolean isInited(){
//        return bean != null;
//    }
//}
