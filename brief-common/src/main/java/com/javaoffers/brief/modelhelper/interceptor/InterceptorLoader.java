package com.javaoffers.brief.modelhelper.interceptor;

import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 *
 */
public class InterceptorLoader {

    private static final  ArrayList<JqlInterceptor> coreInterceptorsList = Lists.newArrayList();

    public static final ArrayList<JqlInterceptor> customInterceptorsList = Lists.newArrayList();

    static {
        ServiceLoader<JqlInterceptor> load = ServiceLoader.load(JqlInterceptor.class);
        if(load != null && coreInterceptorsList.size() == 0){
            for(JqlInterceptor jqlInterceptor : load){
                coreInterceptorsList.add(jqlInterceptor);
            }
        }
    }

    public static synchronized List<JqlInterceptor> loadJqlInterceptor(){
        ArrayList<JqlInterceptor> jqlInterceptors = new ArrayList<>();
        jqlInterceptors.addAll(coreInterceptorsList);
        jqlInterceptors.addAll(customInterceptorsList);
        return jqlInterceptors;
    }

    public static synchronized void init(List<JqlInterceptor> jqlInterceptorList){
        //Initialization allows only once
        if(jqlInterceptorList != null && customInterceptorsList.size() == 0 ){
            customInterceptorsList.addAll(jqlInterceptorList);
        }
    }
}
