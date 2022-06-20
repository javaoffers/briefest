package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.*;
import com.javaoffers.batis.modelhelper.fun.condition.OrderCondition;
import com.javaoffers.batis.modelhelper.fun.condition.SelectColumnCondition;
import com.javaoffers.batis.modelhelper.fun.condition.SelectTableCondition;
import com.javaoffers.batis.modelhelper.fun.crud.JoinFun;
import com.javaoffers.batis.modelhelper.fun.crud.SelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import javafx.scene.control.Tab;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:  以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 01:55
 */
public class SelectFunImpl<M> implements SelectFun<M,GetterFun<M,Object>,Object> {

    private Class<M> mClass;
    /**
     * 存放 查询字段
     */
    private LinkedConditions<Condition> conditions = new LinkedConditions<>();
    {
        conditions.beforeAdd((before,current)->{
            if(before == null){
                return;
            }else{
                if(before instanceof OrderCondition && current instanceof OrderCondition){
                    ((OrderCondition) current).asChild();
                }
            }
        });
    }

    public SelectFunImpl(Class<M> mClass) {
        conditions.add(new SelectTableCondition(TableHelper.getTableName(mClass), mClass));
        this.mClass = mClass;
    }

    /**
     *  添加查询字段
     * @param cols
     * @return
     */
    @Override
    public SelectFun<M, GetterFun<M,Object>, Object> col(GetterFun... cols) {
        Stream.of(cols).forEach(col->{
            conditions.add(new SelectColumnCondition(col));
        });
        return this;
    }

    @Override
    public SelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, GetterFun<M, Object>... cols) {
        if(condition){
            col(cols);
        }
        return this;
    }

    @Override
    public SelectFun<M, GetterFun<M, Object>, Object> col(AggTag aggTag, GetterFun<M, Object>... cols) {
        Stream.of(cols).forEach(col->{
            Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(col);
            String colName = colNameAndAliasName.getLeft();
            String aliasName = colNameAndAliasName.getRight();
            conditions.add(new SelectColumnCondition(aggTag.name()+"("+colName+") as "+aliasName));
        });
        return this;
    }

    @Override
    public SelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, AggTag aggTag, GetterFun<M, Object>... col) {
        if(condition){
            col(aggTag,col);
        }
        return this;
    }

    @Override
    public SelectFun<M, GetterFun<M, Object>, Object> col(AggTag aggTag, GetterFun<M, Object> col, String asName) {
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(col);
        String colName = colNameAndAliasName.getLeft();
        conditions.add(new SelectColumnCondition(aggTag.name()+"("+colName+") as "+ asName));
        return this;
    }

    @Override
    public SelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, AggTag aggTag, GetterFun<M, Object> col, String asName) {
        if(condition){
            col(aggTag, col, asName);
        }
        return this;
    }

    @Override
    public SelectFun<M, GetterFun<M,Object>, Object> col(String... colSql) {
        Stream.of(colSql).forEach(col->{
            conditions.add(new SelectColumnCondition(col));
        });
        return this;
    }

    @Override
    public SelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, String... colSql) {
        if(condition){
            col(colSql);
        }
        return this;
    }

    /**
     * 添加所有字段
     * @return
     */
    @Override
    public SelectFun<M, GetterFun<M,Object>, Object> colAll() {
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
    public <M2, C2 extends GetterFun<M2, Object>> JoinFun<M, M2, C2, Object> leftJoin(ConstructorFun<M2> m2) {
        return new JoinFunmpl(mClass,TableHelper.getClassFromConstructorFun(m2),conditions);
    }

    /**
     * 添加 where 语句
     * @return
     */
    @Override
    public WhereSelectFun<M, Object> where() {
        return new WhereSelectFunImpl(conditions);
    }

}
