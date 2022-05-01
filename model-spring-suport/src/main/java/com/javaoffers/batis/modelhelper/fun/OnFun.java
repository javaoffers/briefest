package com.javaoffers.batis.modelhelper.fun;

/**
 * @Description: on 拼接sql 条件
 * @Auther: create by cmj on 2022/5/2 00:56
 * sql: selct xx from a left join b on a.col = b.col
 */
public interface OnFun<M1,M2, C, V> extends WhereFun<M2, C, V,  OnFun<M1 , M2 , C,V>> {
    /**
     * 添加等值关系 =
     * @param col
     * @param col2
     * @return
     */
    public OnFun<M1,M2, C, V> oeq(C col, C col2);

    /**
     * 添加不等值关系 !=
     * @param col
     * @param col2
     * @return
     */
    public OnFun<M1,M2, C, V> oueq(C col, C col2);

    /**
     * 大于  >
     * @param col
     * @param col2
     * @return
     */
    public OnFun<M1,M2, C, V> ogt(C col, C col2);

    /**
     * 小于 <
     * @param col
     * @param col2
     * @return
     */
    public OnFun<M1,M2, C, V> olt(C col, C col2);

    /**
     * 大于等于  >=
     * @param col
     * @param col2
     * @return
     */
    public OnFun<M1,M2, C, V> ogtEq(C col, C col2);

    /**
     * 小于等于 <=
     * @param col
     * @param col2
     * @return
     */
    public OnFun<M1,M2, C, V> oltEq(C col, C col2);

    /**
     * on 条件结束。返回 Where
     * @return
     */
    public WhereFun<M1 , C, V, ?> end();


}
