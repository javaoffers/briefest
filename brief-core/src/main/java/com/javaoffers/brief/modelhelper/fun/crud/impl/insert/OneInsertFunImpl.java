package com.javaoffers.brief.modelhelper.fun.crud.impl.insert;

import com.javaoffers.brief.modelhelper.core.BaseBatis;
import com.javaoffers.brief.modelhelper.core.BaseBatisImpl;
import com.javaoffers.brief.modelhelper.core.ConditionParse;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.core.MoreSQLInfo;
import com.javaoffers.brief.modelhelper.core.SQLInfo;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ExecutOneFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.mark.OnDuplicateKeyUpdateMark;
import com.javaoffers.brief.modelhelper.fun.condition.mark.ReplaceIntoMark;
import com.javaoffers.brief.modelhelper.fun.crud.insert.OneInsertFun;
import com.javaoffers.brief.modelhelper.log.JqlLogger;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class OneInsertFunImpl<M> implements OneInsertFun<M, GetterFun<M, Object>, Object> {

    private Class<M> mClass;

    private LinkedConditions<Condition> conditions;

    @Override
    public Id ex() {
        BaseBatis instance = BaseBatisImpl.getInstance((HeadCondition) conditions.pollFirst());
        SQLInfo sqlInfo = ((MoreSQLInfo) ConditionParse.conditionParse(conditions)).getSqlInfos().get(0);
        JqlLogger.log.info("SQL: {}", sqlInfo.getSql());
        JqlLogger.log.info("PAM: {}", sqlInfo.getParams());
        List<Id> list = instance.batchInsert(sqlInfo.getSql(), sqlInfo.getParams());
        if(CollectionUtils.isEmpty(list)){
            return Id.EMPTY_ID;
        }
        return list.get(0);
    }

    @Override
    public OneInsertFun<M, GetterFun<M, Object>, Object> col(GetterFun<M, Object> col, Object value) {
        conditions.add(new ColValueCondition(col,value));
        return this;
    }

    @Override
    public OneInsertFun<M, GetterFun<M, Object>, Object> col(boolean condition, GetterFun<M, Object> col, Object value) {
        if(condition){
            col(col,value);
        }
        return this;
    }


    public OneInsertFunImpl(Class<M> mClass, LinkedConditions<Condition> conditions) {
        this.mClass = mClass;
        this.conditions = conditions;
    }

    @Override
    public ExecutOneFun<Id> dupUpdate() {
        this.conditions.add(new OnDuplicateKeyUpdateMark());
        return this;
    }

    @Override
    public ExecutOneFun<Id> dupReplace() {
        this.conditions.add(new ReplaceIntoMark());
        return this;
    }
}
