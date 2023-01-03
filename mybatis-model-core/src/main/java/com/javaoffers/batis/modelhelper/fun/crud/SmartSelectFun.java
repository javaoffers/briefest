package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @Description: 查询
 * @Auther: create by cmj on 2022/5/1 23:58
 * M: model 类
 * C: 字段,
 * V: 字段值
 */
public interface SmartSelectFun<M, C extends GetterFun<M,Object> , V>
        extends BaseSelectFun<M,C,V>, ToJoinFun<M,C,V> {
    /**
     * sql 语句： where
     * @return
     */
    public WhereSelectFun<M,V> where();

}
