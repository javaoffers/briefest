package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

public interface JoinFunTableExtend<M1,M2,C2 extends GetterFun<M2,Object>,V> {

    JoinFun<M1,M2,C2,V> tablePrefix(String tablePrefix);

    JoinFun<M1,M2,C2,V> tableSuffix(String tableSuffix);
}
