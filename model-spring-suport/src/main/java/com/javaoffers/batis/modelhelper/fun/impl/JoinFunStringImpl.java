package com.javaoffers.batis.modelhelper.fun.impl;

import com.javaoffers.batis.modelhelper.fun.JoinFun;
import com.javaoffers.batis.modelhelper.fun.OnFun;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: join 功能实现,以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:11
 */
public class JoinFunStringImpl<M1,M2,V> implements JoinFun<M1,M2,String,V> {

    private List<String> joinSelectCol = new LinkedList<>();
    private M1 m;
    private M2 m2;
    private String table2Name;

    public JoinFunStringImpl(M1 m, M2 m2) {
        this.m = m;
        this.m2 = m2;

    }

    /**
     * 添加查询字段
     * @param col
     * @return
     */
    @Override
    public JoinFun<M1, M2, String, V> col(String col) {

        joinSelectCol.add(col);

        return this;
    }

    @Override
    public OnFun<M1, M2, String, V> on() {
        return new OnFunStringImpl<>();
    }
}
