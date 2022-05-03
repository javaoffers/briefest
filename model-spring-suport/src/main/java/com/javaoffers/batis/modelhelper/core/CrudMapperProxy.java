package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.crud.impl.SelectFunStringImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereFunStringImpl;
import com.javaoffers.batis.modelhelper.mapper.CrudMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * @Description: 解析CrudMapper
 * @Auther: create by cmj on 2022/5/3 00:47
 */
public class CrudMapperProxy<T> implements InvocationHandler, Serializable {

    private MapperProxy<T> mapperProxy;

    private static HashMap<Method,String> isMapperMethod = new HashMap<>();

    public CrudMapperProxy( MapperProxy<T> mapperProxy) {
        this.mapperProxy = mapperProxy;
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

        if(StringUtils.isNotBlank(isMapperMethod.getOrDefault(method,""))){
            if(isMapperMethod.get(method).equalsIgnoreCase(CrudMapperConstant.SELECT.getMethodName())){ //定义常量替换
                Field mapperInterface = MapperProxy.class.getDeclaredField("mapperInterface");
                mapperInterface.setAccessible(true);
                Class mc = (Class)mapperInterface.get(mapperProxy);
                Type[] types = mc.getGenericInterfaces();
                ParameterizedTypeImpl parameterizedTypes = (ParameterizedTypeImpl)types[0];
                Type pclass = parameterizedTypes.getActualTypeArguments()[0];
                return new SelectFunStringImpl<T>((Class) pclass);
            }
            throw new NoSuchMethodException(method.getName());
        }else{
            return mapperProxy.invoke(proxy,method,args);
        }
    }
}
