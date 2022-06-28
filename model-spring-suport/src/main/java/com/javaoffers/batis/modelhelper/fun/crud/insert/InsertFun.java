package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.util.List;

/**
 * 插入功能.
 *
 * @author cmj
 */
public interface InsertFun<M, C extends GetterFun<M, Object>, V> extends OneInsertFun<M,C,V>, MoreInsertFun<M,C,V> {

}
