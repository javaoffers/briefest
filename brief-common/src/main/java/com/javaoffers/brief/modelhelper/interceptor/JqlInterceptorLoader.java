package com.javaoffers.brief.modelhelper.interceptor;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 加载Jql拦截器,外部不可用. 主要加载brief内部自定义的.
 * 只需要实现JqlInterceptorLoader中的loadJqlInterceptor方法即可.
 */
public interface JqlInterceptorLoader extends BriefContextPostProcess {

    @Override
    default void postProcess(BriefContext briefContext) {
        List<JqlInterceptor> jqlInterceptors = briefContext.getJqlInterceptors();
        //加载指定jql拦接器
        JqlInterceptor jqlInterceptor = loadJqlInterceptor();
        if(jqlInterceptor != null){
            jqlInterceptors.add(jqlInterceptor);
        }
    }

    /**
     * 加载自定义Jql拦截器.
     */
    abstract JqlInterceptor loadJqlInterceptor();
}
