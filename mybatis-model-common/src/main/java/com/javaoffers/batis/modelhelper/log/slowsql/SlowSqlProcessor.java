package com.javaoffers.batis.modelhelper.log.slowsql;

import com.javaoffers.batis.modelhelper.filter.Chain;
import com.javaoffers.batis.modelhelper.filter.ChainFilter;

import java.util.function.Supplier;

/**
 * @description: Slow SQL processor
 * @author: create by cmj on 2023/5/28 16:01
 */
public class SlowSqlProcessor implements ChainFilter<Object> {

    Supplier<SlowSqlInfo> function;

    @Override
    public Object filter(Chain<Object> objectChain) {
        return function.get();
    }

    public SlowSqlProcessor(Supplier<SlowSqlInfo> function) {
        this.function = function;
    }
}
