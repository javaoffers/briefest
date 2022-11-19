package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.ExecutMoreFun;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.util.List;

/**
 * 多插入.
 * @author create by cmj
 */
public interface MoreInsertFun<M, C extends GetterFun<M, Object>, V> extends MoreInsertColAll<M,C,V>, ExecutMoreFun<Id> {

    /**
     * The update operation is performed when the primary key or unique constraint is repeated.
     * The primary key id will not be returned when the update is triggered
     * @return this
     */
    ExecutMoreFun<Id> dupUpdate();

    /**
     * delete before insert if the primary key or unique constraint is repeated
     * @return this
     */
    ExecutMoreFun<Id> dupReplace();

}
