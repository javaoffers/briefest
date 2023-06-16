package com.javaoffers.brief.modelhelper.interceptor;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;

public interface JqlInterceptor extends Interceptor {

    void handler(BaseSQLInfo baseSQLInfo);
}
