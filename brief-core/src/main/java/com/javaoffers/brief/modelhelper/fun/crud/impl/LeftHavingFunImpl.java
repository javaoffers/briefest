package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GGetterFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.where.CondSQLCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.ExistsCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.HavingBetweenCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.HavingGroupCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.HavingInCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.HavingMarkWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.IsNullOrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LFCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LikeCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.RFWordCondition;
import com.javaoffers.brief.modelhelper.fun.crud.HavingFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Description: having 后面只允许出现统计函数条件表达式. 若想非统计函数表达式应在where / on 中书写. (设计如此)
 * @Auther: create by cmj on 2022/6/5 19:44
 */
public class LeftHavingFunImpl<M, M2, C extends GetterFun<M, ?>, C2 extends GGetterFun<M2, ?>, V, V2, R extends LeftHavingFunImpl<M, M2, C , C2 , V, V2, R>>
        implements HavingFun<M, C, V, R> {

    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl whereSelectFun;

    public LeftHavingFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.conditions.add(new HavingMarkWordCondition());
        this.whereSelectFun = new WhereSelectFunImpl(this.conditions, false);
    }
    public LeftHavingFunImpl(LinkedList<Condition> conditions, boolean isAddMark) {
        this.conditions = conditions;
        if(isAddMark){
            this.conditions.add(new HavingMarkWordCondition());
        }
        this.whereSelectFun = new WhereSelectFunImpl(this.conditions, false);
    }

    @Override
    public R or() {
        conditions.add(new OrCondition());
        return (R) this;
    }

    @Override
    public R unite(Consumer<R> r) {
        conditions.add(new LFCondition(ConditionTag.LK));
        r.accept((R) this);
        conditions.add(new RFWordCondition(ConditionTag.RK));
        return (R) this;
    }

    @Override
    public R unite(boolean condition, Consumer<R> r) {
        if (condition) {
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
        if (condition) {
            condSQL(sql);
        }
        return (R) this;
    }

    @Override
    public R condSQL(String sql, Map<String, Object> params) {
        conditions.add(new CondSQLCondition(sql,params));
        return (R) this;
    }

    @Override
    public R condSQL(boolean condition, String sql, Map<String, Object> params) {
        if (condition) {
            condSQL(sql);
        }
        return (R) this;
    }

    @Override
    public R eq(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag, (GetterFun) col, value, ConditionTag.EQ));
        return (R) this;
    }

    @Override
    public R eq(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            eq(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R ueq(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag, (GetterFun) col, value, ConditionTag.UEQ));
        return (R) this;
    }

    @Override
    public R ueq(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            ueq(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R gt(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag, col, value, ConditionTag.GT));
        return (R) this;
    }

    @Override
    public R gt(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            gt(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R lt(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag, col, value, ConditionTag.LT));
        return (R) this;
    }

    @Override
    public R lt(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            lt(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R gtEq(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag, col, value, ConditionTag.GT_EQ));
        return (R) this;
    }

    @Override
    public R gtEq(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            gtEq(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R ltEq(AggTag aggTag, C col, V value) {
        conditions.add(new HavingGroupCondition(aggTag, col, value, ConditionTag.LT_EQ));
        return (R) this;
    }

    @Override
    public R ltEq(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            ltEq(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R between(AggTag aggTag, C col, V start, V end) {
        conditions.add(new HavingBetweenCondition(aggTag, col, start, end, ConditionTag.BETWEEN));
        return (R) this;
    }

    @Override
    public R between(boolean condition, AggTag aggTag, C col, V start, V end) {
        if (condition) {
            between(aggTag, col, start, end);
        }
        return (R) this;
    }

    @Override
    public R notBetween(AggTag aggTag, C col, V start, V end) {
        conditions.add(new HavingBetweenCondition(aggTag, col, start, end, ConditionTag.NOT_BETWEEN));
        return (R) this;
    }

    @Override
    public R notBetween(boolean condition, AggTag aggTag, C col, V start, V end) {
        if (condition) {
            notBetween(aggTag, col, start, end);
        }
        return (R) this;
    }

    @Override
    public R like(AggTag aggTag, C col, V value) {
        conditions.add(new LikeCondition(aggTag, col, value, ConditionTag.LIKE));
        return (R) this;
    }

    @Override
    public R like(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            like(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R likeLeft(AggTag aggTag, C col, V value) {
        conditions.add(new LikeCondition(aggTag, col, value, ConditionTag.LIKE_LEFT));
        return (R) this;
    }

    @Override
    public R likeLeft(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            likeLeft(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R likeRight(AggTag aggTag, C col, V value) {
        conditions.add(new LikeCondition(aggTag, col, value, ConditionTag.LIKE_RIGHT));
        return (R) this;
    }

    @Override
    public R likeRight(boolean condition, AggTag aggTag, C col, V value) {
        if (condition) {
            likeLeft(aggTag, col, value);
        }
        return (R) this;
    }

    @Override
    public R in(AggTag aggTag, C col, V... values) {
        conditions.add(new HavingInCondition(aggTag, col, values, ConditionTag.IN));
        return (R) this;
    }

    @Override
    public R in(boolean condition, AggTag aggTag, C col, V... values) {
        if (condition) {
            in(aggTag, col, values);
        }
        return (R) this;
    }

    @Override
    public R in(AggTag aggTag, C col, Collection... values) {
        conditions.add(new HavingInCondition(aggTag, col, values, ConditionTag.IN));
        return (R) this;
    }

    @Override
    public R in(boolean condition, AggTag aggTag, C col, Collection... values) {
        if (condition) {
            in(aggTag, col, values);
        }
        return (R) this;
    }

    @Override
    public R notIn(AggTag aggTag, C col, V... values) {
        conditions.add(new HavingInCondition(aggTag, col, values, ConditionTag.NOT_IN));
        return (R) this;
    }

    @Override
    public R notIn(boolean condition, AggTag aggTag, C col, V... values) {
        if (condition) {
            notIn(aggTag, col, values);
        }
        return (R) this;
    }

    @Override
    public R notIn(AggTag aggTag, C col, Collection... values) {
        conditions.add(new HavingInCondition(aggTag, col, values, ConditionTag.NOT_IN));
        return (R) this;
    }

    @Override
    public R notIn(boolean condition, AggTag aggTag, C col, Collection... values) {
        if (condition) {
            notIn(aggTag, col, values);
        }
        return (R) this;
    }

    @Override
    public R eq(C col, V value) {
        return eq(null, col, value);
    }

    @Override
    public R eq(boolean condition, C col, V value) {
        return eq(condition,null, col, value);
    }

    @Override
    public R ueq(C col, V value) {
        return ueq(null, col, value);
    }

    @Override
    public R ueq(boolean condition, C col, V value) {
        return ueq(condition,null, col, value);
    }

    @Override
    public R gt(C col, V value) {
        return gt(null, col, value);
    }

    @Override
    public R gt(boolean condition, C col, V value) {
        return gt(condition,null, col, value);
    }

    @Override
    public R lt(C col, V value) {
        return lt(null, col, value);
    }

    @Override
    public R lt(boolean condition, C col, V value) {
        return lt(condition,null, col, value);
    }

    @Override
    public R gtEq(C col, V value) {
        return gtEq(null, col, value);
    }

    @Override
    public R gtEq(boolean condition, C col, V value) {
        return gtEq(condition,null, col, value);
    }

    @Override
    public R ltEq(C col, V value) {
        return ltEq(null, col, value);
    }

    @Override
    public R ltEq(boolean condition, C col, V value) {
        return ltEq(condition,null, col, value);
    }

    @Override
    public R between(C col, V start, V end) {
        return between(null, col, start, end);
    }

    @Override
    public R between(boolean condition, C col, V start, V end) {
        return between(condition,null, col, start, end);
    }

    @Override
    public R notBetween(C col, V start, V end) {
        return notBetween(null, col, start, end);
    }

    @Override
    public R notBetween(boolean condition, C col, V start, V end) {
        return notBetween(condition,null, col, start, end);
    }

    @Override
    public R like(C col, V value) {
        return like(null, col, value);
    }

    @Override
    public R like(boolean condition, C col, V value) {
        return like(condition,null, col, value);
    }

    @Override
    public R likeLeft(C col, V value) {
        return likeLeft(null, col, value);
    }

    @Override
    public R likeLeft(boolean condition, C col, V value) {
        return likeLeft(condition,null, col, value);
    }

    @Override
    public R likeRight(C col, V value) {
        return likeRight(null, col, value);
    }

    @Override
    public R likeRight(boolean condition, C col, V value) {
        return likeRight(condition,null, col, value);
    }

    @Override
    public R in(C col, V... values) {
        return in(null, col, values);
    }

    @Override
    public R in(boolean condition, C col, V... values) {
        return in(condition,null, col, values);
    }

    @Override
    public R in(C col, Collection... values) {
        return in(null, col, values);
    }

    @Override
    public R in(boolean condition, C col, Collection... values) {
        return in(condition,null, col, values);
    }

    @Override
    public R notIn(C col, V... values) {
        return notIn(null, col, values);
    }

    @Override
    public R notIn(boolean condition, C col, V... values) {
        return notIn(condition,null, col, values);
    }

    @Override
    public R notIn(C col, Collection... values) {
        return notIn(null, col, values);
    }

    @Override
    public R notIn(boolean condition, C col, Collection... values) {
        return notIn(condition,null, col, values);
    }

    @Override
    public R isNull(C... cols) {
        for(C col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NULL));
        }
        return (R) this;
    }

    @Override
    public R isNull(boolean condition, C... cols) {
        if(condition){
            isNull(cols);
        }
        return (R) this;
    }

    @Override
    public R isNotNull(C... cols) {
        for(C col: cols){
            conditions.add(new IsNullOrCondition(col, ConditionTag.IS_NOT_NULL));
        }
        return (R) this;
    }

    @Override
    public R isNotNull(boolean condition, C... cols) {
        if(condition){
            isNotNull(cols);
        }
        return (R) this;
    }

    @Override
    public R exists(C... cols) {
        for(C col: cols){
            conditions.add(new ExistsCondition<V>(col));
        }
        return (R) this;
    }

    @Override
    public R exists(boolean condition, C... cols) {
        if(condition){
            exists(cols);
        }
        return (R) this;
    }


    /**
     * ---------------------------------------------------------------------------------------------------
     *
     * @param aggTag
     * @param col
     * @param value
     * @return
     */
    public R eq(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new HavingGroupCondition(aggTag, (GetterFun) col, value, ConditionTag.EQ));
        return (R) this;
    }

    public R eq(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            eq(aggTag, col, value);
        }
        return (R) this;
    }


    public R ueq(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new HavingGroupCondition(aggTag, (GetterFun) col, value, ConditionTag.UEQ));
        return (R) this;
    }


    public R ueq(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            ueq(aggTag, col, value);
        }
        return (R) this;
    }


    public R gt(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new HavingGroupCondition(aggTag, col, value, ConditionTag.GT));
        return (R) this;
    }


    public R gt(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            gt(aggTag, col, value);
        }
        return (R) this;
    }


    public R lt(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new HavingGroupCondition(aggTag, col, value, ConditionTag.LT));
        return (R) this;
    }


    public R lt(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            lt(aggTag, col, value);
        }
        return (R) this;
    }


    public R gtEq(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new HavingGroupCondition(aggTag, col, value, ConditionTag.GT_EQ));
        return (R) this;
    }

    public R gtEq(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            gtEq(aggTag, col, value);
        }
        return (R) this;
    }


    public R ltEq(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new HavingGroupCondition(aggTag, col, value, ConditionTag.LT_EQ));
        return (R) this;
    }


    public R ltEq(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            ltEq(aggTag, col, value);
        }
        return (R) this;
    }


    public R between(AggTag aggTag, C2 col, V2 start, V2 end) {
        conditions.add(new HavingBetweenCondition(aggTag, col, start, end, ConditionTag.BETWEEN));
        return (R) this;
    }


    public R between(boolean condition, AggTag aggTag, C2 col, V2 start, V2 end) {
        if (condition) {
            between(aggTag, col, start, end);
        }
        return (R) this;
    }


    public R notBetween(AggTag aggTag, C2 col, V2 start, V2 end) {
        conditions.add(new HavingBetweenCondition(aggTag, col, start, end, ConditionTag.NOT_BETWEEN));
        return (R) this;
    }

    public R notBetween(boolean condition, AggTag aggTag, C2 col, V2 start, V2 end) {
        if (condition) {
            notBetween(aggTag, col, start, end);
        }
        return (R) this;
    }

    public R like(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new LikeCondition(aggTag, col, value, ConditionTag.LIKE));
        return (R) this;
    }

    public R like(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            like(aggTag, col, value);
        }
        return (R) this;
    }

    public R likeLeft(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new LikeCondition(aggTag, col, value, ConditionTag.LIKE_LEFT));
        return (R) this;
    }


    public R likeLeft(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            likeLeft(aggTag, col, value);
        }
        return (R) this;
    }


    public R likeRight(AggTag aggTag, C2 col, V2 value) {
        conditions.add(new LikeCondition(aggTag, col, value, ConditionTag.LIKE_RIGHT));
        return (R) this;
    }


    public R likeRight(boolean condition, AggTag aggTag, C2 col, V2 value) {
        if (condition) {
            likeLeft(aggTag, col, value);
        }
        return (R) this;
    }


    public R in(AggTag aggTag, C2 col, V2... values) {
        conditions.add(new HavingInCondition(aggTag, col, values, ConditionTag.IN));
        return (R) this;
    }

    public LeftHavingFunImpl in(boolean condition, AggTag aggTag, C2 col, V2... values) {
        if (condition) {
            in(aggTag, col, values);
        }
        return (R) this;
    }


    public R in(AggTag aggTag, C2 col, Collection... values) {
        conditions.add(new HavingInCondition(aggTag, col, values, ConditionTag.IN));
        return (R) this;
    }


    public R in(boolean condition, AggTag aggTag, C2 col, Collection... values) {
        if (condition) {
            in(aggTag, col, values);
        }
        return (R) this;
    }


    public R notIn(AggTag aggTag, C2 col, V2... values) {
        conditions.add(new HavingInCondition(aggTag, col, values, ConditionTag.NOT_IN));
        return (R) this;
    }


    public R notIn(boolean condition, AggTag aggTag, C2 col, V2... values) {
        if (condition) {
            notIn(aggTag, col, values);
        }
        return (R) this;
    }


    public R notIn(AggTag aggTag, C2 col, Collection... values) {
        conditions.add(new HavingInCondition(aggTag, col, values, ConditionTag.NOT_IN));
        return (R) this;
    }

    public R notIn(boolean condition, AggTag aggTag, C2 col, Collection... values) {
        if (condition) {
            notIn(aggTag, col, values);
        }
        return (R) this;
    }

    @Override
    public M ex() {
        List<M> exs = this.exs();
        if (exs != null && exs.size() > 0) {
            return exs.get(0);
        }
        return null;
    }

    @Override
    public R limitPage(int pageNum, int size) {
        conditions.add(new LimitWordCondition<>(pageNum, size));
        return (R) this;
    }

    @Override
    public R orderA(C... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, true));
        return (R) this;
    }

    @Override
    public R orderA(boolean condition, C... cs) {
        if (condition) {
            orderA(cs);
        }
        return (R) this;
    }

    @Override
    public R orderD(C... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, false));
        return (R) this;
    }

    @Override
    public R orderD(boolean condition, C... cs) {
        if (condition) {
            orderD(cs);
        }
        return (R) this;
    }

    public R orderA(C2... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, true));
        return (R) this;
    }

    public R orderA(boolean condition, C2... cs) {
        if (condition) {
            orderA(cs);
        }
        return (R) this;
    }

    public R orderD(C2... cs) {
        List<String> clos = Arrays.stream(cs).map(getterFun -> {
            String cloName = TableHelper.getColNameAndAliasName(getterFun).getLeft();
            return cloName;
        }).collect(Collectors.toList());
        conditions.add(new OrderWordCondition(ConditionTag.ORDER, clos, false));
        return (R) this;
    }

    public R orderD(boolean condition, C2... cs) {
        if (condition) {
            orderD(cs);
        }
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
