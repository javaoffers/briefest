package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.GGetterFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.OrderCondition;
import com.javaoffers.batis.modelhelper.fun.crud.impl.LeftHavingPendingFunImpl;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: select where 条件
 * @Auther: create by cmj on 2022/5/4 21:37
 */
public interface LeftWhereSelectFun<M,M2,C extends GetterFun<M,V>,C2 extends GGetterFun<M2,?>,V> extends
        WhereFun<M, GetterFun<M,V>,V, LeftWhereSelectFun<M,M2,C,C2,V>> ,
        LeftGroupFun<M,M2, C, C2, V , LeftHavingPendingFunImpl<M,M2, C,C2,?,?>>,
        OrderFun<M, GetterFun<M,V>,V, LeftWhereSelectFun<M,M2,C,C2,V>>,
        LimitFun<M, LeftWhereSelectFun<M,M2,C,C2,V>>,
        ExecutFun<M> {

    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> orderA(GGetterFun<M2, V>... getterFuns);

    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> orderA(boolean condition, GGetterFun<M2, V>... getterFuns) ;

    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> orderD(GGetterFun<M2, V>... getterFuns) ;

    public LeftWhereSelectFun<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V> orderD(boolean condition, GGetterFun<M2, V>... getterFuns) ;
}
