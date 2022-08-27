package com.javaoffers.batis.modelhelper.fun;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.core.ConditionParse;
import com.javaoffers.batis.modelhelper.core.SQLInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 执行sql 返回列表
 * @Auther: create by cmj on 2022/5/8 20:44
 */
public interface ExecutMoreFun<M> {

    /**
     * 执行sql 返回 List<M>
     * @return
     */
    public List<M> exs();
}
