package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.crud.JoinFun;
import com.javaoffers.batis.modelhelper.fun.crud.OnFun;
import com.javaoffers.batis.modelhelper.fun.condition.SelectColumnCondition;

import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * @Description: join 功能实现,以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:11
 */
public class JoinFunStringImpl<M1,M2,V> implements JoinFun<M1,M2,String,V> {

    private LinkedList<Condition> conditions;
    private Class<M1> m1Class;
    private Class<M2> m2Class;
    private String table2Name;

    public JoinFunStringImpl(Class<M1> mc, Class<M2> m2c, LinkedList<Condition> conditions) {
        this.m1Class = mc;
        this.m2Class = m2c;
        this.conditions = conditions;
    }

    /**
     * 添加查询字段
     * @param cols
     * @return
     */
    @Override
    public JoinFun<M1, M2, String, V> col(String... cols) {
        Stream.of(cols).forEach(col->{conditions.add(new SelectColumnCondition(col));});
        return this;
    }

    @Override
    public OnFun<M1, M2, String, V> on() {
        return new OnFunStringImpl<>(conditions);
    }
}
