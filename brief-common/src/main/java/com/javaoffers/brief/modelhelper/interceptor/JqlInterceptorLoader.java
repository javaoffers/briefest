package com.javaoffers.brief.modelhelper.interceptor;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 加载Jql拦截器,外部不可用.
 */
public interface JqlInterceptorLoader extends BriefContextPostProcess {

    @Override
    default void postProcess(BriefContext briefContext) {
        //加载Jql拦截器通过ServiceLoader
        ServiceLoader<JqlInterceptor> load = ServiceLoader.load(JqlInterceptor.class);
        List<JqlInterceptor> jqlInterceptors = briefContext.getJqlInterceptors();
        for(JqlInterceptor jqlInterceptor : load){
            jqlInterceptors.add(jqlInterceptor);
        }

        //加载jql拦接器
        JqlInterceptor jqlInterceptor = loadJqlInterceptor();
        if(jqlInterceptor != null){
            jqlInterceptors.add(jqlInterceptor);
        }
        //加载外部jql拦截器,通过配置加载

    }

    /**
     * 加载自定义Jql拦截器.
     */
    abstract JqlInterceptor loadJqlInterceptor();
}
