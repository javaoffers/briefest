package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.fun.crud.impl.SelectFunImpl;
import com.javaoffers.brief.modelhelper.fun.crud.impl.delete.DeleteFunImpl;
import com.javaoffers.brief.modelhelper.fun.crud.impl.insert.InsertFunImpl;
import com.javaoffers.brief.modelhelper.fun.crud.impl.update.UpdateFunImpl;
import com.javaoffers.brief.modelhelper.fun.general.GeneralFun;
import com.javaoffers.brief.modelhelper.fun.general.impl.GeneralFunImpl;
import com.javaoffers.brief.modelhelper.fun.general.impl.NativeFunImpl;

/**
 * create by cmj on 2022-06-22 23:37:17
 */
public class CrudMapperMethodExcutor {


    public static SelectFunImpl select() {
        return new SelectFunImpl(CrudMapperMethodThreadLocal.getExcutorModel());
    }

    public static InsertFunImpl insert() {
        return new InsertFunImpl(CrudMapperMethodThreadLocal.getExcutorModel());
    }

    public static UpdateFunImpl update() {
        return new UpdateFunImpl(CrudMapperMethodThreadLocal.getExcutorModel());
    }

    public static DeleteFunImpl delete() {
        return new DeleteFunImpl(CrudMapperMethodThreadLocal.getExcutorModel());
    }

    public static GeneralFun general() {
        return new GeneralFunImpl(CrudMapperMethodThreadLocal.getExcutorModel(),
                select(),
                insert(),
                update(),
                delete(), new NativeFunImpl());
    }
}
