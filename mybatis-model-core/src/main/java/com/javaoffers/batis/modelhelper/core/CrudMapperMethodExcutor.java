package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.crud.impl.SelectFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.delete.DeleteFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.insert.InsertFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.update.UpdateFunImpl;
import com.javaoffers.batis.modelhelper.fun.general.GeneralFun;
import com.javaoffers.batis.modelhelper.fun.general.impl.GeneralFunImpl;

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

    public static UpdateFunImpl update(){
        return new UpdateFunImpl(CrudMapperMethodThreadLocal.getExcutorModel());
    }

    public static DeleteFunImpl delete(){
        return new DeleteFunImpl(CrudMapperMethodThreadLocal.getExcutorModel());
    }

    public static GeneralFun general(){
        Class mClass = CrudMapperMethodThreadLocal.getExcutorModel();
        GeneralFunImpl generalFun = new GeneralFunImpl(
                mClass,select(),insert(), update(),  delete());
        return generalFun;
    }
}
