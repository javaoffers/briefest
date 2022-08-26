package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.util.List;

/**
 * 单插入.
 * @author create by cmj
 */
public interface OneInsertFun <M, C extends GetterFun<M, Object>, V> extends OneInsertCol<M,C,V>,ExecutOneFun<Id> {




}
