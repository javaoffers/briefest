package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.SmartSelectFunImpl;

public interface SelectFun<M, C extends GetterFun<M, Object>, V> extends BaseSelectFun<M,C,V,SmartSelectFunImpl<M,C,V>> {

    /**
     * select distinct xxx
     * @return
     */
    SmartSelectFunImpl<M,C,V> distinct();

}
