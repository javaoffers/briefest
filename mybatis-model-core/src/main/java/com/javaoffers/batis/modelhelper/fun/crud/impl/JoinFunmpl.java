package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.fun.AggTag;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.JoinTableCondition;
import com.javaoffers.batis.modelhelper.fun.crud.JoinFun;
import com.javaoffers.batis.modelhelper.fun.crud.FirstOnFun;
import com.javaoffers.batis.modelhelper.fun.condition.select.SelectColumnCondition;
import com.javaoffers.batis.modelhelper.fun.crud.OnFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description: join 功能实现,以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:11
 */
public class JoinFunmpl<M1,M2,V> implements JoinFun<M1,M2, GetterFun<M2,Object>,V> {

    private LinkedList<Condition> conditions;
    private Class<M1> m1Class;
    private Class<M2> m2Class;
    private String table2Name;

    public JoinFunmpl(Class<M1> mc, Class<M2> m2c, LinkedList<Condition> conditions, ConditionTag tag) {
        this.m1Class = mc;
        this.m2Class = m2c;
        this.conditions = conditions;
        this.table2Name = TableHelper.getTableName(m2c);
        this.conditions.add(new JoinTableCondition(this.table2Name,tag));
    }

    /**
     * 添加查询字段
     * @param colSql
     * @return
     */
    @Override
    public JoinFun<M1, M2,  GetterFun<M2,Object>, V> col(String... colSql) {
        Stream.of(colSql).forEach(col->{conditions.add(new SelectColumnCondition( col));});
        return this;
    }

    @Override
    public JoinFun<M1, M2, GetterFun<M2, Object>, V> col(GetterFun<M2, Object>... cols) {
        Stream.of(cols).forEach(col->conditions.add(new SelectColumnCondition( col)));
        return this;
    }

    @Override
    public JoinFun<M1, M2, GetterFun<M2, Object>, V> col(boolean condition, GetterFun<M2, Object>... cols) {
        if(condition){
            col(cols);
        }
        return this;
    }

    @Override
    public JoinFun<M1, M2, GetterFun<M2, Object>, V> col(AggTag aggTag, GetterFun<M2, Object>... cols) {
        Stream.of(cols).forEach(col->{
            Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(col);
            String colName = colNameAndAliasName.getLeft();
            String aliasName = colNameAndAliasName.getRight();
            conditions.add(new SelectColumnCondition(aggTag.name()+"("+colName+") as "+ aliasName));
        });
        return this;
    }

    @Override
    public JoinFun<M1, M2, GetterFun<M2, Object>, V> col(boolean condition, AggTag aggTag, GetterFun<M2, Object>... col) {
        if(condition){
            col(aggTag,col);
        }
        return this;
    }

    @Override
    public JoinFun<M1, M2, GetterFun<M2, Object>, V> col(AggTag aggTag, GetterFun<M2, Object> col, String asName) {
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(col);
        String colName = colNameAndAliasName.getLeft();
        conditions.add(new SelectColumnCondition(aggTag.name()+"("+colName+") as "
                //join table need SimpleName+asName for diff with main table.
                + this.m2Class.getSimpleName() + asName));
        return this;
    }

    @Override
    public JoinFun<M1, M2, GetterFun<M2, Object>, V> col(boolean condition, AggTag aggTag, GetterFun<M2, Object> col, String asName) {
        if(condition){
            col(aggTag, col, asName);
        }
        return this;
    }

    @Override
    public JoinFun<M1, M2,  GetterFun<M2,Object>, V> colAll() {
        List<String> colAll = TableHelper.getColAll(m2Class);
        for(String col: colAll){
            conditions.add(new SelectColumnCondition(col));
        }
        return this;
    }

    @Override
    public <C1 extends GetterFun<M1, Object>> OnFun<M1, M2, V> on() {
        return  new OnFunImpl(this.m1Class, this.m2Class, this.conditions);
    }

}
