package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.core.BaseBatis;
import com.javaoffers.brief.modelhelper.core.BaseBatisImpl;
import com.javaoffers.brief.modelhelper.core.StatementParserAdepter;
import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.mark.WhereConditionMark;
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
import com.javaoffers.brief.modelhelper.fun.crud.HavingPendingFun;
import com.javaoffers.brief.modelhelper.fun.crud.WhereSelectFun;
import com.javaoffers.brief.modelhelper.log.JqlLogger;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:14
 */
public class WhereSelectFunImpl<M, V> implements WhereSelectFun<M, V> {

    private LinkedList<Condition> conditions;

    public WhereSelectFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.conditions.add(new WhereConditionMark());
    }

    public WhereSelectFunImpl(LinkedList<Condition> conditions, boolean isAddMark) {
        this.conditions = conditions;
        if (isAddMark) {
            this.conditions.add(new WhereConditionMark());
        }
    }

    @Override
    public WhereSelectFunImpl<M, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public WhereSelectFun<M, V> unite(Consumer<WhereSelectFun<M, V>> r) {
        conditions.add(new LFCondition( ConditionTag.LK));
        r.accept(this);
        conditions.add(new RFWordCondition( ConditionTag.RK));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> unite(boolean condition, Consumer<WhereSelectFun<M, V>> r) {
        if(condition){
            unite(r);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> condSQL(String sql) {
        conditions.add(new CondSQLCondition(sql));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> condSQL(boolean condition, String sql) {
        if(condition){
            condSQL(sql);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> condSQL(String sql, Map<String, Object> params) {
        conditions.add(new CondSQLCondition(sql,params));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> condSQL(boolean condition, String sql, Map<String, Object> params) {
        if(condition){
            condSQL(sql, params);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> eq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> eq(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            eq(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ueq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.UEQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ueq(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            ueq(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gt(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.GT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gt(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            gt(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> lt(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.LT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> lt(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            lt(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gtEq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> gtEq(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            gtEq(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ltEq(GetterFun<M, V> col, V value) {
        conditions.add(new WhereOnCondition(col, value, ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> ltEq(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            ltEq(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> between(GetterFun<M, V> col, V start, V end) {
        conditions.add(new BetweenCondition(col, start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> between(boolean condition, GetterFun<M, V> col, V start, V end) {
        if (condition) {
            between(col, start, end);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notBetween(GetterFun<M, V> col, V start, V end) {
        conditions.add(new BetweenCondition(col, start, end, ConditionTag.NOT_BETWEEN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notBetween(boolean condition, GetterFun<M, V> col, V start, V end) {
        if(condition){
            notBetween(col,start,end);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> like(GetterFun<M, V> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> like(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            like(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> likeLeft(GetterFun<M, V> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE_LEFT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> likeLeft(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            likeLeft(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> likeRight(GetterFun<M, V> col, V value) {
        conditions.add(new LikeCondition(col, value, ConditionTag.LIKE_RIGHT));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> likeRight(boolean condition, GetterFun<M, V> col, V value) {
        if (condition) {
            likeRight(col, value);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(GetterFun<M, V> col, V... values) {
        conditions.add(new InCondition(col, values, ConditionTag.IN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(boolean condition, GetterFun<M, V> col, V... values) {
        if (condition) {
            in(col, values);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(GetterFun<M, V> col, Collection... values) {
        conditions.add(new InCondition(col, values, ConditionTag.IN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> in(boolean condition, GetterFun<M, V> col, Collection... values) {
        if (condition) {
            in(col, values);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notIn(GetterFun<M, V> col, V... values) {
        conditions.add(new InCondition(col, values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notIn(boolean condition, GetterFun<M, V> col, V... values) {
        if (condition) {
            notIn(col, values);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notIn(GetterFun<M, V> col, Collection... values) {
        conditions.add(new InCondition(col, values, ConditionTag.NOT_IN));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> notIn(boolean condition, GetterFun<M, V> col, Collection... values) {
        if (condition) {
            notIn(col, values);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> isNull(GetterFun<M, V>... cols) {
        for(GetterFun<M,V> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NULL));
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> isNull(boolean condition, GetterFun<M, V>... cols) {
        if(condition){
            isNull(cols);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> isNotNull(GetterFun<M, V>... cols) {
        for(GetterFun<M,V> col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NOT_NULL));
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> isNotNull(boolean condition, GetterFun<M, V>... cols) {
        if(condition){
            isNull(cols);
        }
        return this;
    }

    @Override
    public WhereSelectFunImpl<M, V> exists(String existsSql) {
        conditions.add(new ExistsCondition<V>(existsSql));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> exists(boolean condition, String existsSql) {
        if (condition) {
            exists(existsSql);
        }
        return this;
    }

    @Override
    public M ex() {
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //解析SQL 并执行 select。
        List<M> exs = exs();
        if (exs != null && exs.size() > 0) {
            return exs.get(0);
        }
        return null;
    }

    @Override
    public HavingPendingFun<M, GetterFun<M, V>, V, ?> groupBy(GetterFun<M, V>... c) {
        conditions.add(new GroupByWordCondition(c,ConditionTag.GROUP_BY));
        return new HavingPendingFunImpl<M, GetterFun<M, V>, V>(conditions);
    }

    @Override
    public HavingPendingFun<M, GetterFun<M, V>, V, ?> groupBy(String... colSql) {
        conditions.add(new GroupByWordCondition(colSql,ConditionTag.GROUP_BY));
        return new HavingPendingFunImpl<M, GetterFun<M, V>, V>(conditions);
    }

    @Override
    public WhereSelectFun<M, V> limitPage(int pageNum, int size) {
        this.conditions.add(new LimitWordCondition(pageNum, size));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> orderA(GetterFun<M, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,true));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> orderA(boolean condition, GetterFun<M, V>... getterFuns) {
        if(condition){
            orderA(getterFuns);
        }
        return this;
    }

    @Override
    public WhereSelectFun<M, V> orderD(GetterFun<M, V>... getterFuns) {
        List<String> clos = Arrays.stream(getterFuns).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos,false));
        return this;
    }

    @Override
    public WhereSelectFun<M, V> orderD(boolean condition, GetterFun<M, V>... getterFuns) {
        if(condition){
            orderD(getterFuns);
        }
        return this;
    }

    @Override
    public List<M> exs() {
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //解析SQL select 并执行。
        BaseBatis instance = BaseBatisImpl.getInstance((HeadCondition) this.conditions.peekFirst());
        SQLStatement sqlStatement = StatementParserAdepter.statementParse(this.conditions);
        JqlLogger.infoSql("SQL: {}", sqlStatement.getSql());
        JqlLogger.infoSql("PAM: {}", sqlStatement.getParams());
        return instance.queryData(sqlStatement.getSql(), sqlStatement.getParams().get(0));
    }
}
