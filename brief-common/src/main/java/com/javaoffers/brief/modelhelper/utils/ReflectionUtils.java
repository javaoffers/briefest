package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.exception.NewInstanceException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterNamesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/9 11:13
 */
public class ReflectionUtils {

    static  Reflections reflections =  new Reflections(
            "com.javaoffers.brief.modelhelper", //指定被扫描的包名
            Arrays.asList(
                    new SubTypesScanner(false)//允许getAllTypes获取所有Object的子类, 不设置为false则 getAllTypes 会报错.默认为true.
                    ,new MethodParameterNamesScanner()//设置方法参数名称 扫描器,否则调用getConstructorParamNames 会报错
                    ,new MethodAnnotationsScanner() //设置方法注解 扫描器, 否则getConstructorsAnnotatedWith,getMethodsAnnotatedWith 会报错
                    ,new TypeAnnotationsScanner()//设置类注解 扫描器 ,否则 getTypesAnnotatedWith 会报错
            )
    );

    public static<T> Set<Class<? extends T>> getChilds(Class<T> c){
        Set<Class<? extends T>> subTypesOf = reflections.getSubTypesOf(c);
        return new HashSet<Class<? extends T>>(subTypesOf);
    }

    public static<T> Set<T> getChildInstance(Class<T> c){
        Set<Class<T>> subTypesOf = getChildClassWithOutAbstract(c);
        HashSet<T> instanceSet = new HashSet<>();
        for(Class clazz : subTypesOf){
            try {
                Constructor constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                T t = (T) constructor.newInstance();
                instanceSet.add(t);
            }catch (Exception e){
                e.printStackTrace();
                throw new NewInstanceException(e.getMessage());
            }

        }
        return instanceSet;
    }

    public static<T> Set<Class<T>> getChildClassWithOutAbstract(Class<T> c){
        Set<Class<? extends T>> subTypesOf = reflections.getSubTypesOf(c);
        HashSet<Class<T>> instanceSet = new HashSet<>();
        for(Class clazz : subTypesOf){
            try {
                if(Modifier.isAbstract(clazz.getModifiers())){
                    instanceSet.addAll(getChildClassWithOutAbstract(clazz));
                    continue;
                }
                instanceSet.add(clazz);
            }catch (Exception e){
                e.printStackTrace();
                throw new NewInstanceException(e.getMessage());
            }

        }
        return instanceSet;
    }

}
