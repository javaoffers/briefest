package com.javaoffers.brief.modelhelper.fun.crud.update;

import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.WhereModifyFun;

/**
 * @author create by cmj on 2022-06-29
 */
public interface OneUpdateFun  <M, C extends GetterFun<M, Object>, V>  extends OneUpdateCol<M,C,V>{

    /**
     * where
     * @return
     */
    WhereModifyFun<M,V> where();
}
