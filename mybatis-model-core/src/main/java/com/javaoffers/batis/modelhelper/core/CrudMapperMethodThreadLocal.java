package com.javaoffers.batis.modelhelper.core;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * create by cmj
 */
public class CrudMapperMethodThreadLocal {

    private final static ThreadLocal<Class> excutorSelect = new InheritableThreadLocal<>();
    private final static ThreadLocal<JdbcTemplate> excutorJdbcTemplate = new InheritableThreadLocal<>();

    public static void addExcutorModel(Class clazz){
        excutorSelect.set(clazz);
    }

    public static void delExcutorModel(){
        excutorSelect.remove();
    }

    public static Class getExcutorModel(){
        return excutorSelect.get();
    }

    public static void addExcutorJdbcTemplate(JdbcTemplate jdbcTemplate){
        excutorJdbcTemplate.set(jdbcTemplate);
    }
    public static void delExcutorJdbcTemplate(){
        excutorJdbcTemplate.remove();
    }
    public static JdbcTemplate getExcutorJdbcTemplate(){
       return excutorJdbcTemplate.get();
    }

}
