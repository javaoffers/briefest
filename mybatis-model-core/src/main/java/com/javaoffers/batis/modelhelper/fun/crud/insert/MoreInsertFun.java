package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.util.List;

/**
 * 多插入.
 * @author create by cmj
 */
public interface MoreInsertFun<M, C extends GetterFun<M, Object>, V> extends MoreInsertColAll<M,C,V>, ExecutOneFun<List<Id>> {


}
