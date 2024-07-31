package com.javaoffers.brief.modelhelper.fun.crud;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Description: sql where Statement
 * @Auther: create by cmj on 2022/5/2 00:08
 */
public interface WhereFun<M,C, V, R >   {

    /**
     * Splicing Or, otherwise it is and by default
     * @return
     */
    public R or();

    /**
     * condition defaults to and splicing, generating (a=b and/or b=c ....)
     * Add opening and closing brackets
     * @param r
     * @return
     */
    public R unite(Consumer<R> r);

    /**
     * condition defaults to and splicing, generating (a=b and/or b=c ....）
     * 添加首尾括号
     * @param r
     * @return
     */
    public R unite(boolean condition, Consumer<R> r);

    /**
     * The condition defaults to and splicing, which can write native SQL statements.
     *
     * @param sql sql
     * @return  r
     */
    public R condSQL(String sql);

    /**
     * The condition defaults to and splicing, which can write native SQL statements.
     *
     * @param sql
     * @return r
     */
    public R condSQL(boolean condition, String sql);


    /**
     * The condition defaults to and splicing, which can write native SQL statements.
     * The parameters in the sql statement only support expressions like #{colName}
     * @param sql sql
     * @return  r
     */
    public R condSQL(String sql, Map<String, Object> params);

    /**
     * The condition defaults to and splicing, which can write native SQL statements.
     * The parameters in the sql statement only support the expression #{colName}
     * @param sql
     * @return r
     */
    public R condSQL(boolean condition, String sql, Map<String, Object> params);

    /**
     * add equivalence =
     * @param col
     * @param value
     * @return
     */
    public R eq(C col, V value);

    /**
     * add equivalence =
     * @param col
     * @param value
     * @return
     */
    public R eq(boolean condition, C col, V value);

    /**
     * Add unequal relationship !=
     * @param col
     * @param value
     * @return
     */
    public R ueq(C col, V value);

    /**
     * Add unequal relationship !=
     * @param col
     * @param value
     * @return
     */
    public R ueq(boolean condition, C col, V value);

    /**
     * more than the  >
     * @param col
     * @param value
     * @return
     */
    public R gt(C col, V value);

    /**
     * more than the  >
     * @param col
     * @param value
     * @return
     */
    public R gt(boolean condition, C col, V value);

    /**
     * less than <
     * @param col
     * @param value
     * @return
     */
    public R lt(C col, V value);

    /**
     * less than <
     * @param col
     * @param value
     * @return
     */
    public R lt(boolean condition, C col, V value);

    /**
     * greater or equal to  >=
     * @param col
     * @param value
     * @return
     */
    public R gtEq(C col, V value);

    /**
     * greater or equal to  >=
     * @param col
     * @param value
     * @return
     */
    public R gtEq(boolean condition, C col, V value);

    /**
     * less than or equal to <=
     * @param col
     * @param value
     * @return
     */
    public R ltEq(C col, V value);

    /**
     * less than or equal to <=
     * @param col
     * @param value
     * @return
     */
    public R ltEq(boolean condition, C col, V value);

    /**
     * sql grammar : between
     * @param col
     * @param start
     * @Param end
     * @return
     */
    public R between(C col, V start,  V end);

    /**
     * sql grammar : between
     * @param col
     * @param start
     * @Param end
     * @return
     */
    public R between(boolean condition, C col, V start,  V end);

    /**
     * sql grammar : between
     * @param col
     * @param start
     * @Param end
     * @return
     */
    public R notBetween(C col, V start,  V end);

    /**
     * sql grammar : between
     * @param col
     * @param start
     * @Param end
     * @return
     */
    public R notBetween(boolean condition, C col, V start,  V end);


    /**
     * sql grammar： like ? . V ： “%xxx%”
     * @param col
     * @param value
     * @return
     */
    public R like(C col, V value);

    /**
     * sql grammar： like ? . V ： “%xxx%”
     * @param col
     * @param value
     * @return
     */
    public R like(boolean condition, C col, V value);

    /**
     * sql grammar： like ? . V ： “%xxx”
     * @param col
     * @param value
     * @return
     */
    public R likeLeft(C col, V value);

    /**
     * sql grammar： like ? . V ： “%xxx”
     * @param col
     * @param value
     * @return
     */
    public R likeLeft(boolean condition, C col, V value);

    /**
     * sql grammar： like ? . V ： “xxx%”
     * @param col
     * @param value
     * @return
     */
    public R likeRight(C col, V value);

    /**
     * sql grammar： like ? . V ： “xxx%”
     * @param col
     * @param value
     * @return
     */
    public R likeRight(boolean condition, C col, V value);

    /**
     * sql grammar in
     * @param col
     * @param values
     * @return
     */
    public R in(C col, V... values);

    /**
     * sql语句  in
     * @param col
     * @param values
     * @return
     */
    public R in(boolean condition, C col, V... values);

    /**
     * sql语句  in
     * @param col
     * @param values
     * @return
     */
    public R in(C col, Collection... values);

    /**
     * sql语句  in
     * @param col
     * @param values
     * @return
     */
    public R in(boolean condition, C col, Collection... values);

    /**
     * sql语句  in
     * @param col
     * @param values
     * @return
     */
    public R notIn(C col, V... values);

    /**
     * sql语句  in
     * @param col
     * @param values
     * @return
     */
    public R notIn(boolean condition, C col, V... values);

    /**
     * sql语句  in
     * @param col
     * @param values
     * @return
     */
    public R notIn(C col, Collection... values);

    /**
     * sql语句  in
     * @param col
     * @param values
     * @return
     */
    public R notIn(boolean condition, C col, Collection... values);

    /**
     * is null
     * @return this
     */
    public R isNull(C... cols);

    /**
     * is null
     * @param condition
     * @param cols
     * @return
     */
    public R isNull(boolean condition, C... cols);

    /**
     * is null
     * @return this
     */
    public R isNotNull(C... cols);

    /**
     * is null
     * @param condition
     * @param cols
     * @return
     */
    public R isNotNull(boolean condition, C... cols);


    /**
     * where exists（）Judge sentences
     * @param existsSql
     * @return
     */
    public R exists(C... existsSql);

    /**
     * where exists（）Judge sentences
     * @param existsSql
     * @return
     */
    public R exists(boolean condition, C... existsSql);

}
