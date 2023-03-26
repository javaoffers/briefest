package com.javaoffers.batis.modelhelper.interceptor;

import com.javaoffers.batis.modelhelper.core.BaseSQLInfo;

public interface JqlInterceptor extends Interceptor {

    void handler(BaseSQLInfo baseSQLInfo);
}
