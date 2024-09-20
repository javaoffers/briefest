package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.G4GetterFun;
import com.javaoffers.brief.modelhelper.fun.G5GetterFun;
import com.javaoffers.brief.modelhelper.fun.GGGetterFun;
import com.javaoffers.brief.modelhelper.fun.GGetterFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.where.GroupByWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mingJie
 */
public class LastLeftWhereSelectFunImpl<M, M2, M3, V> extends LeftWhereSelectFunImpl<M, M2, V,LastLeftWhereSelectFunImpl<M, M2, M3, V>> {

    LinkedList<Condition> conditions;

    public LastLeftWhereSelectFunImpl(LinkedList<Condition> conditions) {
        super(conditions);
        this.conditions = conditions;
    }

    @SafeVarargs
    public final LastLeftHavingPendingFunImpl<M, M2, M3, GetterFun<M, V>, GGetterFun<M2, V>, GGGetterFun<M3, V>, V, V> groupBy(G4GetterFun<M, V>... c) {
        conditions.add(new GroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LastLeftHavingPendingFunImpl<>(conditions);
    }

    @SafeVarargs
    public final LastLeftHavingPendingFunImpl<M, M2, M3, GetterFun<M, V>, GGetterFun<M2, V>, GGGetterFun<M3, V>, V, V> groupBy(G5GetterFun<M2, V>... c) {
        conditions.add(new GroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LastLeftHavingPendingFunImpl<>(conditions);
    }

    @SafeVarargs
    public final LastLeftHavingPendingFunImpl<M, M2, M3, GetterFun<M, V>, GGetterFun<M2, V>, GGGetterFun<M3, V>, V, V> groupBy(GGGetterFun<M3, V>... c) {
        conditions.add(new GroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LastLeftHavingPendingFunImpl<>(conditions);
    }

    @SafeVarargs
    public final LastLeftWhereSelectFunImpl<M, M2, M3, V> orderA(GGGetterFun<M3, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, true));
        return this;
    }

    @SafeVarargs
    public final LastLeftWhereSelectFunImpl<M, M2, M3, V> orderA(boolean condition, GGGetterFun<M3, V>... getterFuns) {
        if (condition) {
            orderA(getterFuns);
        }
        return this;
    }

    @SafeVarargs
    public final LastLeftWhereSelectFunImpl<M, M2, M3, V> orderD(GGGetterFun<M3, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, false));
        return this;
    }

    @SafeVarargs
    public final LastLeftWhereSelectFunImpl<M, M2, M3, V> orderD(boolean condition, GGGetterFun<M3, V>... getterFuns) {
        if (condition) {
            orderD(getterFuns);
        }
        return this;
    }
}
