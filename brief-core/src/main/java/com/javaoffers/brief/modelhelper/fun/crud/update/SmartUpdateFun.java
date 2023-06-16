package com.javaoffers.brief.modelhelper.fun.crud.update;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

/**
 * @author create by cmj on 2022-06-29
 */
public interface SmartUpdateFun<M, C extends GetterFun<M, Object>, V> extends OneUpdateCol<M,C,V>, MoreUpdateFun<M,C,V> {

}
