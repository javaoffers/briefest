package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GGGetterFun;
import com.javaoffers.brief.modelhelper.fun.GGetterFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.where.LeftGroupByWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 19:42
 */
public class LastLeftHavingPendingFunImpl<M, M2, M3, C extends GetterFun<M, ?>, C2 extends GGetterFun<M2, ?>, C3 extends GGGetterFun<M3, ?>, V, V2>
        extends LeftHavingPendingFunImpl<M, M2, C, C2, V, V> {

    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl whereSelectFun;

    public LastLeftHavingPendingFunImpl(LinkedList<Condition> conditions) {
        super(conditions);
        this.conditions = conditions;
        this.whereSelectFun = new WhereSelectFunImpl(this.conditions, false);

    }

    @Override
    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> groupBy(GetterFun<M, V>... c) {
         super.groupBy(c);
         return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> groupBy(String sqlCol) {
        super.groupBy(sqlCol);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> groupBy(GGetterFun<M2, V>... c) {

        super.groupBy(c);
        return this;
    }

    /**
     * 子表分组
     *
     * @param c 子表分组字段
     * @return
     */
    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> groupBy(GGGetterFun<M3, V>... c) {
        conditions.add(new LeftGroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2>(conditions);
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> limitPage(int pageNum, int size) {
        super.limitPage(pageNum, size);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderA(C... cs) {
        super.orderA(cs);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderA(boolean condition, C... cs) {
        super.orderA(condition, cs);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderD(C... cs) {
        super.orderD(cs);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderD(boolean condition, C... cs) {
        super.orderD(condition, cs);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderA(C2... cs) {
        super.orderA(cs);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderA(boolean condition, C2... cs) {
        super.orderA(condition, cs);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderD(C2... cs) {
        super.orderD(cs);
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderD(boolean condition, C2... cs) {
        super.orderD(condition, cs);
        return this;
    }


    @Override
    public LastLeftHavingFunImpl<M, M2, M3, C, C2, C3, V> having() {
        return new LastLeftHavingFunImpl(this.conditions);
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderD(C3... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, false));
        return this;
    }

    public LastLeftHavingPendingFunImpl<M, M2, M3, C, C2, C3, V, V2> orderD(boolean condition, C3... cs) {
        if (condition) {
            orderD(cs);
        }
        return this;
    }

    public List<M> exs() {
        return whereSelectFun.exs();
    }
}
