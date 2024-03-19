package com.javaoffers.brief.modelhelper.filter.impl;

import com.javaoffers.brief.modelhelper.filter.Chain;
import com.javaoffers.brief.modelhelper.filter.ChainFilter;

/**
 * @description: 链包裹器
 * @author: create by cmj on 2023/5/28 20:05
 */
public class ChainFilterWrap<R,S> implements Chain<R,S> {


    private ChainFilter<R,S> chainFilter;

    private ChainFilterWrap<R,S> nextChain;

    public ChainFilterWrap(ChainFilter<R,S> chainFilter, ChainFilterWrap<R,S> nextChain) {
        this.chainFilter = chainFilter;
        this.nextChain = nextChain;
    }

    public ChainFilterWrap(ChainFilter<R,S> chainFilter) {
        this.chainFilter = chainFilter;
    }


    public void setChainFilter(ChainFilter<R,S> chainFilter) {
        this.chainFilter = chainFilter;
    }

    public void setNextChain(ChainFilterWrap<R,S> nextChain) {
        this.nextChain = nextChain;
    }

    public ChainFilter<R,S> getChainFilter() {
        return chainFilter;
    }

    public Chain<R,S> getNextChain() {
        return nextChain;
    }

    @Override
    public R doChain() {
        return chainFilter.filter(nextChain);
    }

}
