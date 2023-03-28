package com.javaoffers.batis.modelhelper.core;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by cmj
 */
public class CrudMapperMethodThreadLocal {
    /**
     * Be careful not to use ThreadLocal.
     */
    private final static Map<Thread, Class> excutorSelect = new ConcurrentHashMap<Thread, Class>();
    private final static Map<Thread, JdbcTemplate> excutorJdbcTemplate = new ConcurrentHashMap<Thread, JdbcTemplate>();

    public static void addExcutorModel(Class clazz){
        excutorSelect.put(Thread.currentThread(), clazz);
    }

    public static void delExcutorModel(){
        excutorSelect.remove(Thread.currentThread());
    }

    public static Class getExcutorModel(){
        return excutorSelect.get(Thread.currentThread());
    }

    public static void addExcutorJdbcTemplate(JdbcTemplate jdbcTemplate){
        excutorJdbcTemplate.put(Thread.currentThread(), jdbcTemplate);
    }

    public static void delExcutorJdbcTemplate(){
        excutorJdbcTemplate.remove(Thread.currentThread());
    }

    public static JdbcTemplate getExcutorJdbcTemplate(){
       return excutorJdbcTemplate.get(Thread.currentThread());
    }

}
