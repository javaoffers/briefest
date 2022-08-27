package com.javaoffers.batis.modelhelper.util;

import com.javaoffers.batis.modelhelper.convert.Convert;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterNamesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/9 11:13
 */
public class ReflectionUtils {

    static  Reflections reflections =  new Reflections(
            "com.javaoffers.batis.modelhelper", //指定被扫描的包名
            Arrays.asList(
                    new SubTypesScanner(false)//允许getAllTypes获取所有Object的子类, 不设置为false则 getAllTypes 会报错.默认为true.
                    ,new MethodParameterNamesScanner()//设置方法参数名称 扫描器,否则调用getConstructorParamNames 会报错
                    ,new MethodAnnotationsScanner() //设置方法注解 扫描器, 否则getConstructorsAnnotatedWith,getMethodsAnnotatedWith 会报错
                    ,new TypeAnnotationsScanner()//设置类注解 扫描器 ,否则 getTypesAnnotatedWith 会报错
            )
    );

    public static Set<Class<? extends Convert>> getChildOfConvert(){
        Set<Class<? extends Convert>> subTypesOf = reflections.getSubTypesOf(Convert.class);
        return new HashSet<>(subTypesOf);
    }

    public static Set<Class<?>> getChilds(Class c){
        Set<Class<?>> subTypesOf = reflections.getSubTypesOf(c);
        return new HashSet<Class<?>>(subTypesOf);
    }


}
