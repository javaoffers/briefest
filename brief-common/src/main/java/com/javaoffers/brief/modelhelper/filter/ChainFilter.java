package com.javaoffers.brief.modelhelper.filter;

/**
 * @description: 拦截器链.
 * {@code ChainProcessor} 作为一个基础架构触发使用由具体的子业务使用.
 * @author: create by cmj on 2023/5/28 19:28
 */
public interface ChainFilter<R,S> extends Filter<R,Chain<R, S>>  {
}
