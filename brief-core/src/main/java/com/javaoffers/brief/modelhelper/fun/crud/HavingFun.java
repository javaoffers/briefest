package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.ExecutFun;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @Description: having Only statistical function conditions are implemented.
 * Common conditions are recommended to be written in where
 * @Auther: create by cmj on 2022/6/5 18:31
 */
public interface HavingFun<M, C, V, R extends HavingFun<M, C, V,?>>
        extends
        LimitFun<M, R>,
        OrderFun<M,C,V, HavingFun<M,C,V,R>>,
        ExecutFun<M> {

    /**
     * 拼接Or, 否者默认都是 and
     * @return
     */
    public R or();

    /**
     * condition 默认为and拼接，生成（a=b and/or b=c ....）
     * 添加首尾括号
     * @param r
     * @return
     */
    public R unite(Consumer<R> r);

    /**
     * condition 默认为and拼接，生成（a=b and/or b=c ....）
     * 添加首尾括号
     * @param r
     * @return
     */
    public R unite(boolean condition, Consumer<R> r);

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






    /**
     * 添加等值关系 =
     *
     * @param col
     * @param value
     * @return
     */
    public R eq( C col, V value);

    /**
     * 添加等值关系 =
     *
     * @param col
     * @param value
     * @return
     */
    public R eq(boolean condition,  C col, V value);

    /**
     * 添加不等值关系 !=
     *
     * @param col
     * @param value
     * @return
     */
    public R ueq( C col, V value);

    /**
     * 添加不等值关系 !=
     *
     * @param col
     * @param value
     * @return
     */
    public R ueq(boolean condition,  C col, V value);

    /**
     * 大于  >
     *
     * @param col
     * @param value
     * @return
     */
    public R gt( C col, V value);

    /**
     * 大于  >
     *
     * @param col
     * @param value
     * @return
     */
    public R gt(boolean condition,  C col, V value);

    /**
     * 小于 <
     *
     * @param col
     * @param value
     * @return
     */
    public R lt( C col, V value);

    /**
     * 小于 <
     *
     * @param col
     * @param value
     * @return
     */
    public R lt(boolean condition,  C col, V value);

    /**
     * 大于等于  >=
     *
     * @param col
     * @param value
     * @return
     */
    public R gtEq( C col, V value);

    /**
     * 大于等于  >=
     *
     * @param col
     * @param value
     * @return
     */
    public R gtEq(boolean condition,  C col, V value);

    /**
     * 小于等于 <=
     *
     * @param col
     * @param value
     * @return
     */
    public R ltEq( C col, V value);

    /**
     * 小于等于 <=
     *
     * @param col
     * @param value
     * @return
     */
    public R ltEq(boolean condition,  C col, V value);

    /**
     * sql 范围 : between
     *
     * @param col
     * @param start
     * @return
     * @Param end
     */
    public R between( C col, V start, V end);

    /**
     * sql 范围 : between
     *
     * @param col
     * @param start
     * @return
     * @Param end
     */
    public R between(boolean condition,  C col, V start, V end);

    /**
     * sql 范围 : between
     *
     * @param col
     * @param start
     * @return
     * @Param end
     */
    public R notBetween( C col, V start, V end);

    /**
     * sql 范围 : between
     *
     * @param col
     * @param start
     * @return
     * @Param end
     */
    public R notBetween(boolean condition,  C col, V start, V end);


    /**
     * sql 模糊： like ? . V ： “%xxx%”
     *
     * @param col
     * @param value
     * @return
     */
    public R like( C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx%”
     *
     * @param col
     * @param value
     * @return
     */
    public R like(boolean condition,  C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx”
     *
     * @param col
     * @param value
     * @return
     */
    public R likeLeft( C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx”
     *
     * @param col
     * @param value
     * @return
     */
    public R likeLeft(boolean condition,  C col, V value);

    /**
     * sql 模糊： like ? . V ： “xxx%”
     *
     * @param col
     * @param value
     * @return
     */
    public R likeRight( C col, V value);

    /**
     * sql 模糊： like ? . V ： “xxx%”
     *
     * @param col
     * @param value
     * @return
     */
    public R likeRight(boolean condition,  C col, V value);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R in( C col, V... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R in(boolean condition,  C col, V... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R in( C col, Collection... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R in(boolean condition,  C col, Collection... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R notIn( C col, V... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R notIn(boolean condition,  C col, V... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R notIn( C col, Collection... values);

    /**
     * sql语句  in
     *
     * @param col
     * @param values
     * @return
     */
    public R notIn(boolean condition,  C col, Collection... values);
}
