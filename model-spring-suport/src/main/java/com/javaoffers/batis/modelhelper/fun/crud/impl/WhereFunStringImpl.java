package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.*;
import com.javaoffers.batis.modelhelper.fun.condition.BetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ExistsCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereOnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:14
 */
public class WhereFunStringImpl<M,V> implements WhereFun<M, GetterFun,V,WhereFunStringImpl<M,V>> {


    /**静态**/
    private static JdbcTemplate jdbcTemplate;

    public WhereFunStringImpl(JdbcTemplate jdbcTemplate){
        synchronized (WhereFunStringImpl.class){
            if(jdbcTemplate==null){
                WhereFunStringImpl.jdbcTemplate = jdbcTemplate;
            }
        }
    }

    private LinkedList<Condition> conditions;

    public WhereFunStringImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public WhereFunStringImpl<M, V> or() {
        conditions.add(()-> ConditionTag.OR);
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> eq(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.EQ));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> ueq(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.UEQ));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> gt(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> lt(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> gtEq(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> ltEq(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> between(GetterFun col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> like(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LIKE));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> in(GetterFun col, V... values) {
        Stream.of(values).forEach(value -> {
            conditions.add(new WhereOnCondition(col,value,ConditionTag.IN));
        } );
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> exists(String existsSql) {
         conditions.add(new ExistsCondition<V>(existsSql));
         return this;
    }

    @Override
    public M ex() {
        return null;
    }

    @Override
    public List<M> exs() {
        return null;
    }
}
