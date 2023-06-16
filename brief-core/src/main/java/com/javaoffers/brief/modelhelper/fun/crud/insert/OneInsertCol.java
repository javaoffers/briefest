package com.javaoffers.brief.modelhelper.fun.crud.insert;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

public interface OneInsertCol <M, C extends GetterFun<M, Object>, V>  {
    /**
     * Specify a specific field to insert
     *
     * @param col   field
     * @param value value
     * @return this
     */
    OneInsertFun<M, C, V> col(C col, V value);

    /**
     * Specify a specific field to insert
     *
     * @param condition if true then insert
     * @param col       field
     * @param value     value
     * @return this
     */
    OneInsertFun<M, C, V> col(boolean condition, C col, V value);
}
