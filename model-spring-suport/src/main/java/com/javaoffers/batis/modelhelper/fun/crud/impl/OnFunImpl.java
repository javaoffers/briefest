package com.javaoffers.batis.modelhelper.fun.crud.impl;


import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.BetweenCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ExistsCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OnColumnFunCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OnValueFunCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereOnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.OnFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:13
 */
public  class OnFunImpl<M1,M2,V>  implements OnFun<M1,M2, GetterFun<M1,Object>, GetterFun<M2,Object>, V>, ExecutFun<M1>
{
    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl<M1,Object> whereFunString1;

    public OnFunImpl(LinkedList<Condition> conditions) {
       this.conditions = conditions;
       this.whereFunString1 = new WhereSelectFunImpl<>(conditions);
    }

    @Override
    public M1 ex() {
        return whereFunString1.ex();
    }

    @Override
    public List<M1> exs() {
        return whereFunString1.exs();
    }


    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> oeq(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col,col2, ConditionTag.EQ));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> oueq(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col,col2, ConditionTag.UEQ));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ogt(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col,col2, ConditionTag.GT));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> olt(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col,col2, ConditionTag.LT));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ogtEq(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col,col2, ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> oltEq(GetterFun<M1, Object> col, GetterFun<M2, Object> col2) {
        conditions.add(new OnColumnFunCondition(col,col2, ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public WhereSelectFun<M1, V> where() {
        return new WhereSelectFunImpl(conditions);
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> or() {
        conditions.add(new OrCondition());
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> eq(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col,value,ConditionTag.EQ));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ueq(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col,value,ConditionTag.UEQ));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> gt(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col,value,ConditionTag.GT));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> lt(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col,value,ConditionTag.LT));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> gtEq(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col,value,ConditionTag.GT_EQ));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ltEq(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col,value,ConditionTag.LT_EQ));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> between(GetterFun<M2, Object> col, V start, V end) {
        conditions.add(new BetweenCondition(col,start, end, ConditionTag.BETWEEN));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> like(GetterFun<M2, Object> col, V value) {
        conditions.add(new OnValueFunCondition(col,value,ConditionTag.LIKE));
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> in(GetterFun<M2, Object> col, V... values) {
        Stream.of(values).forEach(value -> {
            conditions.add(new OnValueFunCondition(col,value,ConditionTag.IN));
        } );
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> exists(String existsSql) {
        conditions.add(new ExistsCondition<V>(existsSql));
        return this;
    }
}
