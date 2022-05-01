package com.javaoffers.batis.modelhelper.fun.impl;

import com.javaoffers.batis.modelhelper.fun.WhereFun;
import java.util.LinkedList;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:14
 */
public class WhereFunStringImpl<M,V,R extends WhereFun<M,String,V,R>> implements WhereFun<M,String,V,R> {

    @Override
    public R or() {
        return (R) this;
    }

    @Override
    public R eq(String col, V value) {
        return (R) this;
    }

    @Override
    public R ueq(String col, V value) {
        return (R) this;
    }

    @Override
    public R gt(String col, V value) {
        return (R) this;
    }

    @Override
    public R lt(String col, V value) {
        return (R) this;
    }

    @Override
    public R gtEq(String col, V value) {
        return (R) this;
    }

    @Override
    public R ltEq(String col, V value) {
        return (R) this;
    }

    @Override
    public R between(String col, V start, V end) {
        return (R) this;
    }

    @Override
    public R like(String col, V value) {
        return (R) this;
    }

    @Override
    public R in(String col, V... values) {
        return (R) this;
    }
}
