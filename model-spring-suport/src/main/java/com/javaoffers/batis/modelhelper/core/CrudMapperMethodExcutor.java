package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.crud.impl.SelectFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.insert.InsertFunImpl;

/**
 * create by cmj on 2022-06-22 23:37:17
 */
public class CrudMapperMethodExcutor {


    public static SelectFunImpl select(){
        return new SelectFunImpl(CrudMapperMethodThreadLocal.getExcutorModel());
    }

    public static InsertFunImpl insert(){
        return new InsertFunImpl(CrudMapperMethodThreadLocal.getExcutorModel());
    }

}
