package com.javaoffers.batis.modelhelper.fun.crud.impl.insert;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.core.ConditionParse;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.batis.modelhelper.fun.crud.insert.MoreInsertFun;

import java.util.Collection;
import java.util.List;

public class MoreInsertFunImpl<M> implements MoreInsertFun<M, GetterFun<M, Object>, Object> {

    private Class<M> mClass;

    private LinkedConditions<Condition> conditions;

    /**
     * Returns the primary key id after success, and the order of multiple ids is consistent with the inserted model
     * @return ids
     */
    @Override
    public List<Id> ex() {
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //Parse SQL select and execute.
        SQLInfo sqlInfo = ConditionParse.conditionParse(conditions);
        System.out.println("SQL: "+sqlInfo.getSql());
        System.out.println("PAM: "+sqlInfo.getParams());
        List<Id> list = BaseBatisImpl.baseBatis.batchInsert(sqlInfo.getSql(), sqlInfo.getParams());
        return list;
    }


    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(M model) {
        conditions.add(new InsertAllColValueCondition(mClass, model));
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, M model) {
        if(condition){
            colAll(model);
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(M... models) {
        for(M m: models){
            colAll(m);
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, M... models) {
        if(condition){
            colAll(models);
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(Collection<M> models) {
        if(models!=null && models.size() > 0){
            models.forEach(model->{
                colAll(model);
            });
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, Collection<M> models) {
        if(condition){
            colAll(models);
        }
        return this;
    }

    public MoreInsertFunImpl(Class<M> mClass, LinkedConditions<Condition> conditions) {
        this.mClass = mClass;
        this.conditions = conditions;
    }
}
