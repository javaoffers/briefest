package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;

import java.util.LinkedList;

/**
 * 在每一个condition添加到list时，可以进行拦截判断.
 *
 * @author cao ming jie create by 2025/4/30
 */
public interface ConditionInterceptor extends Interceptor{

     /**
      * condition 拦截器
      * @param conditions conditions
      * @param condition 当前准备add 的condition
      * @return true mean that the condition will be added 2 the conditions ,
      *         false will be not added 2 the conditions
      */
     boolean process(ConditionContext conditions, Condition condition);
}
