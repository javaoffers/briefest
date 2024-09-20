package com.javaoffers.brief.modelhelper.mapper;

import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.SelectFun;
import com.javaoffers.brief.modelhelper.fun.crud.delete.DeleteFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.SelectFunImpl;
import com.javaoffers.brief.modelhelper.fun.crud.impl.delete.DeleteFunImpl;
import com.javaoffers.brief.modelhelper.fun.crud.impl.insert.InsertFunImpl;
import com.javaoffers.brief.modelhelper.fun.crud.insert.InsertFun;
import com.javaoffers.brief.modelhelper.fun.crud.update.UpdateFun;
import com.javaoffers.brief.modelhelper.fun.general.GeneralFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.update.UpdateFunImpl;
import com.javaoffers.brief.modelhelper.fun.general.impl.GeneralFunImpl;

import java.io.Serializable;

/**
 * create by cmj
 *
 * @param <T>
 */
public interface BriefMapper<T> extends BaseMapper<T> {

    /**
     * select sql
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> SelectFunImpl<T> select();


    /**
     * insert sql
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> InsertFunImpl<T> insert();


    /**
     * insert sql
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> UpdateFunImpl<T, C, V> update();


    /**
     * insert sql
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> DeleteFunImpl<T> delete();

    /**
     * general function
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> GeneralFunImpl<T, C, V> general();

}
