package com.javaoffers.brief.modelhelper.fun.crud.insert;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.fun.ExecutOneFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;

/**
 * 单插入.
 * @author create by cmj
 */
public interface OneInsertFun <M, C extends GetterFun<M, Object>, V> extends OneInsertCol<M,C,V>, ExecutOneFun<Id> {

    /**
     * The update operation is performed when the primary key or unique constraint is repeated.
     * The primary key id will not be returned when the update is triggered
     * @return this
     */
    ExecutOneFun<Id> dupUpdate();

    /**
     * delete before insert if the primary key or unique constraint is repeated
     * @return this
     */
    ExecutOneFun<Id> dupReplace();

}
