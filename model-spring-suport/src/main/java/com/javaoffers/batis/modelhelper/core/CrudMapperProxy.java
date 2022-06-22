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

    private static HashMap<Method,String> isMapperMethod = new HashMap<>();

    private Class clazz;

    private Object defaultObj;

    private Type modelClass;

    public CrudMapperProxy( MapperProxy<T> mapperProxy,Class clazz) {
        this.mapperProxy = mapperProxy;
        this.clazz = clazz;
        Type[] types = clazz.getGenericInterfaces();
        ParameterizedTypeImpl parameterizedTypes = (ParameterizedTypeImpl)types[0];
        Type modelclass = parameterizedTypes.getActualTypeArguments()[0];
        this.modelClass = modelclass;
        ByteBuddyUtils.DefaultClass select = ByteBuddyUtils.buildDefaultClass(
                "select",CrudMapperMethodExcutor.class);
        defaultObj = ByteBuddyUtils
                .makeObject(clazz,
                        Arrays.asList(select));
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
            CrudMapperMethodExcutor.addExcutorSelect((Class) this.modelClass);
            if(method.getModifiers() == 1){
                return method.invoke(defaultObj,args);
            } else if(StringUtils.isNotBlank(isMapperMethod.get(method))){
                if(method.getName().equals(CrudMapperConstant.SELECT.getMethodName())){
                    CrudMapper crudMapper = (CrudMapper) defaultObj;
                    return  crudMapper.select();
                }
                throw new IllegalAccessException("method not found ");
            }else{
                //执行batis的mapperProxy 原生
                return mapperProxy.invoke(proxy,method,args);
            }
        }finally {
            CrudMapperMethodExcutor.delExcutorSelect((Class) this.modelClass);
        }

    }
}
