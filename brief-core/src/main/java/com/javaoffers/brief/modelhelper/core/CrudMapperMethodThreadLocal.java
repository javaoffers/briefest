package com.javaoffers.brief.modelhelper.core;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by cmj
 */
public class CrudMapperMethodThreadLocal {

    private final static ThreadLocal<Class> excutorSelect = new InheritableThreadLocal<Class>();
    private final static ThreadLocal< DataSource> excutorDataSource = new InheritableThreadLocal< DataSource>();

    public static void addExcutorModel(Class clazz){
        excutorSelect.set(clazz);
    }

    public static void delExcutorModel(){
        excutorSelect.remove();
    }

    public static Class getExcutorModel(){
        return excutorSelect.get();
    }

    public static void addExcutorDataSource(DataSource dataSource){
        excutorDataSource.set(dataSource);
    }

    public static void delExcutorDataSource(){
        excutorDataSource.remove();
    }

    public static DataSource getExcutorDataSource(){
       return excutorDataSource.get();
    }

}
