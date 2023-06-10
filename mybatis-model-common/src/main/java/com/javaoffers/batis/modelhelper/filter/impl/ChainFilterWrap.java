package com.javaoffers.batis.modelhelper.filter.impl;

import com.javaoffers.batis.modelhelper.filter.Chain;
import com.javaoffers.batis.modelhelper.filter.ChainFilter;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 20:05
 */
public class ChainFilterWrap<R,S> implements Chain<R,S>{

    private S  s;

    private ChainFilter<R,S> chainFilter;

    private ChainFilterWrap<R,S> nextChain;

    public ChainFilterWrap(ChainFilter<R,S> chainFilter, ChainFilterWrap<R,S> nextChain) {
        this.chainFilter = chainFilter;
        this.nextChain = nextChain;
    }

    public ChainFilterWrap(S s, ChainFilter<R, S> chainFilter) {
        this.s = s;
        this.chainFilter = chainFilter;
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

    @Override
    public S getMetaInfo() {
        return s;
    }
}
