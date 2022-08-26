package com.javaoffers.batis.modelhelper.fun.crud.update;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;

import java.util.List;

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
