package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.where.LeftGroupByWordCondition;
import com.javaoffers.batis.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.batis.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.batis.modelhelper.fun.crud.HavingFun;
import com.javaoffers.batis.modelhelper.fun.crud.HavingPendingFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 19:42
 */
public class HavingPendingFunImpl<M,C extends GetterFun, V> implements HavingPendingFun<M,C, V, HavingFunImpl<M,C,V>> {

    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl whereSelectFun;

    public HavingPendingFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.whereSelectFun = new WhereSelectFunImpl(this.conditions,false);
    }


    @Override
    public M ex() {
        List<M> exs = exs();
        if(exs != null && exs.size()>0){
            return exs.get(0);
        }
        return null;
    }

    @Override
    public HavingPendingFun<M, C, V, HavingFunImpl<M, C, V>> groupBy(C... c) {
        conditions.add(new LeftGroupByWordCondition(c,ConditionTag.GROUP_BY));
        return this;
    }

    @Override
    public HavingPendingFun<M, C, V, HavingFunImpl<M, C, V>> groupBy(String... c) {
        conditions.add(new LeftGroupByWordCondition(c,ConditionTag.GROUP_BY));
        return this;
    }

    @Override
    public HavingFun<M, C, V, HavingFunImpl<M, C, V>> having() {
        HavingFunImpl mcvHavingFun = new HavingFunImpl<>(this.conditions);
        return mcvHavingFun;
    }

    @Override
    public HavingPendingFun<M, C, V, HavingFunImpl<M, C, V>> limitPage(int pageNum, int size) {
        conditions.add(new LimitWordCondition<>(pageNum, size));
        return this;
    }

    @Override
    public List<M> exs() {
        return whereSelectFun.exs();
    }

    @Override
    public HavingPendingFun<M, C, V, HavingFunImpl<M, C, V>> orderA(C... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,true));
        return this;
    }

    @Override
    public HavingPendingFun<M, C, V, HavingFunImpl<M, C, V>> orderA(boolean condition, C... cs) {
        if(condition){
            orderA(cs);
        }
        return this;
    }

    @Override
    public HavingPendingFun<M, C, V, HavingFunImpl<M, C, V>> orderD(C... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,false));
        return this;
    }

    @Override
    public HavingPendingFun<M, C, V, HavingFunImpl<M, C, V>> orderD(boolean condition, C... cs) {
        if(condition){
            orderD(cs);
        }
        return this;
    }



}
