package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * 单插入.
 * @author create by cmj
 */
public interface OneInsertFun <M, C extends GetterFun<M, Object>, V> extends OneInsertCol<M,C,V>,ExecutOneFun<Id> {




}
