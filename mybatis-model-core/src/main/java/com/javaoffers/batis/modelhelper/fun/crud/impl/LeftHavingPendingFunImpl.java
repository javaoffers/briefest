package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GGetterFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.LeftGroupByWordCondition;
import com.javaoffers.batis.modelhelper.fun.condition.LimitWordCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OrderWordCondition;
import com.javaoffers.batis.modelhelper.fun.crud.LeftHavingPendingFun;
import com.javaoffers.batis.modelhelper.fun.crud.OrderFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 19:42
 */
public class LeftHavingPendingFunImpl<M, M2, C extends GetterFun<M,?>, C2 extends GGetterFun<M2,?>, V, V2> implements
        LeftHavingPendingFun<M, M2, C, C2, V, LeftHavingFunImpl<M,M2,C,C2,V,V2>>,
        OrderFun<M,C,V,LeftHavingPendingFunImpl<M,M2,C,C2,V,V2>> {

    private LinkedList<Condition> conditions;

    public LeftHavingPendingFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public LinkedList<Condition> getConditions() {
        return this.conditions;
    }

    @Override
    public M ex() {
        List<M> exs = exs();
        if (exs != null && exs.size() > 0) {
            return exs.get(0);
        }
        return null;
    }

    /**
     * 主表分组
     * @param c 主表分组字段
     * @return this
     */
    public LeftHavingPendingFunImpl<M, M2, C,C2,V,V2> groupBy(GetterFun<M, V>... c) {
        conditions.add(new LeftGroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LeftHavingPendingFunImpl<M, M2, C,C2,V,V2>(conditions);
    }

    /**
     * 分组，支持元SQL。 比如 groupBy( " left(colName, 10) ")
     * @param sqlCol col
     * @return this
     */
    public LeftHavingPendingFunImpl<M, M2, C,C2,V,V2> groupBy(String sqlCol) {
        conditions.add(new LeftGroupByWordCondition(new String[]{sqlCol}, ConditionTag.GROUP_BY));
        return new LeftHavingPendingFunImpl<M, M2, C,C2,V,V2>(conditions);
    }

    /**
     * 子表分组
     * @param c 子表分组字段
     * @return
     */
    public LeftHavingPendingFunImpl<M, M2, C,C2,V,V2> groupBy(GGetterFun<M2, V>... c) {
        conditions.add(new LeftGroupByWordCondition(c, ConditionTag.GROUP_BY));
        return new LeftHavingPendingFunImpl<M, M2, C,C2,V,V2>(conditions);
    }

    /**
     * 分页
     * @return
     */
    public LeftHavingPendingFunImpl<M, M2, C,C2,V,V2> limitPage(int pageNum, int size) {
        this.conditions.add(new LimitWordCondition(pageNum, size));
        return new LeftHavingPendingFunImpl<M, M2, C,C2,V,V2>(conditions);
    }


    @Override
    public LeftHavingFunImpl<M,M2,C,C2,V,V2>  having() {
        LeftHavingFunImpl mcvHavingFun = new LeftHavingFunImpl<>(this.conditions);
        return mcvHavingFun;
    }


    @Override
    public LeftHavingPendingFunImpl<M, M2, C, C2, V, V2> orderA(C... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,true));
        return this;
    }

    @Override
    public LeftHavingPendingFunImpl<M, M2, C, C2, V, V2> orderA(boolean condition, C... cs) {
        if(condition){
            orderA(cs);
        }
        return this;
    }

    @Override
    public LeftHavingPendingFunImpl<M, M2, C, C2, V, V2> orderD(C... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,false));
        return this;
    }

    @Override
    public LeftHavingPendingFunImpl<M, M2, C, C2, V, V2> orderD(boolean condition, C... cs) {
        if(condition){
            orderD(cs);
        }
        return this;
    }

    public LeftHavingPendingFunImpl<M, M2, C, C2, V, V2> orderA(C2... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,true));
        return this;
    }

    public LeftHavingPendingFunImpl<M, M2, C, C2, V, V2> orderA(boolean condition, C2... cs) {
        if(condition){
            orderA(cs);
        }
        return this;
    }

    public LeftHavingPendingFunImpl<M, M2, C, C2, V, V2> orderD(C2... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,false));
        return this;
    }

    public LeftHavingPendingFunImpl<M, M2, C, C2, V, V2> orderD(boolean condition, C2... cs) {
        if(condition){
            orderD(cs);
        }
        return this;
    }
}
