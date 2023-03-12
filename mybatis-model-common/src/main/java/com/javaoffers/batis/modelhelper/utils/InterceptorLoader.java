package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.interceptor.JqlInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 *
 */
public class InterceptorLoader {

    private static final  ArrayList<JqlInterceptor> jqlInterceptorsList = Lists.newArrayList();

    public static synchronized List<JqlInterceptor> loadJqlInterceptor(){
        ServiceLoader<JqlInterceptor> load = ServiceLoader.load(JqlInterceptor.class);
        if(load != null && jqlInterceptorsList.size() == 0){
            for(JqlInterceptor jqlInterceptor : load){
                jqlInterceptorsList.add(jqlInterceptor);
            }
        }
        return jqlInterceptorsList;
    }
}
