package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

public interface SelectFun<M, C extends GetterFun<M, Object>, V> extends BaseSelectFun<M,C,V> {

    /**
     * select distinct xxx
     * @return
     */
    SmartSelectFun<M,C,V> distinct();

}
