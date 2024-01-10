package com.javaoffers.base.modelhelper.sample.spring.interceptor;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import org.springframework.stereotype.Component;

/**
 * 支持spring环境.
 * @description: print sql
 * @author: create by cmj on 2023/5/27 19:00
 */
//@Component
public class LogInterceptor implements JqlInterceptor {
    @Override
    public void handler(BaseSQLInfo baseSQLInfo) {
        System.out.println("LogInterceptor: SQL :  "+ baseSQLInfo.getSql());
        System.out.println("LogInterceptor: Param: " +baseSQLInfo.getParams());
    }
}
