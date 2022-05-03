package com.javaoffers.batis.modelhelper.fun.crud.impl;


import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.crud.OnFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:13
 */
public  class OnFunStringImpl<M1,M2,V>  implements OnFun<M1,M2,String,V>
{
    private LinkedList<Condition> conditions;

    private WhereFunStringImpl<M2,V> whereFunString;
    /**
     * K: table1 的字段名称
     * V: table2 的子度名称
     */
    private M1 m1;
    private M2 m2;
    private String table1Name;
    private String table2Name;

    public OnFunStringImpl(LinkedList<Condition> conditions) {
       this.conditions = conditions;
       this.whereFunString = new WhereFunStringImpl<>(conditions);
    }

    @Override
    public OnFun<M1, M2, String, V> oeq(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> oueq(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> ogt(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> olt(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> ogtEq(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> oltEq(String col, String col2) {
        return null;
    }

    @Override
    public WhereFun<M1, String, V, ?> end() {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> or() {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> eq(String col, V value) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> ueq(String col, V value) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> gt(String col, V value) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> lt(String col, V value) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> gtEq(String col, V value) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> ltEq(String col, V value) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> between(String col, V start, V end) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> like(String col, V value) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> in(String col, V... values) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> exists(String existsSql) {
        return null;
    }

    @Override
    public M2 ex() {
        return null;
    }

    @Override
    public List<M2> exs() {
        return null;
    }
}
