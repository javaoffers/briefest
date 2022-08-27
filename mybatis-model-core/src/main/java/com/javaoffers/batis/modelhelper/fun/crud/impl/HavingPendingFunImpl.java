package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.HavingFun;
import com.javaoffers.batis.modelhelper.fun.crud.HavingPendingFun;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 19:42
 */
public class HavingPendingFunImpl<M,C extends GetterFun, V> implements HavingPendingFun<M,C, V, HavingFunImpl<M,C,V>> {

    private LinkedList<Condition> conditions;

    private WhereSelectFunImpl whereSelectFun;

    public HavingPendingFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.whereSelectFun = new WhereSelectFunImpl(this.conditions);
    }


    @Override
    public M ex() {
        List<M> exs = exs();
        if(exs != null && exs.size()>0){
            return exs.get(0);
        }
        return null;
    }

    @Override
    public HavingFun<M, C, V, HavingFunImpl<M, C, V>> having() {
        HavingFunImpl mcvHavingFun = new HavingFunImpl<>(this.conditions);
        return mcvHavingFun;
    }

    @Override
    public List<M> exs() {
        return whereSelectFun.exs();
    }
}
