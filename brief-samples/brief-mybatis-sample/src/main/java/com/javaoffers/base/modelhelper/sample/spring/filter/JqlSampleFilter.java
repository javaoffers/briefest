package com.javaoffers.base.modelhelper.sample.spring.filter;

import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
import com.javaoffers.brief.modelhelper.filter.JqlChainFilter;
import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;

/**
 * @description: 测试filter
 * @author: create by cmj on 2023/6/1 21:01
 */
public class JqlSampleFilter implements JqlChainFilter {
    @Override
    public Object filter(JqlExecutorChain<Object, JqlMetaInfo> chain) {
        System.out.println("-------------------------- 0 0 ---------------------- ");
        return chain.doChain();

    }
}
