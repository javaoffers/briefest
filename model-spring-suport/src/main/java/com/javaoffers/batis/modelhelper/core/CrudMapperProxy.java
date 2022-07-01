package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.crud.impl.SelectFunImpl;
import com.javaoffers.batis.modelhelper.mapper.CrudMapper;
import com.javaoffers.batis.modelhelper.utils.ByteBuddyUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.binding.MapperProxy;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.Serializable;
import java.lang.reflect.*;
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

    public CrudMapperProxy( MapperProxy<T> mapperProxy,Class clazz) {
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
                "select",CrudMapperMethodExcutor.class);
        ByteBuddyUtils.DefaultClass insert = ByteBuddyUtils.buildDefaultClass(
                "insert",CrudMapperMethodExcutor.class);
        ByteBuddyUtils.DefaultClass update = ByteBuddyUtils.buildDefaultClass(
                "update",CrudMapperMethodExcutor.class);
        defaultObj = ByteBuddyUtils
                .makeObject(clazz,
                        Arrays.asList(select,insert,update));
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
            //如果defaultObj 为 null 说明 没有继承CrudMapper接口
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
                    }
                    throw new IllegalAccessException("method not found ");
                }else{
                    //执行batis的mapperProxy 原生
                    return mapperProxy.invoke(proxy,method,args);
                }
            }else{
                //执行batis的mapperProxy 原生
                return mapperProxy.invoke(proxy,method,args);
            }
        }finally {
            CrudMapperMethodThreadLocal.delExcutorModel();
        }

    }
}
