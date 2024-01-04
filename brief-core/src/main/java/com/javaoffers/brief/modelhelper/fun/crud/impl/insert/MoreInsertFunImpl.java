package com.javaoffers.brief.modelhelper.fun.crud.impl.insert;

import com.javaoffers.brief.modelhelper.core.BaseBrief;
import com.javaoffers.brief.modelhelper.core.BaseBriefImpl;
import com.javaoffers.brief.modelhelper.core.StatementParserAdepter;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.core.MoreSQLInfo;
import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ExecutMoreFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.mark.OnDuplicateKeyUpdateMark;
import com.javaoffers.brief.modelhelper.fun.condition.mark.ReplaceIntoMark;
import com.javaoffers.brief.modelhelper.fun.crud.insert.MoreInsertFun;
import com.javaoffers.brief.modelhelper.log.JqlLogger;

import java.util.ArrayList;
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
    public List<Id> exs() {
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //Parse SQL select and execute.
        BaseBrief instance = BaseBriefImpl.getInstance((HeadCondition) conditions.peekFirst());
        MoreSQLInfo sqlInfos = (MoreSQLInfo) StatementParserAdepter.statementParse(conditions);
        List<SQLStatement> sqlInfosList = sqlInfos.getSqlStatements();
        List<Id> list = new ArrayList<>();
        sqlInfosList.forEach(sqlInfo -> {
            JqlLogger.infoSql("SQL: {}", sqlInfo.getSql());
            JqlLogger.infoSql("PAM: {}", sqlInfo.getParams());
            list.addAll(instance.batchInsert(sqlInfo.getSql(), sqlInfo.getParams()));
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
