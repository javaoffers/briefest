package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GGetterFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.BetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ExistsCondition;
import com.javaoffers.batis.modelhelper.fun.condition.GroupByCondition;
import com.javaoffers.batis.modelhelper.fun.condition.InCondition;
import com.javaoffers.batis.modelhelper.fun.condition.LimitCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.WhereOnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.HavingPendingFun;
import com.javaoffers.batis.modelhelper.fun.crud.LeftHavingPendingFun;
import com.javaoffers.batis.modelhelper.fun.crud.LeftWhereSelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.LimitFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:14
 * @param <M> 主表
 * @param <M2> 子表： 用于支持子表字段 group by , order by
 *
 */
public class LeftWhereSelectFunImpl<M, M2, V> implements LeftWhereSelectFun<M, M2,GetterFun<M,V>, GGetterFun<M2,V>,V> {

    /**
     * 静态
     **/
    private static JdbcTemplate jdbcTemplate;

    public LeftWhereSelectFunImpl(JdbcTemplate jdbcTemplate) {
        synchronized (LeftWhereSelectFunImpl.class) {
            if (jdbcTemplate == null) {
                LeftWhereSelectFunImpl.jdbcTemplate = jdbcTemplate;
            }
        }
    }

    private LinkedList<Condition> conditions;

    public LeftWhereSelectFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.conditions.add(new WhereConditionMark());
    }

    public LeftWhereSelectFunImpl(LinkedList<Condition> conditions, boolean isAddMark) {
        this.conditions = conditions;
        if (isAddMark) {
            this.conditions.add(new WhereConditionMark());
        }
    }

    public LeftWhereSelectFunImpl() {
    }


    @Override
    public LinkedList<Condition> getConditions() {
        return this.conditions;
    }

    @Override
    public M ex() {
        return exs().get(0);
    }


    @Override
    public LeftHavingPendingFunImpl<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V, V> groupBy(GetterFun<M, V>... c) {
        return null;
    }

    @Override
    public LeftHavingPendingFunImpl<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V, V> groupBy(GGetterFun<M2, V>... c) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> limitPage(int pageNum, int size) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> or() {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> eq(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> eq(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> ueq(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> ueq(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> gt(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> gt(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> lt(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> lt(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> gtEq(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> gtEq(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> ltEq(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> ltEq(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> between(GetterFun<M, V> col, V start, V end) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> between(boolean condition, GetterFun<M, V> col, V start, V end) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> notBetween(GetterFun<M, V> col, V start, V end) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> notBetween(boolean condition, GetterFun<M, V> col, V start, V end) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> like(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> like(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> likeLeft(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> likeLeft(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> likeRight(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> likeRight(boolean condition, GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> in(GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> in(boolean condition, GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> in(GetterFun<M, V> col, Collection... values) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> in(boolean condition, GetterFun<M, V> col, Collection... values) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> notIn(GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> notIn(boolean condition, GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> notIn(GetterFun<M, V> col, Collection... values) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> notIn(boolean condition, GetterFun<M, V> col, Collection... values) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> exists(String existsSql) {
        return null;
    }

    @Override
    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> exists(boolean condition, String existsSql) {
        return null;
    }
}
