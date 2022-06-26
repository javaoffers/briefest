package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.crud.impl.SelectFunImpl;

/**
 * create by cmj on 2022-06-22 23:37:17
 */
public class CrudMapperMethodExcutor {


    public static SelectFunImpl select(){
        System.out.println(Thread.currentThread().getId());
        return new SelectFunImpl(CrudMapperMethodThreadLocal.getExcutorSelect());
    }




}
