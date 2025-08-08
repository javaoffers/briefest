package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

public interface LastJoinFunTableExtend<M1,M2,M3,C3 extends GetterFun<M3,Object>,V> {

    LastJoinFun<M1,M2,M3,C3,V> tablePrefix(String tablePrefix);

    LastJoinFun<M1,M2,M3,C3,V> tableSuffix(String tableSuffix);
}
