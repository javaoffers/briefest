package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.fun.Condition;

/**
 * 在每一个condition添加到list时，可以进行拦截判断.
 *
 * @author cao ming jie create by 2025/4/30
 */
public interface ConditionInterceptor extends Interceptor{
    void process(Condition condition);
}
