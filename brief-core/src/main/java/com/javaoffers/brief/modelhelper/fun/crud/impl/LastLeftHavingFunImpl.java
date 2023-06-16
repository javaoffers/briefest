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
public class LastLeftHavingFunImpl<M, M2, M3, C extends GetterFun<M, ?>, C2 extends GGetterFun<M2, ?>, C3 extends GGGetterFun<M3, ?>, V>
extends LeftHavingFunImpl<M,M2,C,C2,V,V>
{

    private LinkedList<Condition> conditions;
    private LeftHavingFunImpl leftHavingFun;
    public LastLeftHavingFunImpl(LinkedList<Condition> conditions) {
        super(conditions);
        this.conditions = conditions;
        this.leftHavingFun = new LeftHavingFunImpl(conditions, false);
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> unite(Consumer<LeftHavingFunImpl<M, M2, C, C2, V, V>> r) {
         super.unite(r);
        return this;
    }

    @Override
    public LeftHavingFunImpl<M, M2, C, C2, V, V> unite(boolean condition, Consumer<LeftHavingFunImpl<M, M2, C, C2, V, V>> r) {
         super.unite(condition, r);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> eq(AggTag aggTag, C col, V value) {
        super.eq(aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> eq(boolean condition, AggTag aggTag, C col, V value) {
        super.eq(condition, aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ueq(AggTag aggTag, C col, V value) {
        super.ueq(aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ueq(boolean condition, AggTag aggTag, C col, V value) {
        super.ueq(condition, aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gt(AggTag aggTag, C col, V value) {
        super.gt(aggTag,col,value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gt(boolean condition, AggTag aggTag, C col, V value) {
        super.gt(condition,aggTag,col,value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> lt(AggTag aggTag, C col, V value) {
        super.lt(aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> lt(boolean condition, AggTag aggTag, C col, V value) {
        super.lt(condition, aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gtEq(AggTag aggTag, C col, V value) {
        super.gtEq(aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gtEq(boolean condition, AggTag aggTag, C col, V value) {
        super.gtEq(condition, aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ltEq(AggTag aggTag, C col, V value) {
        super.ltEq(aggTag, col,value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ltEq(boolean condition, AggTag aggTag, C col, V value) {
        super.ltEq(condition, aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> between(AggTag aggTag, C col, V start, V end) {
        super.between(aggTag, col, start, end);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> between(boolean condition, AggTag aggTag, C col, V start, V end) {
        super.between(condition, aggTag, col, start, end);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notBetween(AggTag aggTag, C col, V start, V end) {
        super.notBetween(aggTag, col, start, end);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notBetween(boolean condition, AggTag aggTag, C col, V start, V end) {
        super.notBetween(condition, aggTag, col, start, end);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> like(AggTag aggTag, C col, V value) {
        super.like(aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> like(boolean condition, AggTag aggTag, C col, V value) {
        super.like(condition, aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeLeft(AggTag aggTag, C col, V value) {
        super.likeLeft(aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeLeft(boolean condition, AggTag aggTag, C col, V value) {
        super.likeLeft(condition, aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeRight(AggTag aggTag, C col, V value) {
        super.likeRight(aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeRight(boolean condition, AggTag aggTag, C col, V value) {
        super.likeRight(condition, aggTag, col, value);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(AggTag aggTag, C col, V... values) {
        super.in(aggTag, col, values);
        return this;
    }

    
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(boolean condition, AggTag aggTag, C col, V... values) {
        super.in(condition, aggTag, col,values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(AggTag aggTag, C col, Collection... values) {
        super.in(aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(boolean condition, AggTag aggTag, C col, Collection... values) {
        super.in(condition, aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(AggTag aggTag, C col, V... values) {
        super.notIn(aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(boolean condition, AggTag aggTag, C col, V... values) {
        super.notIn(condition, aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(AggTag aggTag, C col, Collection... values) {
        super.notIn(aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(boolean condition, AggTag aggTag, C col, Collection... values) {
        super.notIn(condition,aggTag,col,values);
        return this;
    }

    /**
     * ---------------------------------------------C2------------------------------------------------------
     *
     * @param aggTag
     * @param col
     * @param value
     * @return
     */
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> eq(AggTag aggTag, C2 col, V value) {
        super.eq(aggTag, col, value);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> eq(boolean condition, AggTag aggTag, C2 col, V value) {
        super.eq(condition, aggTag, col,value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ueq(AggTag aggTag, C2 col, V value) {
        super.ueq(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ueq(boolean condition, AggTag aggTag, C2 col, V value) {
        super.ueq(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gt(AggTag aggTag, C2 col, V value) {
        super.gt(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gt(boolean condition, AggTag aggTag, C2 col, V value) {
        super.gt(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> lt(AggTag aggTag, C2 col, V value) {
        super.lt(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> lt(boolean condition, AggTag aggTag, C2 col, V value) {
        super.lt(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gtEq(AggTag aggTag, C2 col, V value) {
        super.gtEq(aggTag, col, value);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gtEq(boolean condition, AggTag aggTag, C2 col, V value) {
        super.gtEq(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ltEq(AggTag aggTag, C2 col, V value) {
        super.ltEq(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ltEq(boolean condition, AggTag aggTag, C2 col, V value) {
        super.ltEq(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> between(AggTag aggTag, C2 col, V start, V end) {
        super.between(aggTag, col, start, end);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> between(boolean condition, AggTag aggTag, C2 col, V start, V end) {
        super.between(condition, aggTag, col, start, end);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notBetween(AggTag aggTag, C2 col, V start, V end) {
        super.notBetween(aggTag, col, start, end);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notBetween(boolean condition, AggTag aggTag, C2 col, V start, V end) {
        super.notBetween(condition, aggTag, col, start, end);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> like(AggTag aggTag, C2 col, V value) {
        super.like(aggTag, col, value);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> like(boolean condition, AggTag aggTag, C2 col, V value) {
        super.like(condition, aggTag, col, value);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeLeft(AggTag aggTag, C2 col, V value) {
        super.likeLeft(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeLeft(boolean condition, AggTag aggTag, C2 col, V value) {
        super.likeLeft(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeRight(AggTag aggTag, C2 col, V value) {
        super.likeRight(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeRight(boolean condition, AggTag aggTag, C2 col, V value) {
        super.likeRight(condition,aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(AggTag aggTag, C2 col, V... values) {
        super.in(aggTag, col,values);
        return this;
    }

    public LastLeftHavingFunImpl in(boolean condition, AggTag aggTag, C2 col, V... values) {
        super.in(condition, aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(AggTag aggTag, C2 col, Collection... values) {
        super.in(aggTag,col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(boolean condition, AggTag aggTag, C2 col, Collection... values) {
        super.in(condition, aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(AggTag aggTag, C2 col, V... values) {
        super.notIn(aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(boolean condition, AggTag aggTag, C2 col, V... values) {
        super.notIn(condition,aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(AggTag aggTag, C2 col, Collection... values) {
        super.notIn(aggTag, col, values);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(boolean condition, AggTag aggTag, C2 col, Collection... values) {
        super.notIn(condition, aggTag, col, values);
        return this;
    }

    /**
     * ---------------------------------------------C3------------------------------------------------------
     *
     * @param aggTag
     * @param col
     * @param value
     * @return
     */
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> eq(AggTag aggTag, C3 col, V value) {
        leftHavingFun.eq(aggTag, col, value);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> eq(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.eq(condition, aggTag, col,value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ueq(AggTag aggTag, C3 col, V value) {
        leftHavingFun.ueq(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ueq(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.ueq(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gt(AggTag aggTag, C3 col, V value) {
        leftHavingFun.gt(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gt(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.gt(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> lt(AggTag aggTag, C3 col, V value) {
        leftHavingFun.lt(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> lt(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.lt(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gtEq(AggTag aggTag, C3 col, V value) {
        leftHavingFun.gtEq(aggTag, col, value);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> gtEq(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.gtEq(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ltEq(AggTag aggTag, C3 col, V value) {
        leftHavingFun.ltEq(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> ltEq(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.ltEq(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> between(AggTag aggTag, C3 col, V start, V end) {
        leftHavingFun.between(aggTag, col, start, end);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> between(boolean condition, AggTag aggTag, C3 col, V start, V end) {
        leftHavingFun.between(condition, aggTag, col, start, end);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notBetween(AggTag aggTag, C3 col, V start, V end) {
        leftHavingFun.notBetween(aggTag, col, start, end);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notBetween(boolean condition, AggTag aggTag, C3 col, V start, V end) {
        leftHavingFun.notBetween(condition, aggTag, col, start, end);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> like(AggTag aggTag, C3 col, V value) {
        leftHavingFun.like(aggTag, col, value);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> like(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.like(condition, aggTag, col, value);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeLeft(AggTag aggTag, C3 col, V value) {
        leftHavingFun.likeLeft(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeLeft(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.likeLeft(condition, aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeRight(AggTag aggTag, C3 col, V value) {
        leftHavingFun.likeRight(aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> likeRight(boolean condition, AggTag aggTag, C3 col, V value) {
        leftHavingFun.likeRight(condition,aggTag, col, value);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(AggTag aggTag, C3 col, V... values) {
        leftHavingFun.in(aggTag, col,values);
        return this;
    }

    public LastLeftHavingFunImpl in(boolean condition, AggTag aggTag, C3 col, V... values) {
        leftHavingFun.in(condition, aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(AggTag aggTag, C3 col, Collection... values) {
        leftHavingFun.in(aggTag,col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> in(boolean condition, AggTag aggTag, C3 col, Collection... values) {
        leftHavingFun.in(condition, aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(AggTag aggTag, C3 col, V... values) {
        leftHavingFun.notIn(aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(boolean condition, AggTag aggTag, C3 col, V... values) {
        leftHavingFun.notIn(condition,aggTag, col, values);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(AggTag aggTag, C3 col, Collection... values) {
        leftHavingFun.notIn(aggTag, col, values);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> notIn(boolean condition, AggTag aggTag, C3 col, Collection... values) {
        leftHavingFun.notIn(condition, aggTag, col, values);
        return this;
    }


    public M ex() {
        return (M)leftHavingFun.ex();
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> limitPage(int pageNum, int size) {
        leftHavingFun.limitPage(pageNum, size);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderA(C... cs) {
        leftHavingFun.orderA(cs);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderA(boolean condition, C... cs) {
        leftHavingFun.orderA(condition, cs);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderD(C... cs) {
        leftHavingFun.orderD(cs);
        return this;
    }


    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderD(boolean condition, C... cs) {
        leftHavingFun.orderD(condition, cs);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderA(C2... cs) {
        leftHavingFun.orderA(cs);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderA(boolean condition, C2... cs) {
        leftHavingFun.orderA(condition, cs);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderD(C2... cs) {
        leftHavingFun.orderD(cs);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderD(boolean condition, C2... cs) {
        leftHavingFun.orderD(condition, cs);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderA(C3... cs) {
        leftHavingFun.orderA(cs);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderA(boolean condition, C3... cs) {
        leftHavingFun.orderA(condition, cs);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderD(C3... cs) {
        leftHavingFun.orderD(cs);
        return this;
    }

    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> orderD(boolean condition, C3... cs) {
        leftHavingFun.orderD(condition, cs);
        return this;
    }

    public List<M> exs() {
        return leftHavingFun.exs();
    }
}
