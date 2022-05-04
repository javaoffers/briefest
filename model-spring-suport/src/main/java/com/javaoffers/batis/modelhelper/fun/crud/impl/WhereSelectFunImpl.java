package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.BetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ExistsCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereOnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:14
 */
public class WhereSelectFunImpl<M,V> implements WhereSelectFun<M,V> {


    /**静态**/
    private static JdbcTemplate jdbcTemplate;

    public WhereSelectFunImpl(JdbcTemplate jdbcTemplate){
        synchronized (WhereSelectFunImpl.class){
            if(jdbcTemplate == null){
                WhereSelectFunImpl.jdbcTemplate = jdbcTemplate;
            }
        }
    }

    private LinkedList<Condition> conditions;

    public WhereSelectFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public WhereSelectFunImpl<M, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public WhereSelectFun<M, V> eq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ueq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.UEQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gt(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> lt(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gtEq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ltEq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> between(GetterFun<M, V> col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> like(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col,value,ConditionTag.LIKE));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(GetterFun<M, V> col, V... values) {
        Stream.of(values).forEach(value -> {
            conditions.add(new WhereOnCondition(col,value,ConditionTag.IN));
        } );
        return this;
    }

    @Override
    public WhereSelectFunImpl<M, V> exists(String existsSql) {
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
