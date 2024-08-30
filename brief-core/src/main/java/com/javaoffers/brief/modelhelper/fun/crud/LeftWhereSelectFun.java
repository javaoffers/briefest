package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.ExecutFun;
import com.javaoffers.brief.modelhelper.fun.GGetterFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.StreamingFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.LeftHavingPendingFunImpl;

/**
 * @Description: select where 条件
 * @Auther: create by cmj on 2022/5/4 21:37
 */
public interface LeftWhereSelectFun<M,M2,C extends GetterFun<M,V>,C2 extends GGetterFun<M2,?>,V, R> extends
        WhereFun<M, GetterFun<M,V>,V, R> ,
        LeftGroupFun<M,M2, C, C2, V , LeftHavingPendingFunImpl<M,M2, C,C2,?,?>>,
        OrderFun<M, GetterFun<M,V>,V, R>,
        LimitFun<M, R>,
        StreamingFun<M>,
        ExecutFun<M> {

    public R orderA(GGetterFun<M2, V>... getterFuns);

    public R orderA(boolean condition, GGetterFun<M2, V>... getterFuns) ;

    public R orderD(GGetterFun<M2, V>... getterFuns) ;

    public R orderD(boolean condition, GGetterFun<M2, V>... getterFuns) ;
}
