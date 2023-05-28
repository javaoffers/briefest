package com.javaoffers.batis.modelhelper.filter.impl;

import com.javaoffers.batis.modelhelper.filter.Chain;
import com.javaoffers.batis.modelhelper.filter.ChainFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 连执行器
 * @author: create by cmj on 2023/5/28 19:36
 */
public class ChainProcessor<R> implements Chain<R> {

    private ChainFilterWrap<R> filterWrap ;

    /**
     * 又业务控制.
     * @return
     */
    @Override
    public R doChain() {
        return filterWrap.getChainFilter().filter(filterWrap.getNextChain());
    }

    public ChainProcessor(ChainFilterWrap<R> filterWrap) {
        this.filterWrap = filterWrap;
    }
}
