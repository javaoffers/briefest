package com.javaoffers.base.modelhelper.sample.spring.interceptor;

import com.javaoffers.batis.modelhelper.core.BaseSQLInfo;
import com.javaoffers.batis.modelhelper.interceptor.JqlInterceptor;

/**
 * @description: print sql
 * @author: create by cmj on 2023/5/27 19:00
 */
public class LogInterceptor implements JqlInterceptor {
    @Override
    public void handler(BaseSQLInfo baseSQLInfo) {
        System.out.println("LogInterceptor: SQL :  "+ baseSQLInfo.getSql());
        System.out.println("LogInterceptor: Param: " +baseSQLInfo.getParams());
    }
}
