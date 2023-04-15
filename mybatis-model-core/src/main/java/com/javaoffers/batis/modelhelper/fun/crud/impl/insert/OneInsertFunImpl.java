package com.javaoffers.batis.modelhelper.fun.crud.impl.insert;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.core.ConditionParse;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.core.MoreSQLInfo;
import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.HeadCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.mark.OnDuplicateKeyUpdateMark;
import com.javaoffers.batis.modelhelper.fun.condition.mark.ReplaceIntoMark;
import com.javaoffers.batis.modelhelper.fun.crud.insert.OneInsertFun;
import com.javaoffers.batis.modelhelper.log.JqlLogger;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class OneInsertFunImpl<M> implements OneInsertFun<M, GetterFun<M, Object>, Object> {

    private Class<M> mClass;

    private LinkedConditions<Condition> conditions;

    @Override
    public Id ex() {
        BaseBatisImpl instance = BaseBatisImpl.getInstance((HeadCondition) conditions.pollFirst());
        SQLInfo sqlInfo = ((MoreSQLInfo)ConditionParse.conditionParse(conditions)).getSqlInfos().get(0);
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
