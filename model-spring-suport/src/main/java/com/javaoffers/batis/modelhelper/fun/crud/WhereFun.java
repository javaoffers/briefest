package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ExecutFun;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Description: sql where 语句
 * @Auther: create by cmj on 2022/5/2 00:08
 */
public interface WhereFun<M,C, V, R extends WhereFun<M,C,V,R>>   {

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
     * condition 默认为and拼接， 可以写原生的sql语句。
     *
     * @param sql sql
     * @return  r
     */
    public R condSQL(String sql);

    /**
     * condition 默认为and拼接，可以写原生的sql语句。
     *
     * @param sql
     * @return r
     */
    public R condSQL(boolean condition, String sql);


    /**
     * condition 默认为and拼接， 可以写原生的sql语句。
     * sql 语句中的参数只支持 #{colName} 这种表达式
     * @param sql sql
     * @return  r
     */
    public R condSQL(String sql, Map<String, Object> params);

    /**
     * condition 默认为and拼接，可以写原生的sql语句。
     * sql 语句中的参数只支持 #{colName} 这种表达式
     * @param sql
     * @return r
     */
    public R condSQL(boolean condition, String sql, Map<String, Object> params);

    /**
     * 添加等值关系 =
     * @param col
     * @param value
     * @return
     */
    public R eq(C col, V value);

    /**
     * 添加等值关系 =
     * @param col
     * @param value
     * @return
     */
    public R eq(boolean condition, C col, V value);

    /**
     * 添加不等值关系 !=
     * @param col
     * @param value
     * @return
     */
    public R ueq(C col, V value);

    /**
     * 添加不等值关系 !=
     * @param col
     * @param value
     * @return
     */
    public R ueq(boolean condition, C col, V value);

    /**
     * 大于  >
     * @param col
     * @param value
     * @return
     */
    public R gt(C col, V value);

    /**
     * 大于  >
     * @param col
     * @param value
     * @return
     */
    public R gt(boolean condition, C col, V value);

    /**
     * 小于 <
     * @param col
     * @param value
     * @return
     */
    public R lt(C col, V value);

    /**
     * 小于 <
     * @param col
     * @param value
     * @return
     */
    public R lt(boolean condition, C col, V value);

    /**
     * 大于等于  >=
     * @param col
     * @param value
     * @return
     */
    public R gtEq(C col, V value);

    /**
     * 大于等于  >=
     * @param col
     * @param value
     * @return
     */
    public R gtEq(boolean condition, C col, V value);

    /**
     * 小于等于 <=
     * @param col
     * @param value
     * @return
     */
    public R ltEq(C col, V value);

    /**
     * 小于等于 <=
     * @param col
     * @param value
     * @return
     */
    public R ltEq(boolean condition, C col, V value);

    /**
     * sql 范围 : between
     * @param col
     * @param start
     * @Param end
     * @return
     */
    public R between(C col, V start,  V end);

    /**
     * sql 范围 : between
     * @param col
     * @param start
     * @Param end
     * @return
     */
    public R between(boolean condition, C col, V start,  V end);

    /**
     * sql 范围 : between
     * @param col
     * @param start
     * @Param end
     * @return
     */
    public R notBetween(C col, V start,  V end);

    /**
     * sql 范围 : between
     * @param col
     * @param start
     * @Param end
     * @return
     */
    public R notBetween(boolean condition, C col, V start,  V end);


    /**
     * sql 模糊： like ? . V ： “%xxx%”
     * @param col
     * @param value
     * @return
     */
    public R like(C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx%”
     * @param col
     * @param value
     * @return
     */
    public R like(boolean condition, C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx”
     * @param col
     * @param value
     * @return
     */
    public R likeLeft(C col, V value);

    /**
     * sql 模糊： like ? . V ： “%xxx”
     * @param col
     * @param value
     * @return
     */
    public R likeLeft(boolean condition, C col, V value);

    /**
     * sql 模糊： like ? . V ： “xxx%”
     * @param col
     * @param value
     * @return
     */
    public R likeRight(C col, V value);

    /**
     * sql 模糊： like ? . V ： “xxx%”
     * @param col
     * @param value
     * @return
     */
    public R likeRight(boolean condition, C col, V value);

    /**
     * sql语句  in
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
     * where exists（）判断语句
     * @param existsSql
     * @return
     */
    public R exists(String existsSql);

    /**
     * where exists（）判断语句
     * @param existsSql
     * @return
     */
    public R exists(boolean condition, String existsSql);

}
