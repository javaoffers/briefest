package com.javaoffers.batis.modelhelper.fun.impl;

import com.javaoffers.batis.modelhelper.fun.JoinFun;
import com.javaoffers.batis.modelhelper.fun.SelectFun;
import com.javaoffers.batis.modelhelper.fun.WhereFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

import java.util.LinkedList;

/**
 * @Description:  以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 01:55
 */
public class SelectFunStringImpl<M,C> implements SelectFun<M,String,C> {

    private M m;
    /**
     * 存放 查询字段
     */
    private LinkedList<String> selectSqlCol = new LinkedList<>();

    @Override
    public SelectFun<M, String, C> col(String col) {
         selectSqlCol.add(col);
        return this;
    }

    @Override
    public SelectFun<M, String, C> colAll() {

        selectSqlCol.addAll(TableHelper.getColAll(m.getClass()));

        return this;
    }

    @Override
    public <M2> JoinFun<M, M2, String, C> leftJoin(M2 m2) {
        return new JoinFunStringImpl<>(m,m2);
    }

    @Override
    public WhereFun<M, String, C, ?> where(String col, C value) {
        return null;
    }
}
