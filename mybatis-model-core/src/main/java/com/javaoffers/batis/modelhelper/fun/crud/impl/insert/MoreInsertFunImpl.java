package com.javaoffers.batis.modelhelper.fun.crud.impl.insert;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.core.ConditionParse;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.core.MoreSQLInfo;
import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ExecutMoreFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.HeadCondition;
import com.javaoffers.batis.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.mark.OnDuplicateKeyUpdateMark;
import com.javaoffers.batis.modelhelper.fun.condition.mark.ReplaceIntoMark;
import com.javaoffers.batis.modelhelper.fun.crud.insert.MoreInsertFun;
import com.javaoffers.batis.modelhelper.log.JqlLogger;
import com.javaoffers.batis.modelhelper.log.time.CostTimeLogger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MoreInsertFunImpl<M> implements MoreInsertFun<M, GetterFun<M, Object>, Object> {

    private Class<M> mClass;

    private LinkedConditions<Condition> conditions;

    /**
     * Returns the primary key id after success, and the order of multiple ids is consistent with the inserted model
     * @return ids
     */
    @Override
    public List<Id> exs() {
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //Parse SQL select and execute.
        BaseBatisImpl instance = BaseBatisImpl.getInstance((HeadCondition) conditions.pollFirst());
        MoreSQLInfo sqlInfos = (MoreSQLInfo)ConditionParse.conditionParse(conditions);
        List<SQLInfo> sqlInfosList = sqlInfos.getSqlInfos();
        List<Id> list = new ArrayList<>();
        sqlInfosList.forEach(sqlInfo -> {
            JqlLogger.log.info("SQL: {}", sqlInfo.getSql());
            JqlLogger.log.info("PAM: {}", sqlInfo.getParams());
            list.addAll((List)CostTimeLogger.info(()->{
                return instance.batchInsert(sqlInfo.getSql(), sqlInfo.getParams());
            }));
        });
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

    @Override
    public ExecutMoreFun<Id> dupUpdate() {
        this.conditions.add(new OnDuplicateKeyUpdateMark());
        return this;
    }

    @Override
    public ExecutMoreFun<Id> dupReplace() {
        this.conditions.add(new ReplaceIntoMark());
        return this;
    }
}
