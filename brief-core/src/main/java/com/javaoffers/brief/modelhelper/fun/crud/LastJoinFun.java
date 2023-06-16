package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.anno.internal.NotNull;

/**
 * @Description: join 语句
 * @Auther: create by cmj on 2022/5/2 00:42
 */
public interface LastJoinFun<M1,M2, M3, C3 extends GetterFun<M3,Object>, V>{

    /**
     * 添加查询字段
     * @param cols
     * @return
     */
    public LastJoinFun<M1,M2,M3, C3, V> col(@NotNull C3... cols);

    /**
     * 添加查询字段
     * @param cols
     * @return
     */
    public LastJoinFun<M1,M2, M3, C3, V> col(boolean condition, @NotNull C3... cols);

    /**
     * 添加查询字段
     * @param aggTag  统计函数标签
     * @param cols 查询字段
     * @return
     */
    public LastJoinFun<M1,M2, M3, C3, V> col(AggTag aggTag, @NotNull  C3... cols);

    /**
     * 添加查询字段
     * @param condition 如果为true才会有效
     * @param cols
     * @return
     */
    public LastJoinFun<M1,M2, M3, C3, V> col(boolean condition, AggTag aggTag, @NotNull  C3... cols);

    /**
     * 添加查询字段
     * @param aggTag  统计函数标签
     * @param col 查询字段
     * @param asName 别名
     * @return
     */
    public LastJoinFun<M1,M2,M3, C3, V> col(AggTag aggTag, @NotNull C3 col, String asName);

    /**
     * 添加查询字段
     * @param condition 如果为true才会有效
     * @param col 查询字段
     * @param asName 别名
     * @return
     */
    public LastJoinFun<M1,M2,M3, C3, V> col(boolean condition, AggTag aggTag, @NotNull C3 col, String asName);

    /**
     * 添加所有查询字段
     * @return
     */
    public LastJoinFun<M1, M2, M3, C3,V> colAll();

    /**
     * 添加查询字段 或则 子查询sql
     * @param colSql
     * @return
     */
    public LastJoinFun<M1,M2, M3, C3, V> col(@NotNull String... colSql);

    /**
     * sql语句： on 条件
     * @return
     */
    public<C2 extends GetterFun<M2, Object>> LastOnFun<M1,M2, M3, C2, C3, V> on();
}
