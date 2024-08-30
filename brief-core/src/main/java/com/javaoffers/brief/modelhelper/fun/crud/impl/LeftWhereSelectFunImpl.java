package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GGetterFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.where.BetweenCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.CondSQLCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.ExistsCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.GroupByWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.InCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.IsNullOrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LFCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LikeCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.RFWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereOnCondition;
import com.javaoffers.brief.modelhelper.fun.crud.LeftWhereSelectFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * @Description: Entered as a string as a field name, this class is used when turning to where after the on condition.
 * In other words on ... where (LeftWhereSelectFunImpl)
 * @Auther: create by cmj on 2022/5/2 02:14
 * @param <M> Primary table
 * @param <M2> Subtable: used to support subtable fields group by , order by
 *
 */
public class LeftWhereSelectFunImpl<M, M2, V , R extends LeftWhereSelectFunImpl<M, M2, V, R>>
        implements LeftWhereSelectFun<M, M2, GetterFun<M,V>, GGetterFun<M2,V>, V, R> {

    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl whereSelectFun;

    public LeftWhereSelectFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.whereSelectFun = new WhereSelectFunImpl(this.conditions);
    }

    @Override
    public M ex() {
        List<M> exs = exs();
        if(exs == null ||exs.size() == 0){return null;}
        return exs.get(0);
    }

    @Override
    public R or() {
        conditions.add(new OrCondition());
        return (R) this;
    }

    @Override
    public R unite(
            Consumer<R> r) {
        conditions.add(new LFCondition( ConditionTag.LK));
        r.accept((R) this);
        conditions.add(new RFWordCondition( ConditionTag.RK));
        return (R) this;
    }

    @Override
    public R unite(
            boolean condition, Consumer<R> r) {
        if(condition){
            unite(r);
        }
        return (R) this;
    }

    @Override
    public R condSQL(String sql) {
        conditions.add(new CondSQLCondition(sql));
        return (R) this;
    }

    @Override
    public R condSQL(boolean condition, String sql) {
        if(condition){
            condSQL(sql);
        }
        return (R) this;
    }

    @Override
    public R condSQL(String sql, Map<String, Object> params) {
        conditions.add(new CondSQLCondition(sql, params));
        return (R) this;
    }

    @Override
    public R condSQL(boolean condition, String sql, Map<String, Object> params) {
        if(condition){
            condSQL(sql, params);
        }
        return (R) this;
    }

    @Override
    public R orderA(GetterFun<M, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,true));
        return (R) this;
    }

    @Override
    public R orderA(boolean condition, GetterFun<M, V>... getterFuns) {
        if(condition){
            orderA(getterFuns);
        }
        return (R) this;
    }

    @Override
    public R orderD(GetterFun<M, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,false));
        return (R) this;
    }

    @Override
    public R orderD(boolean condition, GetterFun<M, V>... getterFuns) {
        if(condition){
            orderD(getterFuns);
        }
        return (R) this;
    }

    public R orderA(GGetterFun<M2, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,true));
        return (R) this;
    }

    public R orderA(boolean condition, GGetterFun<M2, V>... getterFuns) {
        if(condition){
            orderA(getterFuns);
        }
        return (R) this;
    }

    public R orderD(GGetterFun<M2, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,false));
        return (R) this;
    }

    public R orderD(boolean condition, GGetterFun<M2, V>... getterFuns) {
        if(condition){
            orderD(getterFuns);
        }
        return (R) this;
    }

    @Override
    public R eq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.EQ));
        return (R) this;
    }

    @Override
    public R eq(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            eq(col,value);
        }
        return (R) this;
    }

    @Override
    public R ueq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.UEQ));
        return (R) this;
    }

    @Override
    public R ueq(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            ueq(col, value);
        }
        return (R) this;
    }

    @Override
    public R gt(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.GT));
        return (R) this;
    }

    @Override
    public R gt(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            gt(col, value);
        }
        return (R) this;
    }

    @Override
    public R lt(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.LT));
        return (R) this;
    }

    @Override
    public R lt(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            lt(col, value);
        }
        return (R) this;
    }

    @Override
    public R gtEq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.GT_EQ));
        return (R) this;
    }

    @Override
    public R gtEq(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            gtEq(col, value);
        }
        return (R) this;
    }

    @Override
    public R ltEq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.LT_EQ));
        return (R) this;
    }

    @Override
    public R ltEq(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            ltEq(col, value);
        }
        return (R) this;
    }

    @Override
    public R between(GetterFun<M, V> col, V start, V end) {
        conditions.add(new BetweenCondition(col, start, end, ConditionTag.BETWEEN));
        return (R) this;
    }

    @Override
    public R between(boolean condition, GetterFun<M, V> col, V start, V end) {
        if (condition) {
            between(col, start, end);
        }
        return (R) this;
    }

    @Override
    public R notBetween(GetterFun<M, V> col, V start, V end) {
        conditions.add(new BetweenCondition(col, start, end, ConditionTag.NOT_BETWEEN));
        return (R) this;
    }

    @Override
    public R notBetween(boolean condition, GetterFun<M, V> col, V start, V end) {
        if(condition){
            notBetween(col,start,end);
        }
        return (R) this;
    }

    @Override
    public R like(GetterFun<M, V> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE));
        return (R) this;
    }

    @Override
    public R like(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            like(col, value);
        }
        return (R) this;
    }

    @Override
    public R likeLeft(GetterFun<M, V> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE_LEFT));
        return (R) this;
    }

    @Override
    public R likeLeft(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            likeLeft(col, value);
        }
        return (R) this;
    }

    @Override
    public R likeRight(GetterFun<M, V> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE_RIGHT));
        return (R) this;
    }

    @Override
    public R likeRight(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            likeRight(col, value);
        }
        return (R) this;
    }

    @Override
    public R in(GetterFun<M, V> col, V... values) {
        conditions.add(new InCondition(col, values, ConditionTag.IN));
        return (R) this;
    }

    @Override
    public R in(boolean condition, GetterFun<M, V> col, V... values) {
        if (condition) {
            in(col, values);
        }
        return (R) this;
    }

    @Override
    public R in(GetterFun<M, V> col, Collection... values) {
        conditions.add(new InCondition(col, values, ConditionTag.IN));
        return (R) this;
    }

    @Override
    public R in(boolean condition, GetterFun<M, V> col, Collection... values) {
        if (condition) {
            in(col, values);
        }
        return (R) this;
    }

    @Override
    public R notIn(GetterFun<M, V> col, V... values) {
        conditions.add(new InCondition(col, values, ConditionTag.NOT_IN));
        return (R) this;
    }

    @Override
    public R notIn(boolean condition, GetterFun<M, V> col, V... values) {
        if (condition) {
            notIn(col, values);
        }
        return (R) this;
    }

    @Override
    public R notIn(GetterFun<M, V> col, Collection... values) {
        conditions.add(new InCondition(col, values, ConditionTag.NOT_IN));
        return (R) this;
    }

    @Override
    public R notIn(boolean condition, GetterFun<M, V> col, Collection... values) {
        if (condition) {
            notIn(col, values);
        }
        return (R) this;
    }

    @Override
    public R isNull(GetterFun<M, V>... cols) {
        for(GetterFun<M,V> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NULL));
        }
        return (R) this;
    }

    @Override
    public R isNull(boolean condition, GetterFun<M, V>... cols) {
        if(condition){
            isNull(cols);
        }
        return (R) this;
    }

    @Override
    public R isNotNull(GetterFun<M, V>... cols) {
        for(GetterFun<M,V> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NOT_NULL));
        }
        return (R) this;
    }

    @Override
    public R isNotNull(boolean condition, GetterFun<M, V>... cols) {
        if(condition){
            isNull(cols);
        }
        return (R) this;
    }

    @Override
    public R exists(GetterFun<M, V>... cols) {
        for(GetterFun<M,V> col: cols){
            conditions.add(new ExistsCondition<V>(col));
        }
        return (R) this;
    }

    @Override
    public R exists(boolean condition, GetterFun<M, V>... cols) {
        if (condition) {
            exists(cols);
        }
        return (R) this;
    }

    @Override
    public LeftHavingPendingFunImpl<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V, V> groupBy(GetterFun<M, V>... c) {
        conditions.add(new GroupByWordCondition(c,ConditionTag.GROUP_BY));
        return new LeftHavingPendingFunImpl<>(conditions);
    }

    @Override
    public LeftHavingPendingFunImpl<M, M2, GetterFun<M, V>, GGetterFun<M2, V>, V, V> groupBy(GGetterFun<M2, V>... c) {
        conditions.add(new GroupByWordCondition(c,ConditionTag.GROUP_BY));
        return new LeftHavingPendingFunImpl<>(conditions);
    }

    @Override
    public R limitPage(int pageNum, int size) {
        this.conditions.add(new LimitWordCondition(pageNum, size));
        return (R) this;
    }

    @Override
    public List<M> exs() {
        return whereSelectFun.exs();
    }

    @Override
    public void stream(Consumer<M> consumer) {
        whereSelectFun.stream(consumer);
    }
}
