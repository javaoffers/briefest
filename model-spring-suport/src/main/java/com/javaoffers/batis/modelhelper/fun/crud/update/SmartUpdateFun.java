package com.javaoffers.batis.modelhelper.fun.crud.update;

import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @author create by cmj on 2022-06-29
 */
public interface SmartUpdateFun<M, C extends GetterFun<M, Object>, V> extends OneUpdateFun<M,C,V>, MoreUpdateFun<M,C,V> {

}
