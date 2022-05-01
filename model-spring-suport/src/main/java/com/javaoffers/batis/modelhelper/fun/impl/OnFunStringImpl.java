package com.javaoffers.batis.modelhelper.fun.impl;


import com.javaoffers.batis.modelhelper.fun.OnFun;
import com.javaoffers.batis.modelhelper.fun.WhereFun;

import java.util.LinkedHashMap;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:13
 */
public  class OnFunStringImpl<M1,M2,V> extends WhereFunStringImpl<M2,V,OnFun<M1,M2,String,V>>
        implements OnFun<M1,M2,String,V>
{
    /**
     * K: table1 的字段名称
     * V: table2 的子度名称
     */
    private M1 m1;
    private M2 m2;
    private String table1Name;
    private String table2Name;

    @Override
    public OnFun<M1, M2, String, V> oeq(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> oueq(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> ogt(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> olt(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> ogtEq(String col, String col2) {
        return null;
    }

    @Override
    public OnFun<M1, M2, String, V> oltEq(String col, String col2) {
        return null;
    }

    @Override
    public WhereFun<M1, String, V, ?> end() {
        return null;
    }
}
