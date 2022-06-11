package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.BetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ExistsCondition;
import com.javaoffers.batis.modelhelper.fun.condition.HavingBetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.HavingGroupCondition;
import com.javaoffers.batis.modelhelper.fun.condition.HavingInCondition;
import com.javaoffers.batis.modelhelper.fun.condition.HavingMarkCondition;
import com.javaoffers.batis.modelhelper.fun.condition.InCondition;
import com.javaoffers.batis.modelhelper.fun.condition.LimitCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereOnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.HavingFun;
import com.javaoffers.batis.modelhelper.fun.crud.LimitFun;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 19:44
 */
public class HavingFunImpl<M, C extends GetterFun, V> implements HavingFun<M, C, V, HavingFunImpl<M,C,V>>
        , LimitFun<M, HavingFunImpl<M,C,V>> {

    LinkedList<Condition> conditions;

    public HavingFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.conditions.add(new HavingMarkCondition());
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
    public LinkedList<Condition> getConditions() {
        return this.conditions;
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
    public HavingFun<M, C, V, ?> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> eq(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.EQ));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> eq(boolean condition, C col, V value) {
        if(condition){
            eq(col,value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> ueq(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.UEQ));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> ueq(boolean condition, C col, V value) {
        if(condition){
           ueq(col,value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> gt(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.GT));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> gt(boolean condition, C col, V value) {
        if(condition){
            gt(col,value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> lt(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.LT));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> lt(boolean condition, C col, V value) {
        if(condition){
            lt(col,value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> gtEq(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> gtEq(boolean condition, C col, V value) {
        if(condition){
            gtEq(col, value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> ltEq(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> ltEq(boolean condition, C col, V value) {
        if(condition){
            ltEq(col, value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> between(C col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> between(boolean condition, C col, V start, V end) {
        if(condition){
            between(col,start,end);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> notBetween(C col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.NOT_BETWEEN));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> notBetween(boolean condition, C col, V start, V end) {
        if(condition){
            notBetween(col, start, end);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> like(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.LIKE));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> like(boolean condition, C col, V value) {
        if(condition){
            like(col, value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> likeLeft(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.LIKE_LEFT));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> likeLeft(boolean condition, C col, V value) {
        if(condition){
            likeLeft(col, value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> likeRight(C col, V value) {
        conditions.add(new WhereOnCondition(col,value, ConditionTag.LIKE_RIGHT));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> likeRight(boolean condition, C col, V value) {
        if(condition){
            likeRight(col, value);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> in(C col, V... values) {
        conditions.add(new InCondition(col,values, ConditionTag.IN));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> in(boolean condition, C col, V... values) {
        if(condition){
            in(col,values);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> in(C col, Collection... values) {
        conditions.add(new InCondition(col,values, ConditionTag.IN));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> in(boolean condition, C col, Collection... values) {
        if(condition){
            in(col,values);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> notIn(C col, V... values) {
        conditions.add(new InCondition(col,values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> notIn(boolean condition, C col, V... values) {
        if(condition){
            notIn(col, values);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> notIn(C col, Collection... values) {
        conditions.add(new InCondition(col,values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> notIn(boolean condition, C col, Collection... values) {
        if(condition){
            notIn(col, values);
        }
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> exists(String existsSql) {
        conditions.add(new ExistsCondition(existsSql));
        return this;
    }

    @Override
    public HavingFun<M, C, V, ?> exists(boolean condition, String existsSql) {
        if(condition){
            exists(existsSql);
        }
        return this;
    }

    @Override
    public HavingFunImpl<M, C, V> limitPage(int pageNum, int size) {
        conditions.add(new LimitCondition<>(pageNum, size));
        return this;
    }
}
