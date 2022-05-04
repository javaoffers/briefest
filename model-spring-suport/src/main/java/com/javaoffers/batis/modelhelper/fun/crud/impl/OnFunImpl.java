package com.javaoffers.batis.modelhelper.fun.crud.impl;


import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.OnColumnFunCondition;
import com.javaoffers.batis.modelhelper.fun.crud.OnFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:13
 */
public  class OnFunImpl<M1,M2,V>  implements OnFun<M1,M2, GetterFun<M1,Object>, GetterFun<M2,Object>, V>, ExecutFun<M1>
{
    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl<M1,Object> whereFunString;
    /**
     * K: table1 的字段名称
     * V: table2 的子度名称
     */
    private M1 m1;
    private M2 m2;
    private String table1Name;
    private String table2Name;

    public OnFunImpl(LinkedList<Condition> conditions) {
       this.conditions = conditions;
       this.whereFunString = new WhereSelectFunImpl<>(conditions);
    }



    @Override
    public M1 ex() {
        return whereFunString.ex();
    }

    @Override
    public List<M1> exs() {
        return whereFunString.exs();
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
        whereFunString.or();
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> eq(GetterFun<M1, Object> col, V value) {
        whereFunString.eq(col,value);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ueq(GetterFun<M1, Object> col, V value) {
        whereFunString.ueq(col,value);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> gt(GetterFun<M1, Object> col, V value) {
        whereFunString.gt(col,value);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> lt(GetterFun<M1, Object> col, V value) {
        whereFunString.lt(col,value);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> gtEq(GetterFun<M1, Object> col, V value) {
        whereFunString.gtEq(col,value);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> ltEq(GetterFun<M1, Object> col, V value) {
        whereFunString.ltEq(col,value);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> between(GetterFun<M1, Object> col, V start, V end) {
        whereFunString.between(col,start,end);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> like(GetterFun<M1, Object> col, V value) {
        whereFunString.like(col,value);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> in(GetterFun<M1, Object> col, V... values) {
        whereFunString.in(col,values);
        return this;
    }

    @Override
    public OnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V> exists(String existsSql) {
        whereFunString.exists(existsSql);
        return this;
    }
}
