package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.ConstructorFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.JoinFunImpl;

/**
 * @author mingJie
 */
public interface ToJoinFun <M, C extends GetterFun<M,Object> , V> {
    /**
     * sql 语句： left join
     * @param m2 model类( left join m2)
     * @return
     */
    public <M2 , C2 extends GetterFun<M2,Object>> JoinFunImpl<M,M2, V> leftJoin(ConstructorFun<M2> m2);

    /**
     * sql 语句： left join
     * @param m2 model类( left join m2)
     * @return
     */
    public <M2 , C2 extends GetterFun<M2,Object>> JoinFunImpl<M,M2, V> innerJoin(ConstructorFun<M2> m2);

    /**
     * sql 语句： left join
     * @param m2 model类( left join m2)
     * @return
     */
    public <M2 , C2 extends GetterFun<M2,Object>> JoinFunImpl<M,M2, V> rightJoin(ConstructorFun<M2> m2);
}
