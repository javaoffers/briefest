package com.javaoffers.brief.modelhelper.fun.crud.update;

import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.WhereModifyFun;

public interface PrepareWhereModifyFun <M, C extends GetterFun<M, Object>, V> {

    /**
     * where
     * @return
     */
    WhereModifyFun<M,V> where();

}
