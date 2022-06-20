package com.javaoffers.batis.modelhelper.fun.crud.impl.modify;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.LFCondition;
import com.javaoffers.batis.modelhelper.fun.condition.RFCondition;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereSelectFunImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Description: 更新条件
 * @Auther: create by cmj on 2022/5/4 20:42
 */
public class WhereModifyFunImpl<M,V>  implements WhereModifyFun<M,V>  {

    private LinkedList<Condition> conditions;
    
    private WhereSelectFunImpl whereFun;

    /**静态**/
    private static JdbcTemplate jdbcTemplate;

    public WhereModifyFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.whereFun = new WhereSelectFunImpl(this.conditions);
    }

    /**
     * 初始化使用 init
     * @param jdbcTemplate
     */
    public WhereModifyFunImpl (JdbcTemplate jdbcTemplate){
        if(jdbcTemplate != null){
            WhereModifyFunImpl.jdbcTemplate = jdbcTemplate;
        }
    }

    @Override
    public WhereModifyFun<M, V> or() {
        whereFun.or();
        return this;
    }

    @Override
    public WhereModifyFun<M, V> cond(Consumer<WhereModifyFun<M, V>> r) {
        conditions.add(new LFCondition( ConditionTag.LK));
        r.accept(this);
        conditions.add(new RFCondition( ConditionTag.RK));
        return this;
    }

    @Override
    public WhereModifyFun<M, V> cond(boolean condition, Consumer<WhereModifyFun<M, V>> r) {
        if(condition){
            cond(r);
        }
        return this;
    }

    @Override
    public WhereModifyFun<M, V> eq(GetterFun<M, V> col, V value) {
        whereFun.eq(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> eq(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.eq(condition, col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> ueq(GetterFun<M, V> col, V value) {
        whereFun.ueq(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> ueq(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.ueq(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> gt(GetterFun<M, V> col, V value) {
        whereFun.gt(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> gt(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.gt(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> lt(GetterFun<M, V> col, V value) {
        whereFun.lt(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> lt(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.lt(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> gtEq(GetterFun<M, V> col, V value) {
        whereFun.gtEq(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> gtEq(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.gtEq(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> ltEq(GetterFun<M, V> col, V value) {
        whereFun.ltEq(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> ltEq(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.ltEq(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> between(GetterFun<M, V> col, V start, V end) {
        whereFun.between(col,start,end);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> between(boolean condition, GetterFun<M, V> col, V start, V end) {
        whereFun.between(condition,col,start,end);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> notBetween(GetterFun<M, V> col, V start, V end) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> notBetween(boolean condition, GetterFun<M, V> col, V start, V end) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> like(GetterFun<M, V> col, V value) {
        whereFun.like(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> like(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.like(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> likeLeft(GetterFun<M, V> col, V value) {
        whereFun.likeLeft(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> likeLeft(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.likeLeft(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> likeRight(GetterFun<M, V> col, V value) {
        whereFun.likeRight(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> likeRight(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.likeRight(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> in(GetterFun<M, V> col, V... values) {
        whereFun.in(col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> in(boolean condition, GetterFun<M, V> col, V... values) {
        whereFun.in(condition,col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> in(GetterFun<M, V> col, Collection... values) {
        whereFun.in(col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> in(boolean condition, GetterFun<M, V> col, Collection... values) {
        whereFun.in(condition,col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> notIn(GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> notIn(boolean condition, GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> notIn(GetterFun<M, V> col, Collection... values) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> notIn(boolean condition, GetterFun<M, V> col, Collection... values) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> exists(String existsSql) {
        whereFun.exists(existsSql);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> exists(boolean condition, String existsSql) {
        whereFun.exists(condition,existsSql);
        return this;
    }

    @Override
    public Integer ex() {
        return 0;
    }
}
