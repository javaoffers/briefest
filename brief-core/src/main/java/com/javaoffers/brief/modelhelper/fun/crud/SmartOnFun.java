package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.ConstructorFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.LastJoinFunImpl;
import com.javaoffers.brief.modelhelper.fun.crud.impl.LeftWhereSelectFunImpl;

import java.io.Serializable;

/**
 * @Description: on 拼接sql 条件
 * @Auther: create by cmj on 2022/5/2 00:56
 * sql: selct xx from a left join b on a.col = b.col
 */
public interface SmartOnFun<M1,M2,
        C extends GetterFun<M1, Object> & Serializable,
        C2 extends GetterFun<M2, Object> & Serializable,
        V,
        R extends SmartOnFun<M1,M2, C , C2, V,R>>
        extends WhereFun<M2, C2, V, R>{
    /**
     * 添加等值关系 =
     * @param col
     * @param col2
     * @return
     */
    public R oeq(C col, C2 col2);

    /**
     * 添加不等值关系 !=
     * @param col
     * @param col2
     * @return
     */
    public R oueq(C col, C2 col2);

    /**
     * 大于  >
     * @param col
     * @param col2
     * @return
     */
    public R ogt(C col, C2 col2);

    /**
     * 小于 <
     * @param col
     * @param col2
     * @return
     */
    public R olt(C col, C2 col2);

    /**
     * 大于等于  >=
     * @param col
     * @param col2
     * @return
     */
    public R ogtEq(C col, C2 col2);

    /**
     * 小于等于 <=
     * @param col
     * @param col2
     * @return
     */
    public R oltEq(C col, C2 col2);

    /**
     * on 条件结束。返回 Where
     * @return
     */
    public LeftWhereSelectFunImpl<M1, M2, V, ?> where();

    /**
     * left join
     * @param m3 table3
     * @param <M3>
     * @param <C3>
     * @return
     */
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFunImpl<M1,M2, M3, C3, V> leftJoin(ConstructorFun<M3> m3) ;

    /**
     * inner join
     * @param m3 table3
     * @param <M3>
     * @param <C3>
     * @return
     */
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFunImpl<M1,M2, M3, C3, V> innerJoin(ConstructorFun<M3> m3) ;

    /**
     * right join
     * @param m3 table3
     * @param <M3>
     * @param <C3>
     * @return
     */
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFunImpl<M1,M2, M3, C3, V> rightJoin(ConstructorFun<M3> m3) ;

}
