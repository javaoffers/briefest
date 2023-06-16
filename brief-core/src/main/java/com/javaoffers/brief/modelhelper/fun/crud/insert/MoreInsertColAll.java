package com.javaoffers.brief.modelhelper.fun.crud.insert;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

import java.util.Collection;

public interface MoreInsertColAll<M, C extends GetterFun<M, Object>, V> {
    /**
     * Query all the non-null values ​​in model. (Automatically match database fields for insertion)
     *
     * @param model 值
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(M model);

    /**
     * Query all the non-null values ​​in model. (Automatically match database fields for insertion)
     *
     * @param condition if true then insert
     * @param model value
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(boolean condition, M model);

    /**
     * Query all the non-null values ​​in model. (Automatically match database fields for insertion)
     *
     * @param models model
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(M... models);

    /**
     * Query all the non-null values ​​in model. (Automatically match database fields for insertion)
     *
     * @param models model
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(boolean condition, M... models);

    /**
     * Query all the non-null values ​​in model. (Automatically match database fields for insertion)
     *
     * @param models 值
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(Collection<M> models);

    /**
     * Query all the non-null values ​​in model. (Automatically match database fields for insertion)
     * @param condition if true then insert
     * @param models model
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(boolean condition, Collection<M> models);

}
