package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.ConstructorFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.select.SelectColumnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.JoinFun;
import com.javaoffers.batis.modelhelper.fun.crud.SmartSelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmartSelectFunImpl<M, C extends GetterFun<M,Object>, V> implements SmartSelectFun<M,C,V> {


    private Class<M> mClass;
    /**
     * 存放 查询字段
     */
    private LinkedConditions<Condition> conditions = new LinkedConditions<>();

    public SmartSelectFunImpl(Class<M> mClass, LinkedConditions<Condition> conditions) {
        this.mClass = mClass;
        this.conditions = conditions;
    }

    @Override
    public SmartSelectFun<M, C, V> col(C... cols) {
        Stream.of(cols).forEach(col -> {
            this.conditions.add(new SelectColumnCondition(col));
        });
        return this;
    }

    @Override
    public SmartSelectFun<M, C, V> col(boolean condition, C... cols) {
        if(condition){
            col(cols);
        }
        return this;
    }

    @Override
    public SmartSelectFun<M, C, V> col(AggTag aggTag, C... cols) {
        Stream.of(cols).forEach(col->{
            Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(col);
            String colName = colNameAndAliasName.getLeft();
            String aliasName = colNameAndAliasName.getRight();
            this.conditions.add(new SelectColumnCondition(aggTag.name()+"("+colName+") as " + aliasName));
        });
        return this;
    }

    @Override
    public SmartSelectFun<M, C, V> col(boolean condition, AggTag aggTag, C... cols) {
        if(condition){
            col(aggTag,cols);
        }
        return this;
    }

    @Override
    public SmartSelectFun<M, C, V> col(AggTag aggTag, C col, String asName) {
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(col);
        String colName = colNameAndAliasName.getLeft();
        this.conditions.add(new SelectColumnCondition( aggTag.name()+"("+colName+") as "+ asName));
        return this;
    }

    @Override
    public SmartSelectFun<M, C, V> col(boolean condition, AggTag aggTag, C col, String asName) {
        if(condition){
            col(aggTag, col, asName);
        }
        return this;
    }

    @Override
    public SmartSelectFun<M, C, V> col(String... colSql) {
        Stream.of(colSql).forEach(col->{
            this.conditions.add(new SelectColumnCondition( col));
        });
        return this;
    }

    @Override
    public SmartSelectFun<M, C, V> col(boolean condition, String... colSql) {
        if(condition){
            col(colSql);
        }
        return this;
    }

    @Override
    public SmartSelectFun<M, C, V> colAll() {
        Set<SelectColumnCondition> cols = TableHelper.getColAll(this.mClass).stream().map(colName ->
                new SelectColumnCondition(colName)).collect(Collectors.toSet());
        this.conditions.addAll(cols);
        return this;
    }

    @Override
    public <M2, C2 extends GetterFun<M2, Object>> JoinFun<M, M2, C2, V> leftJoin(ConstructorFun<M2> m2) {
        return new JoinFunmpl(this.mClass,TableHelper.getClassFromConstructorFun(m2),this.conditions,ConditionTag.LEFT_JOIN);
    }

    @Override
    public <M2, C2 extends GetterFun<M2, Object>> JoinFun<M, M2, C2, V> innerJoin(ConstructorFun<M2> m2) {
        return new JoinFunmpl(this.mClass,TableHelper.getClassFromConstructorFun(m2),this.conditions, ConditionTag.INNER_JOIN);
    }

    @Override
    public <M2, C2 extends GetterFun<M2, Object>> JoinFun<M, M2, C2, V> rightJoin(ConstructorFun<M2> m2) {
        return new JoinFunmpl(this.mClass,TableHelper.getClassFromConstructorFun(m2),this.conditions, ConditionTag.RIGHT_JOIN);
    }

    @Override
    public WhereSelectFun<M, V> where() {
        return new WhereSelectFunImpl(this.conditions);
    }
}
