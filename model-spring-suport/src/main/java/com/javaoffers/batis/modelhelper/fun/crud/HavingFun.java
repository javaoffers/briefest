package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.ExecutFun;

import java.util.Collection;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 18:31
 */
public interface HavingFun<M, C, V, R extends HavingFun> extends WhereFun<M, C, V, HavingFun<M, C, V, ?>>, ExecutFun<M> {

    /**
     * 添加等值关系 =
     *
     * @param col
     * @param value
     * @return
     */
    public R eq(AggTag aggTag, C col, V value);

    /**
     * 添加等值关系 =
     *
     * @param col
     * @param value
     * @return
     */
    public R eq(boolean condition, AggTag aggTag, C col, V value);

    /**
     * 添加不等值关系 !=
     *
     * @param col
     * @param value
     * @return
     */
    public R ueq(AggTag aggTag, C col, V value);

    /**
     * 添加不等值关系 !=
     *
     * @param col
     * @param value
     * @return
     */
    public R ueq(boolean condition, AggTag aggTag, C col, V value);

    /**
     * 大于  >
     *
     * @param col
     * @param value
     * @return
     */
    public R gt(AggTag aggTag, C col, V value);

    /**
     * 大于  >
     *
     * @param col
     * @param value
     * @return
     */
    public R gt(boolean condition, AggTag aggTag, C col, V value);

    /**
     * 小于 <
     *
     * @param col
     * @param value
     * @return
     */
    public R lt(AggTag aggTag, C col, V value);

    /**
     * 小于 <
     *
     * @param col
     * @param value
     * @return
     */
    public R lt(boolean condition, AggTag aggTag, C col, V value);

    /**
     * 大于等于  >=
     *
     * @param col
     * @param value
     * @return
     */
    public R gtEq(AggTag aggTag, C col, V value);

    /**
     * 大于等于  >=
     *
     * @param col
     * @param value
     * @return
     */
    public R gtEq(boolean condition, AggTag aggTag, C col, V value);

    /**
     * 小于等于 <=
     *
     * @param col
     * @param value
     * @return
     */
    public R ltEq(AggTag aggTag, C col, V value);

    /**
     * 小于等于 <=
     *
     * @param col
     * @param value
     * @return
     */
    public R ltEq(boolean condition, AggTag aggTag, C col, V value);

    /**
     * sql 范围 : between
     *
     * @param col
     * @param start
     * @return
     * @Param end
     */
    public R between(AggTag aggTag, C col, V start, V end);

    /**
     * sql 范围 : between
     *
     * @param col
     * @param start
     * @return
     * @Param end
     */
    public R between(boolean condition, AggTag aggTag, C col, V start, V end);

    /**
     * sql 范围 : between
     *
     * @param col
     * @param start
     * @return
     * @Param end
     */
    public R notBetween(AggTag aggTag, C col, V start, V end);

    /**
     * sql 范围 : between
     *
     * @param col
     * @param start
     * @return
     * @Param end
     */
    public R notBetween(boolean condition, AggTag aggTag, C col, V start, V end);


    /**
     * sql 模糊： like ? . V ： “%xxx%”
     *
     * @param col
     * @param value
     * @return
     */
    public R like(AggTag aggTag, C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx%”
     *
     * @param col
     * @param value
     * @return
     */
    public R like(boolean condition, AggTag aggTag, C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx”
     *
     * @param col
     * @param value
     * @return
     */
    public R likeLeft(AggTag aggTag, C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx”
     *
     * @param col
     * @param value
     * @return
     */
    public R likeLeft(boolean condition, AggTag aggTag, C col, V value);

    /**
     * sql 模糊： like ? . V ： “xxx%”
     *
     * @param col
     * @param value
     * @return
     */
    public R likeRight(AggTag aggTag, C col, V value);

    /**
     * sql 模糊： like ? . V ： “xxx%”
     *
     * @param col
     * @param value
     * @return
     */
    public R likeRight(boolean condition, AggTag aggTag, C col, V value);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R in(AggTag aggTag, C col, V... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R in(boolean condition, AggTag aggTag, C col, V... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R in(AggTag aggTag, C col, Collection... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R in(boolean condition, AggTag aggTag, C col, Collection... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R notIn(AggTag aggTag, C col, V... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R notIn(boolean condition, AggTag aggTag, C col, V... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R notIn(AggTag aggTag, C col, Collection... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R notIn(boolean condition, AggTag aggTag, C col, Collection... values);


}
