package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.KeyWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.select.SelectTableCondition;
import com.javaoffers.brief.modelhelper.fun.crud.SelectFun;
import com.javaoffers.brief.modelhelper.fun.crud.SmartSelectFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

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
        this.conditions.add(new HeadCondition(CrudMapperMethodThreadLocal.getExcutorDataSource(), mClass));
        this.conditions.add(new SelectTableCondition(TableHelper.getTableName(mClass), mClass));
        this.mClass = mClass;
        this.smartSelectFun = new SmartSelectFunImpl(mClass, conditions);
    }

    @Override
    public SmartSelectFunImpl<M, GetterFun<M,Object>, Object> distinct() {
        this.conditions.add(new KeyWordCondition(ConditionTag.DISTINCT.getTag()));
        return smartSelectFun;
    }

    /**
     *  添加查询字段
     * @param col
     * @return
     */
    @Override
    public SmartSelectFunImpl<M, GetterFun<M,Object>, Object> col(GetterFun<M,Object> col) {
        this.smartSelectFun.col(col);
        return this.smartSelectFun;
    }

    /**
     *  添加查询字段
     * @param cols
     * @return
     */
    @Override
    @SafeVarargs
    public final SmartSelectFunImpl<M, GetterFun<M,Object>, Object> col(GetterFun<M,Object>... cols) {
        this.smartSelectFun.col(cols);
        return this.smartSelectFun;
    }

    @Override
    @SafeVarargs
    public final SmartSelectFunImpl<M, GetterFun<M, Object>, Object> col(boolean condition, GetterFun<M, Object>... cols) {
        this.smartSelectFun.col(condition, cols);
        return this.smartSelectFun;
    }

    @Override
    @SafeVarargs
    public final SmartSelectFunImpl<M, GetterFun<M, Object>, Object> col(AggTag aggTag, GetterFun<M, Object>... cols) {

        this.smartSelectFun.col(aggTag,cols);
        return this.smartSelectFun;
    }

    @Override
    @SafeVarargs
    public final SmartSelectFunImpl<M, GetterFun<M, Object>, Object> col(boolean condition, AggTag aggTag, GetterFun<M, Object>... cols) {
        this. smartSelectFun.col(condition, aggTag, cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFunImpl<M, GetterFun<M, Object>, Object> col(AggTag aggTag, GetterFun<M, Object> col, String asName) {
        this.smartSelectFun.col(aggTag, col, asName);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFunImpl<M, GetterFun<M, Object>, Object> col(boolean condition, AggTag aggTag, GetterFun<M, Object> col, String asName) {
        this.smartSelectFun.col(condition,aggTag,col,asName);
        return this.smartSelectFun;
    }

    @Override
    @SafeVarargs
    public final SmartSelectFunImpl<M, GetterFun<M,Object>, Object> col(String... colSql) {
        this.smartSelectFun.col(colSql);
        return this.smartSelectFun;
    }

    @Override
    @SafeVarargs
    public final SmartSelectFunImpl<M, GetterFun<M, Object>, Object> col(boolean condition, String... colSql) {
        this.smartSelectFun.col(condition, colSql);
        return this.smartSelectFun;
    }

    /**
     * 添加所有字段
     * @return
     */
    @Override
    public SmartSelectFunImpl<M, GetterFun<M,Object>, Object> colAll() {

        this.smartSelectFun.colAll();
        return this.smartSelectFun;
    }

}
