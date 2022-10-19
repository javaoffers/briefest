package com.javaoffers.batis.modelhelper.proxy;

import com.javaoffers.batis.modelhelper.core.CrudMapperConstant;
import com.javaoffers.batis.modelhelper.core.CrudMapperMethodExcutor;
import com.javaoffers.batis.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.batis.modelhelper.exception.ParseDataSourceException;
import com.javaoffers.batis.modelhelper.exception.ParseTableInfoException;
import com.javaoffers.batis.modelhelper.fun.crud.impl.SelectFunImpl;
import com.javaoffers.batis.modelhelper.mapper.CrudMapper;
import com.javaoffers.batis.modelhelper.utils.ByteBuddyUtils;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.*;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * @Description: 解析CrudMapper
 * @Auther: create by cmj on 2022/5/3 00:47
 */
public class CrudMapperProxy<T> implements InvocationHandler, Serializable {

    private MapperProxy<T> mapperProxy;

    private static final Class crudClass = CrudMapper.class;

    private static HashMap<Method,String> isMapperMethod = new HashMap<>();

    private Class clazz;

    private Object defaultObj;

    private Type modelClass;

    private JdbcTemplate jdbcTemplate;

    public CrudMapperProxy( MapperProxy<T> mapperProxy,Class clazz) {
        try {

            Field sqlSessionField = mapperProxy.getClass().getDeclaredField("sqlSession");
            sqlSessionField.setAccessible(true);
            SqlSession sqlSession = (SqlSession)sqlSessionField.get(mapperProxy);
            Field sqlSessionFactoryField = sqlSession.getClass().getDeclaredField("sqlSessionFactory");
            sqlSessionFactoryField.setAccessible(true);
            SqlSessionFactory sessionFactory = (SqlSessionFactory)sqlSessionFactoryField.get(sqlSession);
            DataSource dataSource = sessionFactory.getConfiguration().getEnvironment().getDataSource();
            this.jdbcTemplate = new JdbcTemplate(dataSource);
            this.mapperProxy = mapperProxy;
            this.clazz = clazz;
            if(!crudClass.isAssignableFrom(clazz)){
                return;
            }
            Type[] types = clazz.getGenericInterfaces();
            ParameterizedTypeImpl parameterizedTypes = (ParameterizedTypeImpl)types[0];
            Type modelclass = parameterizedTypes.getActualTypeArguments()[0];
            this.modelClass = modelclass;
            ByteBuddyUtils.DefaultClass select = ByteBuddyUtils.buildDefaultClass(
                    "select", CrudMapperMethodExcutor.class);
            ByteBuddyUtils.DefaultClass insert = ByteBuddyUtils.buildDefaultClass(
                    "insert",CrudMapperMethodExcutor.class);
            ByteBuddyUtils.DefaultClass update = ByteBuddyUtils.buildDefaultClass(
                    "update",CrudMapperMethodExcutor.class);
            ByteBuddyUtils.DefaultClass delete = ByteBuddyUtils.buildDefaultClass(
                    "delete",CrudMapperMethodExcutor.class);
            ByteBuddyUtils.DefaultClass general = ByteBuddyUtils.buildDefaultClass(
                    "general",CrudMapperMethodExcutor.class);

            defaultObj = ByteBuddyUtils
                    .makeObject(clazz,
                            Arrays.asList(select,insert,update,delete,general));

            Connection connection = jdbcTemplate.getDataSource().getConnection();
            try {
                TableHelper.parseTableInfo((Class) modelclass, connection);
            }catch (Exception e){
                throw new ParseTableInfoException("parse table info exception", e);
            }finally {
                if(!connection.isClosed()){
                    connection.close();
                }
            }

        }catch (Exception e){
            throw new ParseDataSourceException("parse datasource exception", e);
        }

    }

    static {
        Stream.of(
                CrudMapper.class.getDeclaredMethods()
        ).flatMap(Stream::of).forEach(method -> {
            method.setAccessible(true);
            isMapperMethod.put(method,method.getName());
        });
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            CrudMapperMethodThreadLocal.addExcutorModel((Class) this.modelClass);
            CrudMapperMethodThreadLocal.addExcutorJdbcTemplate(this.jdbcTemplate);
            //If defaultObj is null, it means CrudMapper interface is not inherited
            if(defaultObj != null){
                if(method.getModifiers() == 1){
                    return method.invoke(defaultObj,args);
                } else if(StringUtils.isNotBlank(isMapperMethod.get(method))){
                    if(method.getName().equals(CrudMapperConstant.SELECT.getMethodName())){
                        CrudMapper crudMapper = (CrudMapper) defaultObj;
                        return  crudMapper.select();
                    }else if(method.getName().equals(CrudMapperConstant.INSERT.getMethodName())){
                        CrudMapper crudMapper = (CrudMapper) defaultObj;
                        return crudMapper.insert();
                    }else if(method.getName().equals(CrudMapperConstant.UPDATE.getMethodName())){
                        CrudMapper crudMapper = (CrudMapper) defaultObj;
                        return crudMapper.update();
                    }else if(method.getName().equals(CrudMapperConstant.DELETE.getMethodName())){
                        CrudMapper crudMapper = (CrudMapper) defaultObj;
                        return crudMapper.delete();
                    }else if(method.getName().equals(CrudMapperConstant.GENERAL.getMethodName())){
                        CrudMapper crudMapper = (CrudMapper) defaultObj;
                        return crudMapper.general();
                    }
                    throw new IllegalAccessException("method not found ");
                }else{
                    //Execute mapperProxy native of batis
                    return mapperProxy.invoke(proxy,method,args);
                }
            }else{
                //Execute mapperProxy native of batis
                return mapperProxy.invoke(proxy,method,args);
            }
        }finally {
            CrudMapperMethodThreadLocal.delExcutorModel();
            CrudMapperMethodThreadLocal.delExcutorJdbcTemplate();
        }

    }
}
