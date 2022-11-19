package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.util.List;

public interface SmartMoreInsertFun<M, C extends GetterFun<M, Object>, V> extends MoreInsertColAll<M,C,V>, ExecutOneFun<Id> {

    /**
     * The update operation is performed when the primary key or unique constraint is repeated
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
