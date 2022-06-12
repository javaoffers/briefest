package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.GroupByCondition;
import com.javaoffers.batis.modelhelper.fun.crud.impl.HavingPendingFunImpl;

/**
 * @Description: 支持分组.
 * @Auther: create by cmj on 2022/6/5 18:10
 */
public interface GroupFun <M,C, V, R extends HavingPendingFun<M,C, V,?>> {
    R groupBy(C... c);
}
