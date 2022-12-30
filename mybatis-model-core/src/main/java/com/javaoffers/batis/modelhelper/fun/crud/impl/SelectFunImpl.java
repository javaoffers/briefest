package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.*;
import com.javaoffers.batis.modelhelper.fun.condition.select.SelectTableCondition;
import com.javaoffers.batis.modelhelper.fun.condition.where.LFCondition;
import com.javaoffers.batis.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.batis.modelhelper.fun.crud.SelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.SmartSelectFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

/**
 * @Description:  以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 01:55
 */
public class SelectFunImpl<M> implements SelectFun<M,GetterFun<M,Object>,Object> {

    private Class<M> mClass;
    /**
     * 存放 查询字段
     */
    private LinkedConditions<Condition> conditions = new LinkedConditions<>();


    private SmartSelectFunImpl<M,GetterFun<M,Object>,Object> smartSelectFun ;

    public SelectFunImpl(Class<M> mClass) {
        this.conditions.add(new HeadCondition(CrudMapperMethodThreadLocal.getExcutorJdbcTemplate()));
        this.conditions.add(new SelectTableCondition(TableHelper.getTableName(mClass), mClass));
        this.mClass = mClass;
        this.smartSelectFun = new SmartSelectFunImpl(mClass, conditions);
    }

    /**
     *  添加查询字段
     * @param col
     * @return
     */
    @Override
    public SmartSelectFun<M, GetterFun<M,Object>, Object> col(GetterFun col) {
        this.smartSelectFun.col(col);
        return this.smartSelectFun;
    }

    /**
     *  添加查询字段
     * @param cols
     * @return
     */
    @Override
    public SmartSelectFun<M, GetterFun<M,Object>, Object> col(GetterFun... cols) {
        this.smartSelectFun.col(cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, GetterFun<M, Object>... cols) {
        this.smartSelectFun.col(condition, cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(AggTag aggTag, GetterFun<M, Object>... cols) {

        this.smartSelectFun.col(aggTag,cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, AggTag aggTag, GetterFun<M, Object>... cols) {
        this. smartSelectFun.col(condition, aggTag, cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(AggTag aggTag, GetterFun<M, Object> col, String asName) {
        this.smartSelectFun.col(aggTag, col, asName);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, AggTag aggTag, GetterFun<M, Object> col, String asName) {
        this.smartSelectFun.col(condition,aggTag,col,asName);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M,Object>, Object> col(String... colSql) {
        this.smartSelectFun.col(colSql);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, String... colSql) {
        this.smartSelectFun.col(condition, colSql);
        return this.smartSelectFun;
    }

    /**
     * 添加所有字段
     * @return
     */
    @Override
    public SmartSelectFun<M, GetterFun<M,Object>, Object> colAll() {

        this.smartSelectFun.colAll();
        return this.smartSelectFun;
    }



}
