package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

public interface SelectFun<M, C extends GetterFun<M, Object>, V>  {

    /**
     * Add query field
     * @param col
     * @return
     */
    public SmartSelectFun<M, C, V> col(C col);

    /**
     * Add query field
     * @param cols
     * @return
     */
    public SmartSelectFun<M, C, V> col(C... cols);

    /**
     * Add query field
     * @param condition 如果为true才会有效
     * @param cols
     * @return
     */
    public SmartSelectFun<M, C, V> col(boolean condition, C... cols);

    /**
     * Add query field
     * @param aggTag  Statistical function labels
     * @param cols query field
     * @return
     */
    public SmartSelectFun<M, C, V> col(AggTag aggTag, C... cols);

    /**
     * Add query field
     * @param condition Only valid if true
     * @param cols
     * @return
     */
    public SmartSelectFun<M, C, V> col(boolean condition, AggTag aggTag, C... cols);

    /**
     * Add query field
     * @param aggTag  Statistical function labels
     * @param col query field
     * @param asName  asName
     * @return
     */
    public SmartSelectFun<M, C, V> col(AggTag aggTag, C col, String asName);

    /**
     * Add query field
     * @param condition Only valid if true
     * @param col query field
     * @param asName alias
     * @return
     */
    public SmartSelectFun<M, C, V> col(boolean condition, AggTag aggTag, C col, String asName);

    /**
     * Add query field or sub query sql
     * @param colSql
     * @return
     */
    public SmartSelectFun<M, C, V> col(String... colSql);

    /**
     * Add query field or sub query sql
     * @param condition Only valid if true
     * @param colSql
     * @return
     */
    public SmartSelectFun<M, C, V> col(boolean condition, String... colSql);

    /**
     * Add all query fields
     * @return
     */
    public SmartSelectFun<M, C, V> colAll();

}
