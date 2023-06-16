package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.where.HavingBetweenCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.HavingGroupCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.HavingInCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.HavingMarkWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LFCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.RFWordCondition;
import com.javaoffers.brief.modelhelper.fun.crud.HavingFun;
import com.javaoffers.brief.modelhelper.fun.crud.WhereSelectFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Description: having 后面只允许出现统计函数条件表达式. 若想非统计函数表达式应在where / on 中书写. (设计如此)
 * @Auther: create by cmj on 2022/6/5 19:44
 */
public class HavingFunImpl<M, C extends GetterFun, V> implements HavingFun<M, C, V, HavingFunImpl<M,C,V>> {

    LinkedList<Condition> conditions;

    WhereSelectFun whereSelectFun;

    public HavingFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.conditions.add(new HavingMarkWordCondition());
        this.whereSelectFun = new WhereSelectFunImpl(this.conditions,false);
    }

    @Override
    public HavingFunImpl<M, C, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public HavingFunImpl<M, C, V> unite(Consumer<HavingFunImpl<M, C, V>> r) {
        conditions.add(new LFCondition( ConditionTag.LK));
        r.accept(this);
        conditions.add(new RFWordCondition( ConditionTag.RK));
        return this;
    }

    @Override
    public HavingFunImpl<M, C, V> unite(boolean condition, Consumer<HavingFunImpl<M, C, V>> r) {
        if(condition){
            unite(r);
        }
        return this;
    }

    @Override
    public HavingFunImpl eq(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag, (GetterFun) col,value, ConditionTag.EQ));
        return this;
    }

    @Override
    public HavingFunImpl eq(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            eq(aggTag,col,value);
        }
        return this;
    }

    @Override
    public HavingFunImpl ueq(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag, (GetterFun) col,value, ConditionTag.UEQ));
        return this;
    }

    @Override
    public HavingFunImpl ueq(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            ueq(aggTag,col,value);
        }
        return this;
    }

    @Override
    public HavingFunImpl gt(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag,  col,value, ConditionTag.GT));
        return this;
    }

    @Override
    public HavingFunImpl gt(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            gt(aggTag,col,value);
        }
        return this;
    }

    @Override
    public HavingFunImpl lt(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag,  col,value, ConditionTag.LT));
        return this;
    }

    @Override
    public HavingFunImpl lt(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            lt(aggTag, col, value);
        }
        return this;
    }

    @Override
    public HavingFunImpl gtEq(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag,  col,value, ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public HavingFunImpl gtEq(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            gtEq(aggTag,col,value);
        }
        return this;
    }

    @Override
    public HavingFunImpl ltEq(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag,  col,value, ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public HavingFunImpl ltEq(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            ltEq(aggTag,col,value);
        }
        return this;
    }

    @Override
    public HavingFunImpl between(AggTag aggTag, C col, V start, V end) {
        conditions.add(new HavingBetweenCondition(aggTag,  col,start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public HavingFunImpl between(boolean condition, AggTag aggTag, C col, V start, V end) {
        if(condition){
            between(aggTag, col, start,end);
        }
        return this;
    }

    @Override
    public HavingFunImpl notBetween(AggTag aggTag, C col, V start, V end) {
        conditions.add(new HavingBetweenCondition(aggTag,  col,start, end, ConditionTag.NOT_BETWEEN));
        return this;
    }

    @Override
    public HavingFunImpl notBetween(boolean condition, AggTag aggTag, C col, V start, V end) {
        if(condition){
            notBetween(aggTag, col, start, end);
        }
        return this;
    }

    @Override
    public HavingFunImpl like(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag,  col, value, ConditionTag.LIKE));
        return this;
    }

    @Override
    public HavingFunImpl like(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            like(aggTag, col, value);
        }
        return this;
    }

    @Override
    public HavingFunImpl likeLeft(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag,  col, value, ConditionTag.LIKE_LEFT));
        return this;
    }

    @Override
    public HavingFunImpl likeLeft(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            likeLeft(aggTag, col, value);
        }
        return this;
    }

    @Override
    public HavingFunImpl likeRight(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag,  col, value, ConditionTag.LIKE_RIGHT));
        return this;
    }

    @Override
    public HavingFunImpl likeRight(boolean condition, AggTag aggTag, C col, V value) {
        if(condition){
            likeLeft(aggTag, col, value);
        }
        return this;
    }

    @Override
    public HavingFunImpl in(AggTag aggTag, C col, V... values) {
        conditions.add(new HavingInCondition(aggTag,  col, values, ConditionTag.IN));
        return this;
    }

    @Override
    public HavingFunImpl in(boolean condition, AggTag aggTag, C col, V... values) {
        if(condition){
            in(aggTag, col, values);
        }
        return this;
    }

    @Override
    public HavingFunImpl in(AggTag aggTag, C col, Collection... values) {
        conditions.add(new HavingInCondition(aggTag,  col, values, ConditionTag.IN));
        return this;
    }

    @Override
    public HavingFunImpl in(boolean condition, AggTag aggTag, C col, Collection... values) {
        if(condition){
            in(aggTag,col,values);
        }
        return this;
    }

    @Override
    public HavingFunImpl notIn(AggTag aggTag, C col, V... values) {
        conditions.add(new HavingInCondition(aggTag,  col, values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    public HavingFunImpl notIn(boolean condition, AggTag aggTag, C col, V... values) {
        if(condition){
            notIn(aggTag,col,values);
        }
        return this;
    }

    @Override
    public HavingFunImpl notIn(AggTag aggTag, C col, Collection... values) {
        conditions.add(new HavingInCondition(aggTag,  col, values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    public HavingFunImpl notIn(boolean condition, AggTag aggTag, C col, Collection... values) {
        if(condition){
            notIn(aggTag, col, values);
        }
        return this;
    }

    @Override
    public M ex() {
        List<M> exs = this.exs();
        if(exs != null && exs.size() > 0){
            return exs.get(0);
        }
        return null;
    }

    @Override
    public HavingFunImpl<M, C, V> limitPage(int pageNum, int size) {
        conditions.add(new LimitWordCondition<>(pageNum, size));
        return this;
    }

    @Override
    public List<M> exs() {
        return whereSelectFun.exs();
    }

    @Override
    public HavingFunImpl<M, C, V> orderA(C... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,true));
        return this;
    }

    @Override
    public HavingFunImpl<M, C, V> orderA(boolean condition, C... cs) {
        if(condition){
            orderA(cs);
        }
        return this;
    }

    @Override
    public HavingFunImpl<M, C, V> orderD(C... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,false));
        return this;
    }

    @Override
    public HavingFunImpl<M, C, V> orderD(boolean condition, C... cs) {
        if(condition){
            orderD(cs);
        }
        return this;
    }
}
