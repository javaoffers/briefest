package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.util.List;

/**
 * 插入功能.
 *
 * @author cmj
 */
public interface InsertFun<M, C extends GetterFun<M, Object>, V> extends OneInsertCol<M,C,V>, MoreInsertColAll<M,C,V> {

}
