package com.javaoffers.brief.modelhelper.mapper;

import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.SelectFun;
import com.javaoffers.brief.modelhelper.fun.crud.delete.DeleteFun;
import com.javaoffers.brief.modelhelper.fun.crud.insert.InsertFun;
import com.javaoffers.brief.modelhelper.fun.crud.update.UpdateFun;
import com.javaoffers.brief.modelhelper.fun.general.GeneralFun;

import java.io.Serializable;

/**
 * create by cmj
 *
 * @param <T>
 */
public interface CrudMapper<T> extends BaseMapper<T> {

    /**
     * select sql
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> SelectFun<T, C, V> select();


    /**
     * insert sql
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> InsertFun<T, C, V> insert();


    /**
     * insert sql
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> UpdateFun<T, C, V> update();


    /**
     * insert sql
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> DeleteFun<T, C, V> delete();

    /**
     * general function
     */
    public <C extends GetterFun<T, Object>, V extends Serializable> GeneralFun<T, C, V> general();

}
