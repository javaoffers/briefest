package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.ConstructorFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereSelectFunImpl;

/**
 * @Description: 查询
 * @Auther: create by cmj on 2022/5/1 23:58
 * M: model 类
 * C: 字段,
 * V: 字段值
 */
public interface SelectFun<M, C extends GetterFun<M,Object> , V> {

    /**
     * 去重复关键字 distinct
     * @return this
     */
    public SelectFun<M,C,V> distinct();

    /**
     * 添加查询字段
     * @param cols
     * @return
     */
    public SelectFun<M, C, V> col(C... cols);

    /**
     * 添加查询字段
     * @param condition 如果为true才会有效
     * @param cols
     * @return
     */
    public SelectFun<M, C, V> col(boolean condition, C... cols);

    /**
     * 添加查询字段
     * @param aggTag  统计函数标签
     * @param cols 查询字段
     * @return
     */
    public SelectFun<M, C, V> col(AggTag aggTag, C... cols);

    /**
     * 添加查询字段
     * @param condition 如果为true才会有效
     * @param cols
     * @return
     */
    public SelectFun<M, C, V> col(boolean condition, AggTag aggTag, C... cols);

    /**
     * 添加查询字段
     * @param aggTag  统计函数标签
     * @param col 查询字段
     * @param asName 别名
     * @return
     */
    public SelectFun<M, C, V>  col(AggTag aggTag, C col, String asName);

    /**
     * 添加查询字段
     * @param condition 如果为true才会有效
     * @param col 查询字段
     * @param asName 别名
     * @return
     */
    public SelectFun<M, C, V>  col(boolean condition, AggTag aggTag, C col, String asName);

    /**
     * 添加查询字段 或则 子查询sql
     * @param colSql
     * @return
     */
    public SelectFun<M, C, V> col(String... colSql);

    /**
     * 添加查询字段 或则 子查询sql
     * @param condition 如果为true才会有效
     * @param colSql
     * @return
     */
    public SelectFun<M, C, V> col(boolean condition,String... colSql);

    /**
     * 添加所有查询字段
     * @return
     */
    public SelectFun<M, C, V> colAll();

    /**
     * sql 语句： left join
     * @param m2 model类( left join m2)
     * @return
     */
    public <M2 , C2 extends GetterFun<M2,Object>> JoinFun<M,M2, C2, V> leftJoin(ConstructorFun<M2> m2);

    /**
     * sql 语句： left join
     * @param m2 model类( left join m2)
     * @return
     */
    public <M2 , C2 extends GetterFun<M2,Object>> JoinFun<M,M2, C2, V> innerJoin(ConstructorFun<M2> m2);

    /**
     * sql 语句： left join
     * @param m2 model类( left join m2)
     * @return
     */
    public <M2 , C2 extends GetterFun<M2,Object>> JoinFun<M,M2, C2, V> rightJoin(ConstructorFun<M2> m2);

    /**
     * sql 语句： where
     * @return
     */
    public WhereSelectFun<M,V> where();

}
