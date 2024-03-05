package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;

/**
 * {@code SQLParse} 对jql信息拦截,在真正执行之前可修改数据.
 */
public interface JqlInterceptor extends Interceptor {

    void handler(BaseSQLInfo baseSQLInfo);
}
