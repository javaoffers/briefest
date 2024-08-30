package com.javaoffers.brief.modelhelper.fun.crud.impl;


import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.ConstructorFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.mark.OnConditionMark;
import com.javaoffers.brief.modelhelper.fun.condition.where.BetweenCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.CondSQLCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.ExistsCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.InCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.IsNullOrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LFCondition;
import com.javaoffers.brief.modelhelper.fun.condition.on.OnColumnFunCondition;
import com.javaoffers.brief.modelhelper.fun.condition.on.OnValueFunCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LikeCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.RFWordCondition;
import com.javaoffers.brief.modelhelper.fun.crud.LastJoinFun;
import com.javaoffers.brief.modelhelper.fun.crud.OnFun;
import com.javaoffers.brief.modelhelper.fun.crud.SmartOnFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:13
 */
public class OnFunImpl<M1, M2, V> implements OnFun<M1,M2,V> {
    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl<M1,V> whereSelectFun ;
    private Class<M2> m1Class;
    private Class<M2> m2Class;

    public OnFunImpl(Class<M2> m1Class, Class<M2> m2Class, LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.conditions.add(new OnConditionMark());
        this.whereSelectFun = new WhereSelectFunImpl<M1,V>(conditions,false);
        this.m2Class = m2Class;
        this.m1Class = m1Class;
    }

    @Override
    public M1 ex() {
        return whereSelectFun.ex();
    }

    @Override
    public List<M1> exs() {
        return whereSelectFun.exs();
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> oeq(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.EQ));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> oueq(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.UEQ));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ogt(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.GT));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> olt(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.LT));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ogtEq(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> oltEq(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col, col2, ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public LeftWhereSelectFunImpl<M1,M2, V> where() {
        return new LeftWhereSelectFunImpl<>(conditions);
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> unite(Consumer<SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V>> r) {
        conditions.add(new LFCondition( ConditionTag.LK));
        r.accept(this);
        conditions.add(new RFWordCondition( ConditionTag.RK));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> unite(boolean condition, Consumer<SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V>> r) {
        if(condition){
            unite(r);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> condSQL(String sql) {
        conditions.add(new CondSQLCondition(sql));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> condSQL(boolean condition, String sql) {
        if(condition){
            condSQL(sql);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> condSQL(String sql, Map<String, Object> params) {
        conditions.add(new CondSQLCondition(sql,params));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> condSQL(boolean condition, String sql, Map<String, Object> params) {
        if(condition){
            condSQL(sql, params);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> eq(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.EQ));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> eq(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            eq(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ueq(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.UEQ));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ueq(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            ueq(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> gt(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.GT));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> gt(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            gt(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> lt(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.LT));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> lt(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            lt(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> gtEq(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> gtEq(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            gtEq(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ltEq(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col, value, ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ltEq(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            ltEq(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> between(GetterFun<M2, Object> col, V start, V end) {
        conditions.add(new BetweenCondition(col, start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> between(boolean condition, GetterFun<M2, Object> col, V start, V end) {
        if (condition) {
            between(col, start, end);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> notBetween(GetterFun<M2, Object> col, V start, V end) {
        conditions.add(new BetweenCondition(col, start, end, ConditionTag.NOT_BETWEEN));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> notBetween(boolean condition, GetterFun<M2, Object> col, V start, V end) {
        if(condition){
            notBetween(col,start,end);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> like(GetterFun<M2, Object> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> like(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            like(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> likeLeft(GetterFun<M2, Object> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE_LEFT));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> likeLeft(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            likeLeft(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> likeRight(GetterFun<M2, Object> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE_RIGHT));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> likeRight(boolean condition, GetterFun<M2, Object> col, V value) {
        if (condition) {
            likeRight(col,value);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> in(GetterFun<M2, Object> col, V... values) {
        conditions.add(new InCondition(col, values, ConditionTag.IN));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> in(boolean condition, GetterFun<M2, Object> col, V... values) {
        if (condition) {
            in(col,values);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> in(GetterFun<M2, Object> col, Collection... values) {
        conditions.add(new InCondition(col, values, ConditionTag.IN));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> in(boolean condition, GetterFun<M2, Object> col, Collection... values) {
        if (condition) {
            in(col,values);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> notIn(GetterFun<M2, Object> col, V... values) {
        conditions.add(new InCondition(col, values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> notIn(boolean condition, GetterFun<M2, Object> col, V... values) {
        if(condition){
            notIn(col,values);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> notIn(GetterFun<M2, Object> col, Collection... values) {
        conditions.add(new InCondition(col, values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> notIn(boolean condition, GetterFun<M2, Object> col, Collection... values) {
        if(condition){
            notIn(col,values);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> isNull(GetterFun<M2, Object>... cols) {
        for(GetterFun<M2,Object> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NULL));
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> isNull(boolean condition, GetterFun<M2, Object>... cols) {
        if(condition){
            isNull(cols);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> isNotNull(GetterFun<M2, Object>... cols) {
        for(GetterFun<M2,Object> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NOT_NULL));
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> isNotNull(boolean condition, GetterFun<M2, Object>... cols) {
        if(condition){
            isNotNull(cols);
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> exists(GetterFun<M2, Object>... cols) {
        for(GetterFun<M2,Object> col: cols){
            conditions.add(new ExistsCondition<V>(col));
        }
        return this;
    }

    @Override
    public SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> exists(boolean condition, GetterFun<M2, Object>... cols) {
        if (condition) {
            exists(cols);
        }
        return this;
    }

    @Override
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFun<M1,M2, M3, C3, V> leftJoin(ConstructorFun<M3> m3) {
        HeadCondition headCondition = (HeadCondition) this.conditions.peekFirst();
        return new LastJoinFunImpl(this.m1Class, this.m2Class, TableHelper.getClassFromConstructorFunForJoin(m3,headCondition.getDataSource()), this.conditions, ConditionTag.LEFT_JOIN);
    }

    @Override
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFun<M1,M2, M3, C3, V> innerJoin(ConstructorFun<M3> m3) {
        HeadCondition headCondition = (HeadCondition) this.conditions.peekFirst();
        return new LastJoinFunImpl(this.m1Class, this.m2Class, TableHelper.getClassFromConstructorFunForJoin(m3,headCondition.getDataSource()), this.conditions, ConditionTag.INNER_JOIN);
    }

    @Override
    public <M3, C3 extends GetterFun<M3, Object>> LastJoinFun<M1,M2, M3, C3, V> rightJoin(ConstructorFun<M3> m3) {
        HeadCondition headCondition = (HeadCondition) this.conditions.peekFirst();
        return new LastJoinFunImpl(this.m1Class, this.m2Class, TableHelper.getClassFromConstructorFunForJoin(m3,headCondition.getDataSource()), this.conditions, ConditionTag.RIGHT_JOIN);
    }

    @Override
    public void stream(Consumer<M1> consumer) {
         whereSelectFun.stream(consumer);
    }
}
