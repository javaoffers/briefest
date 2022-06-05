package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.crud.HavingFun;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 19:44
 */
public class HavingFunImpl<M, C, V> implements HavingFun<M, C, V, HavingFunImpl> {

    LinkedList<Condition> conditions;

    public HavingFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public HavingFunImpl eq(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl eq(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl ueq(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl ueq(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl gt(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl gt(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl lt(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl lt(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl gtEq(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl gtEq(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl ltEq(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl ltEq(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl between(AggTag aggTag, C col, V start, V end) {
        return null;
    }

    @Override
    public HavingFunImpl between(boolean condition, AggTag aggTag, C col, V start, V end) {
        return null;
    }

    @Override
    public HavingFunImpl notBetween(AggTag aggTag, C col, V start, V end) {
        return null;
    }

    @Override
    public HavingFunImpl notBetween(boolean condition, AggTag aggTag, C col, V start, V end) {
        return null;
    }

    @Override
    public HavingFunImpl like(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl like(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl likeLeft(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl likeLeft(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl likeRight(AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl likeRight(boolean condition, AggTag aggTag, C col, V value) {
        return null;
    }

    @Override
    public HavingFunImpl in(AggTag aggTag, C col, V... values) {
        return null;
    }

    @Override
    public HavingFunImpl in(boolean condition, AggTag aggTag, C col, V... values) {
        return null;
    }

    @Override
    public HavingFunImpl in(AggTag aggTag, C col, Collection... values) {
        return null;
    }

    @Override
    public HavingFunImpl in(boolean condition, AggTag aggTag, C col, Collection... values) {
        return null;
    }

    @Override
    public HavingFunImpl notIn(AggTag aggTag, C col, V... values) {
        return null;
    }

    @Override
    public HavingFunImpl notIn(boolean condition, AggTag aggTag, C col, V... values) {
        return null;
    }

    @Override
    public HavingFunImpl notIn(AggTag aggTag, C col, Collection... values) {
        return null;
    }

    @Override
    public HavingFunImpl notIn(boolean condition, AggTag aggTag, C col, Collection... values) {
        return null;
    }

    @Override
    public LinkedList<Condition> getConditions() {
        return this.conditions;
    }

    @Override
    public List<M> exs() {
        return null;
    }

    @Override
    public M ex() {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> or() {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> eq(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> eq(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> ueq(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> ueq(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> gt(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> gt(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> lt(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> lt(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> gtEq(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> gtEq(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> ltEq(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> ltEq(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> between(C col, V start, V end) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> between(boolean condition, C col, V start, V end) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> notBetween(C col, V start, V end) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> notBetween(boolean condition, C col, V start, V end) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> like(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> like(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> likeLeft(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> likeLeft(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> likeRight(C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> likeRight(boolean condition, C col, V value) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> in(C col, V... values) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> in(boolean condition, C col, V... values) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> in(C col, Collection... values) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> in(boolean condition, C col, Collection... values) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> notIn(C col, V... values) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> notIn(boolean condition, C col, V... values) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> notIn(C col, Collection... values) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> notIn(boolean condition, C col, Collection... values) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> exists(String existsSql) {
        return null;
    }

    @Override
    public HavingFun<M, C, V, ?> exists(boolean condition, String existsSql) {
        return null;
    }
}
