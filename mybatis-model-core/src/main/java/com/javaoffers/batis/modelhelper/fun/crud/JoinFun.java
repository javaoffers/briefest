package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @Description: join 语句
 * @Auther: create by cmj on 2022/5/2 00:42
 */
public interface JoinFun<M1,M2, C2 extends GetterFun<M2,Object>, V>{

    /**
     * 添加查询字段
     * @param col
     * @return
     */
    public JoinFun<M1,M2, C2, V> col(C2... col);

    /**
     * 添加查询字段
     * @param cols
     * @return
     */
    public JoinFun<M1,M2, C2, V> col(boolean condition, C2... cols);

    /**
     * 添加查询字段
     * @param aggTag  统计函数标签
     * @param cols 查询字段
     * @return
     */
    public JoinFun<M1,M2, C2, V>  col(AggTag aggTag, C2... cols);

    /**
     * 添加查询字段
     * @param condition 如果为true才会有效
     * @param cols
     * @return
     */
    public JoinFun<M1,M2, C2, V>  col(boolean condition, AggTag aggTag, C2... cols);

    /**
     * 添加查询字段
     * @param aggTag  统计函数标签
     * @param col 查询字段
     * @param asName 别名
     * @return
     */
    public JoinFun<M1,M2, C2, V>  col(AggTag aggTag, C2 col, String asName);

    /**
     * 添加查询字段
     * @param condition 如果为true才会有效
     * @param col 查询字段
     * @param asName 别名
     * @return
     */
    public JoinFun<M1,M2, C2, V>  col(boolean condition, AggTag aggTag, C2 col, String asName);

    /**
     * 添加所有查询字段
     * @return
     */
    public JoinFun<M1, M2,C2,V> colAll();

    /**
     * 添加查询字段 或则 子查询sql
     * @param colSql
     * @return
     */
    public JoinFun<M1,M2, C2, V> col(String... colSql);

    /**
     * sql语句： on 条件
     * @return
     */
    public<C1 extends GetterFun<M1,Object>> OnFun<M1,M2, C1, C2, V> on();
}
