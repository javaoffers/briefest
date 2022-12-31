package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GGGetterFun;
import com.javaoffers.batis.modelhelper.fun.GGetterFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.where.GroupByWordCondition;
import com.javaoffers.batis.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.batis.modelhelper.fun.crud.LeftWhereSelectFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author mingJie
 */
public class LastLeftWhereSelectFunImpl<M, M2, M3, V> extends LeftWhereSelectFunImpl<M, M2, V> {

    LinkedList<Condition> conditions;

    public LastLeftWhereSelectFunImpl(LinkedList<Condition> conditions) {
        super(conditions);
        this.conditions = conditions;
    }

    @Override
    public LastLeftHavingPendingFunImpl<M, M2, M3, GetterFun<M, V>, GGetterFun<M2, V>, GGGetterFun<M3, V>, V, V> groupBy(GetterFun<M, V>... c) {
        conditions.add(new GroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LastLeftHavingPendingFunImpl<>(conditions);
    }

    @Override
    public LastLeftHavingPendingFunImpl<M, M2, M3, GetterFun<M, V>, GGetterFun<M2, V>, GGGetterFun<M3, V>, V, V> groupBy(GGetterFun<M2, V>... c) {
        conditions.add(new GroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LastLeftHavingPendingFunImpl<>(conditions);
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, GetterFun<M, V>, GGetterFun<M2, V>, GGGetterFun<M3, V>, V, V> groupBy(GGGetterFun<M3, V>... c) {
        conditions.add(new GroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LastLeftHavingPendingFunImpl<>(conditions);
    }

    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderA(GGGetterFun<M3, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, true));
        return this;
    }

    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderA(boolean condition, GGGetterFun<M3, V>... getterFuns) {
        if (condition) {
            orderA(getterFuns);
        }
        return this;
    }

    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderD(GGGetterFun<M3, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, false));
        return this;
    }

    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderD(boolean condition, GGGetterFun<M3, V>... getterFuns) {
        if (condition) {
            orderD(getterFuns);
        }
        return this;
    }

    @Override
    public M ex() {
        return super.ex();
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> or() {
        super.or();
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> unite(Consumer<LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V>> r) {
        super.unite(r);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> unite(boolean condition, Consumer<LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V>> r) {
        super.unite(condition, r);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> condSQL(String sql) {
        super.condSQL(sql);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> condSQL(boolean condition, String sql) {
        super.condSQL(condition, sql);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> condSQL(String sql, Map<String, Object> params) {
        super.condSQL(sql, params);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> condSQL(boolean condition, String sql, Map<String, Object> params) {
        super.condSQL(condition, sql, params);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderA(GetterFun<M, V>... getterFuns) {
        super.orderA(getterFuns);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderA(boolean condition, GetterFun<M, V>... getterFuns) {
        super.orderA(condition, getterFuns);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderD(GetterFun<M, V>... getterFuns) {
        super.orderD(getterFuns);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderD(boolean condition, GetterFun<M, V>... getterFuns) {
        super.orderD(condition, getterFuns);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderA(GGetterFun<M2, V>... getterFuns) {
        super.orderA(getterFuns);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderA(boolean condition, GGetterFun<M2, V>... getterFuns) {
        super.orderA(condition, getterFuns);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderD(GGetterFun<M2, V>... getterFuns) {
        super.orderD(getterFuns);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> orderD(boolean condition, GGetterFun<M2, V>... getterFuns) {
        super.orderD(condition, getterFuns);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> eq(GetterFun<M, V> col, V value) {
        super.eq(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> eq(boolean condition, GetterFun<M, V> col, V value) {
        super.eq(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> ueq(GetterFun<M, V> col, V value) {
        super.ueq(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> ueq(boolean condition, GetterFun<M, V> col, V value) {
        super.ueq(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> gt(GetterFun<M, V> col, V value) {
        super.gt(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> gt(boolean condition, GetterFun<M, V> col, V value) {
        super.gt(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> lt(GetterFun<M, V> col, V value) {
        super.lt(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> lt(boolean condition, GetterFun<M, V> col, V value) {
        super.lt(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> gtEq(GetterFun<M, V> col, V value) {
        super.gtEq(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> gtEq(boolean condition, GetterFun<M, V> col, V value) {
        super.gtEq(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> ltEq(GetterFun<M, V> col, V value) {
        super.ltEq(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> ltEq(boolean condition, GetterFun<M, V> col, V value) {
        super.ltEq(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> between(GetterFun<M, V> col, V start, V end) {
        super.between(col, start, end);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> between(boolean condition, GetterFun<M, V> col, V start, V end) {
        super.between(condition, col, start, end);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> notBetween(GetterFun<M, V> col, V start, V end) {
        super.notBetween(col, start, end);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> notBetween(boolean condition, GetterFun<M, V> col, V start, V end) {
        super.notBetween(condition, col, start, end);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> like(GetterFun<M, V> col, V value) {
        super.like(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> like(boolean condition, GetterFun<M, V> col, V value) {
        super.like(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> likeLeft(GetterFun<M, V> col, V value) {
        super.likeLeft(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> likeLeft(boolean condition, GetterFun<M, V> col, V value) {
        super.likeLeft(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> likeRight(GetterFun<M, V> col, V value) {
        super.likeRight(col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> likeRight(boolean condition, GetterFun<M, V> col, V value) {
        super.likeRight(condition, col, value);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> in(GetterFun<M, V> col, V... values) {
        super.in(col, values);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> in(boolean condition, GetterFun<M, V> col, V... values) {
        super.in(condition, col, values);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> in(GetterFun<M, V> col, Collection... values) {
        super.in(col, values);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> in(boolean condition, GetterFun<M, V> col, Collection... values) {
        super.in(condition, col, values);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> notIn(GetterFun<M, V> col, V... values) {
        super.notIn(col, values);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> notIn(boolean condition, GetterFun<M, V> col, V... values) {
        super.notIn(condition, col, values);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> notIn(GetterFun<M, V> col, Collection... values) {
        super.notIn(col, values);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> notIn(boolean condition, GetterFun<M, V> col, Collection... values) {
        super.notIn(condition, col, values);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> isNull(GetterFun<M, V>... cols) {
        super.isNull(cols);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> isNull(boolean condition, GetterFun<M, V>... cols) {
        super.isNull(condition, cols);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> isNotNull(GetterFun<M, V>... cols) {
        super.isNotNull(cols);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> isNotNull(boolean condition, GetterFun<M, V>... cols) {
        super.isNotNull(condition, cols);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> exists(String existsSql) {
        super.exists(existsSql);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> exists(boolean condition, String existsSql) {
        super.exists(condition, existsSql);
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M, M2, M3, V> limitPage(int pageNum, int size) {
        super.limitPage(pageNum, size);
        return this;
    }

    @Override
    public List<M> exs() {
        return super.exs();
    }
}
