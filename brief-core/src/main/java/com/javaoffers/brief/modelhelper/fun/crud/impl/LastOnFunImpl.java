package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.mark.OnConditionMark;
import com.javaoffers.brief.modelhelper.fun.condition.on.OnColumnFunCondition;
import com.javaoffers.brief.modelhelper.fun.condition.on.OnValueFunCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.BetweenCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.CondSQLCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.ExistsCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.InCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.IsNullOrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LFCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LikeCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.RFWordCondition;
import com.javaoffers.brief.modelhelper.fun.crud.LastOnFun;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author mingJie
 */
public class LastOnFunImpl<M1,M2, M3, C2 extends GetterFun<M2, Object> & Serializable, C3 extends GetterFun<M3, Object> & Serializable, V>
        implements LastOnFun<M1, M2, M3, C2, C3, V> {

    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl<M1,V> whereSelectFun ;

    public LastOnFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.conditions.add(new OnConditionMark());
        this.whereSelectFun = new WhereSelectFunImpl<M1,V>(conditions,false);;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> oeq(C2 col, C3 col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.EQ));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> oueq(C2 col, C3 col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.UEQ));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> ogt(C2 col, C3 col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.GT));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> olt(C2 col, C3 col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.LT));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> ogtEq(C2 col, C3 col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> oltEq(C2 col, C3 col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> unite(Consumer<LastOnFun<M1, M2, M3, C2, C3, V>> r) {
        conditions.add(new LFCondition( ConditionTag.LK));
        r.accept(this);
        conditions.add(new RFWordCondition( ConditionTag.RK));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> unite(boolean condition, Consumer<LastOnFun<M1, M2, M3, C2, C3, V>> r) {
        if(condition){
            unite(r);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> condSQL(String sql) {
        conditions.add(new CondSQLCondition(sql));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> condSQL(boolean condition, String sql) {
        if(condition){
            condSQL(sql);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> condSQL(String sql, Map<String, Object> params) {
        conditions.add(new CondSQLCondition(sql,params));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> condSQL(boolean condition, String sql, Map<String, Object> params) {
        if(condition){
            condSQL(sql, params);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> eq(C3 col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.EQ));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> eq(boolean condition, C3 col, V value) {
        if (condition) {
            eq(col,value);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> ueq(C3 col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.UEQ));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> ueq(boolean condition, C3 col, V value) {
        if (condition) {
            ueq(col,value);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> gt(C3 col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.GT));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> gt(boolean condition, C3 col, V value) {
        if (condition) {
            gt(col,value);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> lt(C3 col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.LT));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> lt(boolean condition, C3 col, V value) {
        if (condition) {
            lt(col,value);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> gtEq(C3 col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> gtEq(boolean condition, C3 col, V value) {
        if (condition) {
            gtEq(col,value);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> ltEq(C3 col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> ltEq(boolean condition, C3 col, V value) {
        if (condition) {
            ltEq(col,value);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> between(C3 col, V start, V end) {
        conditions.add(new BetweenCondition(col, start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> between(boolean condition, C3 col, V start, V end) {
        if (condition) {
            between(col, start, end);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> notBetween(C3 col, V start, V end) {
        conditions.add(new BetweenCondition(col, start, end, ConditionTag.NOT_BETWEEN));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> notBetween(boolean condition, C3 col, V start, V end) {
        if(condition){
            notBetween(col,start,end);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> like(C3 col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> like(boolean condition, C3 col, V value) {
        if (condition) {
            like(col,value);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> likeLeft(C3 col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE_LEFT));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> likeLeft(boolean condition, C3 col, V value) {
        if (condition) {
            likeLeft(col,value);
        }
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> likeRight(C3 col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE_RIGHT));
        return this;
    }

    @Override
    public LastOnFun<M1, M2, M3, C2, C3, V> likeRight(boolean condition, C3 col, V value) {
        if (condition) {
            likeRight(col,value);
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> in(C3 col, V... values) {
        conditions.add(new InCondition(col, values, ConditionTag.IN));
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> in(boolean condition, C3 col, V... values) {
        if (condition) {
            in(col,values);
        }
        return this;
    }

    @Override 
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> in(C3 col, Collection... values) {
        conditions.add(new InCondition(col, values, ConditionTag.IN));
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> in(boolean condition, C3 col, Collection... values) {
        if (condition) {
            in(col,values);
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> notIn(C3 col, V... values) {
        conditions.add(new InCondition(col, values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> notIn(boolean condition, C3 col, V... values) {
        if(condition){
            notIn(col,values);
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> notIn(C3 col, Collection... values) {
        conditions.add(new InCondition(col, values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> notIn(boolean condition, C3 col, Collection... values) {
        if(condition){
            notIn(col,values);
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> isNull(C3... cols) {
        for(GetterFun<M3,Object> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NULL));
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> isNull(boolean condition, C3... cols) {
        if(condition){
            isNull(cols);
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> isNotNull(C3... cols) {
        for(GetterFun<M3,Object> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NOT_NULL));
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> isNotNull(boolean condition, C3... cols) {
        if(condition){
            isNotNull(cols);
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> exists(C3... cols) {
        for(GetterFun<M3,Object> col: cols){
            conditions.add(new ExistsCondition<V>(col));
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final LastOnFun<M1, M2, M3, C2, C3, V> exists(boolean condition, C3... cols) {
        if (condition) {
            exists(cols);
        }
        return this;
    }

    @Override
    public LastLeftWhereSelectFunImpl<M1, M2, M3, V> where() {
        return new LastLeftWhereSelectFunImpl<>(conditions);
    }

}
