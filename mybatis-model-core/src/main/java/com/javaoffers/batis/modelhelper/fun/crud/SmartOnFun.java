package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ConstructorFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.LeftWhereSelectFunImpl;

import java.io.Serializable;

/**
 * @Description: on 拼接sql 条件
 * @Auther: create by cmj on 2022/5/2 00:56
 * sql: selct xx from a left join b on a.col = b.col
 */
public interface SmartOnFun<M1,M2, C extends GetterFun<M1, Object> & Serializable, C2 extends GetterFun<M2, Object> & Serializable, V>
        extends WhereFun<M2, C2, V, SmartOnFun<M1 , M2 , C,C2, V>>{
    /**
     * 添加等值关系 =
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V> oeq(C col, C2 col2);

    /**
     * 添加不等值关系 !=
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V> oueq(C col, C2 col2);

    /**
     * 大于  >
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V> ogt(C col, C2 col2);

    /**
     * 小于 <
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V> olt(C col, C2 col2);

    /**
     * 大于等于  >=
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V> ogtEq(C col, C2 col2);

    /**
     * 小于等于 <=
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V> oltEq(C col, C2 col2);

    /**
     * on 条件结束。返回 Where
     * @return
     */
    public LeftWhereSelectFunImpl<M1, M2,V> where();

    /**
     * left join
     * @param m3 table3
     * @param <M3>
     * @param <C3>
     * @return
     */
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFun<M1,M2, M3, C3, V> leftJoin(ConstructorFun<M3> m3) ;

    /**
     * inner join
     * @param m3 table3
     * @param <M3>
     * @param <C3>
     * @return
     */
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFun<M1,M2, M3, C3, V> innerJoin(ConstructorFun<M3> m3) ;

    /**
     * right join
     * @param m3 table3
     * @param <M3>
     * @param <C3>
     * @return
     */
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFun<M1,M2, M3, C3, V> rightJoin(ConstructorFun<M3> m3) ;

}
