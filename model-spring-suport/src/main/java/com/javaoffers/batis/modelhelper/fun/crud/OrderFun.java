package com.javaoffers.batis.modelhelper.fun.crud;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/19 02:44
 */
public interface OrderFun <M,C, V, R extends OrderFun<M,C,V,R>> {

    /**
     * 根据某些字段排序 ASC
     * @param cs
     * @return
     */
    public R orderA(C... cs);

    /**
     * 根据某些字段排序 ASC
     * @param cs
     * @return
     */
    public R orderA(boolean condition, C... cs);

    /**
     * 根据某些字段排序 DESC
     * @param cs
     * @return
     */
    public R orderD(C... cs);

    /**
     * 根据某些字段排序 DESC
     * @param cs
     * @return
     */
    public R orderD(boolean condition, C... cs);

}
