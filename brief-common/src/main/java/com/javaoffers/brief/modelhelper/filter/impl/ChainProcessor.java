package com.javaoffers.brief.modelhelper.filter.impl;

/**
 * @description: 连执行器
 * @author: create by cmj on 2023/5/28 19:36
 */
public class ChainProcessor<R,S> {

    private ChainFilterWrap<R,S> filterWrap ;

    /**
     * 又业务控制.
     * @return
     */
    public R doChain() {
        return filterWrap.getChainFilter().filter(filterWrap.getNextChain());
    }

    public ChainProcessor(ChainFilterWrap<R,S> filterWrap) {
        this.filterWrap = filterWrap;
    }
}
