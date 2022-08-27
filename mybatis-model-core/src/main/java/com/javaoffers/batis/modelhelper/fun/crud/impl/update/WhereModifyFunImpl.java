package com.javaoffers.batis.modelhelper.fun.crud.impl.update;

import com.javaoffers.batis.modelhelper.core.*;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.CondSQLCondition;
import com.javaoffers.batis.modelhelper.fun.condition.IsNullOrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.LFCondition;
import com.javaoffers.batis.modelhelper.fun.condition.RFWordCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.AddPatchMarkCondition;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereSelectFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.update.SmartUpdateFun;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @Description: 更新条件
 * @Auther: create by cmj on 2022/5/4 20:42
 */
public class WhereModifyFunImpl<M,V>  implements WhereModifyFun<M,V>  {
    
    private WhereSelectFunImpl whereFun;

    private LinkedConditions<Condition> conditions;

    private Class modelClass;

    private boolean isUpdateNull;

    public WhereModifyFunImpl(LinkedConditions<Condition> conditions,Class modelClass) {
        this.conditions = conditions;
        this.whereFun = new WhereSelectFunImpl(this.conditions);
        this.modelClass = modelClass;
    }

    @Override
    public WhereModifyFun<M, V> or() {
        whereFun.or();
        return this;
    }

    @Override
    public WhereModifyFun<M, V> unite(Consumer<WhereModifyFun<M, V>> r) {
        conditions.add(new LFCondition( ConditionTag.LK));
        r.accept(this);
        conditions.add(new RFWordCondition( ConditionTag.RK));
        return this;
    }

    @Override
    public WhereModifyFun<M, V> unite(boolean condition, Consumer<WhereModifyFun<M, V>> r) {
        if(condition){
            unite(r);
        }
        return this;
    }

    @Override
    public WhereModifyFun<M, V> condSQL(String sql) {
        conditions.add(new CondSQLCondition(sql));
        return this;
    }

    @Override
    public WhereModifyFun<M, V> condSQL(boolean condition, String sql) {
        if(condition){
            condSQL(sql);
        }
        return this;
    }

    @Override
    public WhereModifyFun<M, V> condSQL(String sql, Map<String, Object> params) {
        conditions.add(new CondSQLCondition(sql, params));
        return this;
    }

    @Override
    public WhereModifyFun<M, V> condSQL(boolean condition, String sql, Map<String, Object> params) {
        if(condition){
            condSQL(sql, params);
        }
        return this;
    }

    @Override
    public WhereModifyFun<M, V> eq(GetterFun<M, V> col, V value) {
        whereFun.eq(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> eq(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.eq(condition, col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> ueq(GetterFun<M, V> col, V value) {
        whereFun.ueq(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> ueq(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.ueq(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> gt(GetterFun<M, V> col, V value) {
        whereFun.gt(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> gt(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.gt(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> lt(GetterFun<M, V> col, V value) {
        whereFun.lt(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> lt(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.lt(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> gtEq(GetterFun<M, V> col, V value) {
        whereFun.gtEq(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> gtEq(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.gtEq(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> ltEq(GetterFun<M, V> col, V value) {
        whereFun.ltEq(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> ltEq(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.ltEq(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> between(GetterFun<M, V> col, V start, V end) {
        whereFun.between(col,start,end);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> between(boolean condition, GetterFun<M, V> col, V start, V end) {
        whereFun.between(condition,col,start,end);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> notBetween(GetterFun<M, V> col, V start, V end) {
        return this;
    }

    @Override
    public WhereModifyFun<M, V> notBetween(boolean condition, GetterFun<M, V> col, V start, V end) {
        return this;
    }

    @Override
    public WhereModifyFun<M, V> like(GetterFun<M, V> col, V value) {
        whereFun.like(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> like(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.like(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> likeLeft(GetterFun<M, V> col, V value) {
        whereFun.likeLeft(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> likeLeft(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.likeLeft(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> likeRight(GetterFun<M, V> col, V value) {
        whereFun.likeRight(col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> likeRight(boolean condition, GetterFun<M, V> col, V value) {
        whereFun.likeRight(condition,col,value);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> in(GetterFun<M, V> col, V... values) {
        whereFun.in(col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> in(boolean condition, GetterFun<M, V> col, V... values) {
        whereFun.in(condition,col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> in(GetterFun<M, V> col, Collection... values) {
        whereFun.in(col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> in(boolean condition, GetterFun<M, V> col, Collection... values) {
        whereFun.in(condition,col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> notIn(GetterFun<M, V> col, V... values) {
        whereFun.notIn(col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> notIn(boolean condition, GetterFun<M, V> col, V... values) {
        whereFun.notIn(condition, col, values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> notIn(GetterFun<M, V> col, Collection... values) {
        whereFun.notIn(col,values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> notIn(boolean condition, GetterFun<M, V> col, Collection... values) {
        whereFun.notIn(condition, col, values);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> isNull(GetterFun<M, V>... cols) {
        for(GetterFun<M,V> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NULL));
        }
        return this;
    }

    @Override
    public WhereModifyFun<M, V> isNull(boolean condition, GetterFun<M, V>... cols) {
        if(condition){
            isNull(cols);
        }
        return this;
    }

    @Override
    public WhereModifyFun<M, V> isNotNull(GetterFun<M, V>... cols) {
        for(GetterFun<M,V> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NOT_NULL));
        }
        return this;
    }

    @Override
    public WhereModifyFun<M, V> isNotNull(boolean condition, GetterFun<M, V>... cols) {
        if(condition){
            isNotNull(cols);
        }
        return this;
    }

    @Override
    public WhereModifyFun<M, V> exists(String existsSql) {
        whereFun.exists(existsSql);
        return this;
    }

    @Override
    public WhereModifyFun<M, V> exists(boolean condition, String existsSql) {
        whereFun.exists(condition,existsSql);
        return this;
    }

    @Override
    public Integer ex() {
        MoreSQLInfo moreSqlInfo = (MoreSQLInfo) ConditionParse.conditionParse(conditions);
        List<SQLInfo> sqlInfos = moreSqlInfo.getSqlInfos();
        HashMap<String, List<Map<String, Object>>> sqlbatch = new HashMap<>();
        for(SQLInfo sqlInfo : sqlInfos){
            String sql = sqlInfo.getSql();
            List<Map<String, Object>> params = sqlInfo.getParams();
            List<Map<String, Object>> paramBatch = sqlbatch.get(sql);
            if(paramBatch == null){
                paramBatch = new LinkedList<Map<String, Object>>();
                sqlbatch.put(sql, paramBatch);
            }
            paramBatch.addAll(params);
            System.out.println("SQL: "+sql);
            System.out.println("参数： "+params);
        }
        AtomicInteger count = new AtomicInteger();
        sqlbatch.forEach((sql, params) ->{
            Integer integer = BaseBatisImpl.baseBatis.batchUpdate(sql, params);
            count.addAndGet(integer);
        });

        return count.get();
    }

    @Override
    public SmartUpdateFun<M, GetterFun<M, Object>, V> addBatch() {
         conditions.add(new AddPatchMarkCondition());
         return new SmartUpdateFunImpl(modelClass, conditions,isUpdateNull);
    }
}
