package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.*;
import com.javaoffers.batis.modelhelper.fun.condition.SelectColumnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.JoinFun;
import com.javaoffers.batis.modelhelper.fun.crud.SelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:  以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 01:55
 */
public class SelectFunStringImpl<M> implements SelectFun<M,GetterFun,Object> {

    private Class<M> mClass;
    /**
     * 存放 查询字段
     */
    private LinkedList<Condition> conditions = new LinkedList<>();

    public SelectFunStringImpl(Class<M> mClass) {
        this.mClass = mClass;
    }

    /**
     *  添加查询字段
     * @param cols
     * @return
     */
    @Override
    public SelectFun<M, GetterFun, Object> col(GetterFun... cols) {
        Stream.of(cols).forEach(col->{
            conditions.add(new SelectColumnCondition(col));
        });
        return this;
    }

    @Override
    public SelectFun<M, GetterFun, Object> col(String... colSql) {
        Stream.of(colSql).forEach(col->{
            conditions.add(new SelectColumnCondition(col));
        });
        return this;
    }

    /**
     * 添加所有字段
     * @return
     */
    @Override
    public SelectFun<M, GetterFun, Object> colAll() {
        Set<SelectColumnCondition> cols = TableHelper.getColAll(mClass).stream().map(SelectColumnCondition::new).collect(Collectors.toSet());
        conditions.addAll(cols);
        return this;
    }

    /**
     * left join 其他表
     * @param m2 model类( left join m2)
     * @param <M2>
     * @return
     */
    @Override
    public <M2> JoinFun<M, M2, GetterFun, Object> leftJoin(M2 m2) {
        return new JoinFunStringImpl(mClass,m2.getClass(),conditions);
    }

    /**
     * 添加 where 语句
     * @return
     */
    @Override
    public WhereFun<M, GetterFun, Object, ?> where() {
        return new WhereFunStringImpl<>(conditions);
    }

}
