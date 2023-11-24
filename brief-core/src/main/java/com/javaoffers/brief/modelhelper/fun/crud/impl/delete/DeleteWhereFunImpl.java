package com.javaoffers.brief.modelhelper.fun.crud.impl.delete;

import com.javaoffers.brief.modelhelper.core.BaseBatis;
import com.javaoffers.brief.modelhelper.core.BaseBatisImpl;
import com.javaoffers.brief.modelhelper.core.StatementParserAdepter;
import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.crud.delete.DeleteWhereFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.WhereSelectFunImpl;
import com.javaoffers.brief.modelhelper.log.JqlLogger;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

public class DeleteWhereFunImpl<M,C extends GetterFun<M, V>,V> implements DeleteWhereFun<M,C,V> {

    private LinkedConditions<Condition> conditions;

    private WhereSelectFunImpl whereFun;

    private Class modelClass;

    public DeleteWhereFunImpl(LinkedConditions<Condition> conditions, Class modelClass) {
        this.conditions = conditions;
        this.modelClass = modelClass;
        this.whereFun = new WhereSelectFunImpl(conditions);
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> or() {
        this.whereFun.or();
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> unite(Consumer<DeleteWhereFun<M, C, V>> r) {
        this.whereFun.unite(r);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> unite(boolean condition, Consumer<DeleteWhereFun<M, C, V>> r) {
        this.whereFun.unite(condition,r);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> condSQL(String sql) {
        this.whereFun.condSQL(sql);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> condSQL(boolean condition, String sql) {
        this.whereFun.condSQL(condition,sql);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> condSQL(String sql, Map<String, Object> params) {
        this.whereFun.condSQL(sql,params);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> condSQL(boolean condition, String sql, Map<String, Object> params) {
        this.whereFun.condSQL(condition,sql,params);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> eq(C col, V value) {
        this.whereFun.eq(col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> eq(boolean condition, C col, V value) {
        this.whereFun.eq(condition, col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> ueq(C col, V value) {
        this.whereFun.ueq(col,value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> ueq(boolean condition, C col, V value) {
        this.whereFun.ueq(condition, col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> gt(C col, V value) {
        this.whereFun.gt(col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> gt(boolean condition, C col, V value) {
        this.whereFun.gt(condition, col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> lt(C col, V value) {
        this.whereFun.lt(col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> lt(boolean condition, C col, V value) {
        this.whereFun.lt(condition, col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> gtEq(C col, V value) {
        this.whereFun.gtEq(col,value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> gtEq(boolean condition, C col, V value) {
        this.whereFun.gtEq(condition, col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> ltEq(C col, V value) {
        this.whereFun.ltEq(col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> ltEq(boolean condition, C col, V value) {
        this.whereFun.ltEq(condition, col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> between(C col, V start, V end) {
        this.whereFun.between(col, start, end);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> between(boolean condition, C col, V start, V end) {
        this.whereFun.between(condition,col,start,end);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> notBetween(C col, V start, V end) {
        this.whereFun.notBetween(col,start,end);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> notBetween(boolean condition, C col, V start, V end) {
        this.whereFun.notBetween(condition, col, start, end);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> like(C col, V value) {
        this.whereFun.like(col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> like(boolean condition, C col, V value) {
        this.whereFun.like(condition, col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> likeLeft(C col, V value) {
        this.whereFun.likeLeft( col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> likeLeft(boolean condition, C col, V value) {
        this.whereFun.likeLeft( condition,  col,  value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> likeRight(C col, V value) {
        this.whereFun.likeRight( col,  value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> likeRight(boolean condition, C col, V value) {
        this.whereFun.likeRight( condition,  col, value);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> in(C col, V... values) {
        this.whereFun.in( col,  values);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> in(boolean condition, C col, V... values) {
        this.whereFun.in( condition,  col,  values);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> in(C col, Collection... values) {
        this.whereFun.in( col,  values);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> in(boolean condition, C col, Collection... values) {
        this.whereFun.in( condition,  col,  values);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> notIn(C col, V... values) {
        this.whereFun.notIn( col,  values);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> notIn(boolean condition, C col, V... values) {
        this.whereFun.notIn(condition, col, values);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> notIn(C col, Collection... values) {
        this.whereFun.notIn( col,  values);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> notIn(boolean condition, C col, Collection... values) {
        this.whereFun.notIn( condition,  col,  values);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> isNull(C... cols) {
        this.whereFun.isNull(cols);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> isNull(boolean condition, C... cols) {
        this.whereFun.isNull( condition,  cols);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> isNotNull(C... cols) {
        this.whereFun.isNotNull(cols);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> isNotNull(boolean condition, C... cols) {
        this.whereFun.isNotNull(condition, cols);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> exists(String existsSql) {
        this.whereFun.exists(existsSql);
        return this;
    }

    @Override
    public DeleteWhereFunImpl<M, C, V> exists(boolean condition, String existsSql) {
        this.whereFun.exists(condition, existsSql);
        return this;
    }

    @Override
    public Integer ex() {
        BaseBatis instance = BaseBatisImpl.getInstance((HeadCondition) conditions.peekFirst());
        SQLStatement sqlStatement = StatementParserAdepter.statementParse(conditions);
        JqlLogger.infoSql("SQL: {}", sqlStatement.getSql());
        JqlLogger.infoSql("PAM: {}", sqlStatement.getParams());
        Integer count = instance.batchUpdate(sqlStatement.getSql(), sqlStatement.getParams());
        return count;
    }
}
