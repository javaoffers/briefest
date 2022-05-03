package com.javaoffers.batis.modelhelper.mapper;

import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.SelectFun;

import java.io.Serializable;

/**
 * create by cmj
 * @param <T>
 */
public interface CrudMapper<T> extends BaseMapper<T> {

    /**
     * select sql
     */
    public <C extends  GetterFun <T, Serializable> &  Serializable,V extends Serializable > SelectFun<T, C , V > select();



}
