package com.javaoffers.batis.modelhelper.fun.impl;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.JoinFun;
import com.javaoffers.batis.modelhelper.fun.SelectFun;
import com.javaoffers.batis.modelhelper.fun.WhereFun;
import com.javaoffers.batis.modelhelper.fun.condition.SelectColumnCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereOnCondition;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:  以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 01:55
 */
public class SelectFunStringImpl<M> implements SelectFun<M,String,Object> {

    private M m;
    /**
     * 存放 查询字段
     */
    private LinkedList<Condition> conditions = new LinkedList<>();

    /**
     *  添加查询字段
     * @param cols
     * @return
     */
    @Override
    public SelectFun<M, String, Object> col(String... cols) {
        Stream.of(cols).forEach(col->{
            conditions.add(new SelectColumnCondition(col));
        });
        return this;
    }

    /**
     * 添加所有字段
     * @return
     */
    @Override
    public SelectFun<M, String, Object> colAll() {
        Set<SelectColumnCondition> cols = TableHelper.getColAll(m.getClass()).stream().map(SelectColumnCondition::new).collect(Collectors.toSet());
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
    public <M2> JoinFun<M, M2, String, Object> leftJoin(M2 m2) {
        return new JoinFunStringImpl<>(m,m2,conditions);
    }

    /**
     * 添加 where 语句
     * @return
     */
    @Override
    public WhereFun<M, String, Object, ?> where() {
        return new WhereFunStringImpl<>(conditions);
    }

}
