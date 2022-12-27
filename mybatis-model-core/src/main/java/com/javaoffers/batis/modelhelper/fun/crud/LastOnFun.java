package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.LeftWhereSelectFunImpl;

import java.io.Serializable;

/**
 * @Description: on 拼接sql 条件
 * @Auther: create by cmj on 2022/5/2 00:56
 * sql: selct xx from a left join b on a.col = b.col
 */
public interface LastOnFun<M1,M2, M3, C2 extends GetterFun<M2, Object> & Serializable, C3 extends GetterFun<M3, Object> & Serializable, V>
        extends WhereFun<M3, C3, V, LastOnFun<M1 , M2 , M3, C2, C3, V>>{
    /**
     * 添加等值关系 =
     * @param col2
     * @param col3
     * @return
     */
    public LastOnFun<M1,M2,M3, C2, C3, V> oeq(C2 col2, C3 col3);

    /**
     * 添加不等值关系 !=
     * @param col2
     * @param col3
     * @return
     */
    public LastOnFun<M1,M2,M3, C2, C3, V> oueq(C2 col2, C3 col3);

    /**
     * 大于  >
     * @param col2
     * @param col3
     * @return
     */
    public LastOnFun<M1,M2, M3, C2, C3, V> ogt(C2 col2, C3 col3);

    /**
     * 小于 <
     * @param col2
     * @param col3
     * @return
     */
    public LastOnFun<M1,M2,M3, C2, C3, V> olt(C2 col2, C3 col3);

    /**
     * 大于等于  >=
     * @param col2
     * @param col3
     * @return
     */
    public LastOnFun<M1,M2,M3, C2, C3, V> ogtEq(C2 col2, C3 col3);

    /**
     * 小于等于 <=
     * @param col2
     * @param col3
     * @return
     */
    public LastOnFun<M1,M2,M3 ,C2, C3, V> oltEq(C2 col2, C3 col3);

    /**
     * on 条件结束。返回 Where
     * @return
     */
    public LeftWhereSelectFunImpl<M1, M2,V> where();

}
