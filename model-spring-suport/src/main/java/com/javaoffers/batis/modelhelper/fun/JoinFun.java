package com.javaoffers.batis.modelhelper.fun;

/**
 * @Description: join 语句
 * @Auther: create by cmj on 2022/5/2 00:42
 */
public interface JoinFun<M1,M2, C, V>{

    /**
     * 添加查询字段
     * @param col
     * @return
     */
    public JoinFun<M1,M2, C, V> col(C... col);

    /**
     * 添加查询字段 或则 子查询sql
     * @param colSql
     * @return
     */
    public JoinFun<M1,M2, C, V> col(String... colSql);

    /**
     * sql语句： on 条件
     * @return
     */
    public OnFun<M1,M2, C, V> on();
}
