package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.GGetterFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;

/**
 * @Description: 支持分组.
 * @Auther: create by cmj on 2022/6/5 18:10
 */
public interface LeftGroupFun<M, M2, C extends GetterFun<M,?>, C2 extends GGetterFun<M2,?>, V, R extends LeftHavingPendingFun<M,M2,C,C2, ?,?>> {
    R groupBy(C... c);
    R groupBy(C2... c);
}
