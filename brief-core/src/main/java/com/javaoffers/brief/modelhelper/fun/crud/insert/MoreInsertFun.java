package com.javaoffers.brief.modelhelper.fun.crud.insert;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.fun.ExecutMoreFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;

/**
 * 多插入.
 * @author create by cmj
 */
public interface MoreInsertFun<M, C extends GetterFun<M, Object>, V> extends MoreInsertColAll<M,C,V>, ExecutMoreFun<Id> {

}
