package com.javaoffers.brief.modelhelper.fun;

/**
 * @Description: 执行sql
 * @Auther: create by cmj on 2022/5/8 20:43
 */
public interface ExecutOneFun<M> {
    /**
     * 执行sql 返回 M.
     * @return
     */
    public M ex();
}
