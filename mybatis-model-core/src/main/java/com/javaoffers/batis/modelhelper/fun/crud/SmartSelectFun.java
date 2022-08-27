package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ConstructorFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @Description: 查询
 * @Auther: create by cmj on 2022/5/1 23:58
 * M: model 类
 * C: 字段,
 * V: 字段值
 */
public interface SmartSelectFun<M, C extends GetterFun<M,Object> , V> extends SelectFun<M,C,V> {

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
