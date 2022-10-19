package com.javaoffers.batis.modelhelper.fun.crud.impl.insert;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.core.ConditionParse;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.core.IdImpl;
import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.core.MoreSQLInfo;
import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.JdbcTemplateCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.batis.modelhelper.fun.crud.insert.OneInsertFun;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class OneInsertFunImpl<M> implements OneInsertFun<M, GetterFun<M, Object>, Object> {

    private Class<M> mClass;

    private LinkedConditions<Condition> conditions;

    @Override
    public Id ex() {
        BaseBatisImpl instance = BaseBatisImpl.getInstance((JdbcTemplateCondition) conditions.pollFirst());
        SQLInfo sqlInfo = ((MoreSQLInfo)ConditionParse.conditionParse(conditions)).getSqlInfos().get(0);
        System.out.println("SQL: "+sqlInfo.getSql());
        System.out.println("PAM: "+sqlInfo.getParams());
        List<Id> list = instance.batchInsert(sqlInfo.getSql(), sqlInfo.getParams());
        if(CollectionUtils.isEmpty(list)){
            return IdImpl.EMPTY_ID;
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
}
