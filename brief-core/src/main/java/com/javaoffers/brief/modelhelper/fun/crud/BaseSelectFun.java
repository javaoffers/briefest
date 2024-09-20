package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.anno.internal.NotNull;
import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;

public interface BaseSelectFun<M, C extends GetterFun<M, Object>, V, R>  {

    /**
     * Add query field
     * @param col
     * @return
     */
    public R col(@NotNull C col);

    /**
     * Add query field
     * @param cols
     * @return
     */
    public R col(@NotNull C... cols);

    /**
     * Add query field
     * @param condition 如果为true才会有效
     * @param cols
     * @return
     */
    public R col(boolean condition, @NotNull C... cols);

    /**
     * Add query field
     * @param aggTag  Statistical function labels
     * @param cols query field
     * @return
     */
    public R col(AggTag aggTag, @NotNull C... cols);

    /**
     * Add query field
     * @param condition Only valid if true
     * @param cols
     * @return
     */
    public R col(boolean condition, AggTag aggTag, @NotNull C... cols);

    /**
     * Add query field
     * @param aggTag  Statistical function labels
     * @param col query field
     * @param asName  asName
     * @return
     */
    public R col(AggTag aggTag, @NotNull C col, String asName);

    /**
     * Add query field
     * @param condition Only valid if true
     * @param col query field
     * @param asName alias
     * @return
     */
    public R col(boolean condition, AggTag aggTag, @NotNull C col, String asName);

    /**
     * Add query field or sub query sql
     * @param colSql
     * @return
     */
    public R col(@NotNull String... colSql);

    /**
     * Add query field or sub query sql
     * @param condition Only valid if true
     * @param colSql
     * @return
     */
    public R col(boolean condition, @NotNull String... colSql);

    /**
     * Add all query fields
     * @return
     */
    public R colAll();

}
