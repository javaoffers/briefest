package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.*;
import com.javaoffers.batis.modelhelper.fun.condition.BetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ExistsCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OrCondition;
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
public class WhereFunImpl<M,V> implements WhereFun<M, GetterFun,V, WhereFunImpl<M,V>> , ExecutFun<M> {


    /**静态**/
    private static JdbcTemplate jdbcTemplate;

    public WhereFunImpl(JdbcTemplate jdbcTemplate){
        synchronized (WhereFunImpl.class){
            if(jdbcTemplate==null){
                WhereFunImpl.jdbcTemplate = jdbcTemplate;
            }
        }
    }

    private LinkedList<Condition> conditions;

    public WhereFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public WhereFunImpl<M, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public WhereFunImpl<M, V> eq(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.EQ));
        return this;
    }

    @Override
    public WhereFunImpl<M, V> ueq(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.UEQ));
        return this;
    }

    @Override
    public WhereFunImpl<M, V> gt(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT));
        return this;
    }

    @Override
    public WhereFunImpl<M, V> lt(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT));
        return this;
    }

    @Override
    public WhereFunImpl<M, V> gtEq(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public WhereFunImpl<M, V> ltEq(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public WhereFunImpl<M, V> between(GetterFun col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public WhereFunImpl<M, V> like(GetterFun col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LIKE));
        return this;
    }

    @Override
    public WhereFunImpl<M, V> in(GetterFun col, V... values) {
        Stream.of(values).forEach(value -> {
            conditions.add(new WhereOnCondition(col,value,ConditionTag.IN));
        } );
        return this;
    }

    @Override
    public WhereFunImpl<M, V> exists(String existsSql) {
         conditions.add(new ExistsCondition<V>(existsSql));
         return this;
    }

    @Override
    public M ex() {
        conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //解析SQL 并执行 select。
        //TODO
        return null;
    }

    @Override
    public List<M> exs() {
        conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //解析SQL select 并执行。
        //TODO
        return null;
    }

}
