package com.javaoffers.batis.modelhelper.fun.crud;

/**
 * @Description: 查询
 * @Auther: create by cmj on 2022/5/1 23:58
 * M: model 类
 * C: 字段,
 * V: 字段值
 */
public interface SelectFun<M, C , V> {

    /**
     * 添加查询字段
     * @param col
     * @return
     */
    public SelectFun<M, C, V> col(C... col);

    /**
     * 添加查询字段 或则 子查询sql
     * @param colSql
     * @return
     */
    public SelectFun<M, C, V> col(String... colSql);

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
    public <M2> JoinFun<M,M2, C, V> leftJoin(M2 m2);

    /**
     * sql 语句： where
     * @return
     */
    public WhereFun<M, C, V, ? > where();

}
