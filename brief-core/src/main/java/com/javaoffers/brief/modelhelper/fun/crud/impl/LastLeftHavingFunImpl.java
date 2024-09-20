package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.GGGetterFun;
import com.javaoffers.brief.modelhelper.fun.GGetterFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrCondition;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Description: having 后面只允许出现统计函数条件表达式. 若想非统计函数表达式应在where / on 中书写. (设计如此)
 * @Auther: create by cmj on 2022/6/5 19:44
 */
public class LastLeftHavingFunImpl<M, M2, M3,
        C extends GetterFun<M, ?>,
        C2 extends GGetterFun<M2, ?>,
        C3 extends GGGetterFun<M3, ?>, V,
        R extends LastLeftHavingFunImpl<M, M2, M3, C , C2 , C3 , V, R>>
extends LeftHavingFunImpl<M,M2,C,C2,V,V, R>
{

    private LinkedList<Condition> conditions;
    private LeftHavingFunImpl leftHavingFun;
    public LastLeftHavingFunImpl(LinkedList<Condition> conditions) {
        super(conditions);
        this.conditions = conditions;
        this.leftHavingFun = new LeftHavingFunImpl(conditions, false);
    }

    
    public R or() {
        conditions.add(new OrCondition());
        super.or();
        return (R) this;
    }

    /**
     * ---------------------------------------------C3------------------------------------------------------
     *
     * @param aggTag
     * @param col
     * @param value
     * @return
     */
    public R eq(AggTag aggTag, C3 col, V value) {
        leftHavingFun.eq(aggTag, col, value);
        return (R) this;
    }

    public R eq(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.eq(condition, aggTag, col,value);
        return (R) this;
    }


    public R ueq(AggTag aggTag, C3 col, V value) {
        leftHavingFun.ueq(aggTag, col, value);
        return (R) this;
    }


    public R ueq(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.ueq(condition, aggTag, col, value);
        return (R) this;
    }


    public R gt(AggTag aggTag, C3 col, V value) {
        leftHavingFun.gt(aggTag, col, value);
        return (R) this;
    }


    public R gt(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.gt(condition, aggTag, col, value);
        return (R) this;
    }


    public R lt(AggTag aggTag, C3 col, V value) {
        leftHavingFun.lt(aggTag, col, value);
        return (R) this;
    }


    public R lt(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.lt(condition, aggTag, col, value);
        return (R) this;
    }


    public R gtEq(AggTag aggTag, C3 col, V value) {
        leftHavingFun.gtEq(aggTag, col, value);
        return (R) this;
    }

    public R gtEq(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.gtEq(condition, aggTag, col, value);
        return (R) this;
    }


    public R ltEq(AggTag aggTag, C3 col, V value) {
        leftHavingFun.ltEq(aggTag, col, value);
        return (R) this;
    }


    public R ltEq(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.ltEq(condition, aggTag, col, value);
        return (R) this;
    }


    public R between(AggTag aggTag, C3 col, V start, V end) {
        leftHavingFun.between(aggTag, col, start, end);
        return (R) this;
    }


    public R between(boolean condition, AggTag aggTag, C3 col, V start, V end) {
        leftHavingFun.between(condition, aggTag, col, start, end);
        return (R) this;
    }


    public R notBetween(AggTag aggTag, C3 col, V start, V end) {
        leftHavingFun.notBetween(aggTag, col, start, end);
        return (R) this;
    }

    public R notBetween(boolean condition, AggTag aggTag, C3 col, V start, V end) {
        leftHavingFun.notBetween(condition, aggTag, col, start, end);
        return (R) this;
    }

    public R like(AggTag aggTag, C3 col, V value) {
        leftHavingFun.like(aggTag, col, value);
        return (R) this;
    }

    public R like(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.like(condition, aggTag, col, value);
        return (R) this;
    }

    public R likeLeft(AggTag aggTag, C3 col, V value) {
        leftHavingFun.likeLeft(aggTag, col, value);
        return (R) this;
    }


    public R likeLeft(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.likeLeft(condition, aggTag, col, value);
        return (R) this;
    }


    public R likeRight(AggTag aggTag, C3 col, V value) {
        leftHavingFun.likeRight(aggTag, col, value);
        return (R) this;
    }


    public R likeRight(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.likeRight(condition,aggTag, col, value);
        return (R) this;
    }

    @SafeVarargs
    public final R in(AggTag aggTag, C3 col, V... values) {
        leftHavingFun.in(aggTag, col,values);
        return (R) this;
    }
    @SafeVarargs
    public final LastLeftHavingFunImpl in(boolean condition, AggTag aggTag, C3 col, V... values) {
        leftHavingFun.in(condition, aggTag, col, values);
        return (R) this;
    }

    @SafeVarargs
    public final R in(AggTag aggTag, C3 col, Collection... values) {
        leftHavingFun.in(aggTag,col, values);
        return (R) this;
    }

    @SafeVarargs
    public final R in(boolean condition, AggTag aggTag, C3 col, Collection... values) {
        leftHavingFun.in(condition, aggTag, col, values);
        return (R) this;
    }

    @SafeVarargs
    public final R notIn(AggTag aggTag, C3 col, V... values) {
        leftHavingFun.notIn(aggTag, col, values);
        return (R) this;
    }

    @SafeVarargs
    public final R notIn(boolean condition, AggTag aggTag, C3 col, V... values) {
        leftHavingFun.notIn(condition,aggTag, col, values);
        return (R) this;
    }

    @SafeVarargs
    public final R notIn(AggTag aggTag, C3 col, Collection... values) {
        leftHavingFun.notIn(aggTag, col, values);
        return (R) this;
    }
    @SafeVarargs
    public final R notIn(boolean condition, AggTag aggTag, C3 col, Collection... values) {
        leftHavingFun.notIn(condition, aggTag, col, values);
        return (R) this;
    }

    public R limitPage(int pageNum, int size) {
        leftHavingFun.limitPage(pageNum, size);
        return (R) this;
    }
    @SafeVarargs
    public final R orderA(C3... cs) {
        leftHavingFun.orderA(cs);
        return (R) this;
    }
    @SafeVarargs
    public final R orderA(boolean condition, C3... cs) {
        leftHavingFun.orderA(condition, cs);
        return (R) this;
    }
    @SafeVarargs
    public final R orderD(C3... cs) {
        leftHavingFun.orderD(cs);
        return (R) this;
    }
    @SafeVarargs
    public final  R orderD(boolean condition, C3... cs) {
        leftHavingFun.orderD(condition, cs);
        return (R) this;
    }
    public M ex() {
        return (M)leftHavingFun.ex();
    }
    public List<M> exs() {
        return leftHavingFun.exs();
    }
}
