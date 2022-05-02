package com.javaoffers.batis.modelhelper.fun.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.JoinFun;
import com.javaoffers.batis.modelhelper.fun.WhereFun;
import com.javaoffers.batis.modelhelper.fun.condition.BetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ExistsCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereOnCondition;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:14
 */
public class WhereFunStringImpl<M,V> implements WhereFun<M,String,V,WhereFunStringImpl<M,V>> {


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
    public WhereFunStringImpl<M, V> eq(String col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.EQ));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> ueq(String col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.UEQ));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> gt(String col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> lt(String col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> gtEq(String col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> ltEq(String col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> between(String col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> like(String col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LIKE));
        return this;
    }

    @Override
    public WhereFunStringImpl<M, V> in(String col, V... values) {
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
