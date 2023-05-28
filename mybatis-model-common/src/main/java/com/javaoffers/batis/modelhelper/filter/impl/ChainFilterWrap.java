package com.javaoffers.batis.modelhelper.filter.impl;

import com.javaoffers.batis.modelhelper.filter.Chain;
import com.javaoffers.batis.modelhelper.filter.ChainFilter;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 20:05
 */
public class ChainFilterWrap<R> {

    private ChainFilter<R> chainFilter;

    private Chain<R> nextChain;

    public ChainFilterWrap(ChainFilter<R> chainFilter, Chain<R> nextChain) {
        this.chainFilter = chainFilter;
        this.nextChain = nextChain;
    }

    public ChainFilterWrap(ChainFilter<R> chainFilter) {
        this.chainFilter = chainFilter;
    }


    public void setChainFilter(ChainFilter<R> chainFilter) {
        this.chainFilter = chainFilter;
    }

    public void setNextChain(Chain<R> nextChain) {
        this.nextChain = nextChain;
    }

    public ChainFilter<R> getChainFilter() {
        return chainFilter;
    }

    public Chain<R> getNextChain() {
        return nextChain;
    }
}
