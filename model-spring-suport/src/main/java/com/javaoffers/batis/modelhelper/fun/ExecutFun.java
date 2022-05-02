package com.javaoffers.batis.modelhelper.fun;

import java.util.List;

/**
 * @Description:  执行sql
 * @Auther: create by cmj on 2022/5/2 23:08
 */
public interface ExecutFun<M> {
    /**
     * 执行sql 返回 M
     * @return
     */
    public M ex();

    /**
     * 执行sql 返回 List<M>
     * @return
     */
    public List<M> exs();
}
