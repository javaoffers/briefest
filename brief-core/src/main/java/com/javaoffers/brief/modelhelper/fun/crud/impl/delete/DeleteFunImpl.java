package com.javaoffers.brief.modelhelper.fun.crud.impl.delete;

import com.javaoffers.brief.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.DeleteFromCondition;
import com.javaoffers.brief.modelhelper.fun.crud.delete.DeleteFun;
import com.javaoffers.brief.modelhelper.fun.crud.delete.DeleteWhereFun;

public class DeleteFunImpl<M> implements DeleteFun<M, GetterFun<M,Object>,Object> {

    private LinkedConditions<Condition> conditions = new LinkedConditions<>();

    private Class modelClass;


    public DeleteFunImpl(Class modelClass) {
        this.modelClass = modelClass;
        this.conditions.add(new HeadCondition(CrudMapperMethodThreadLocal.getExcutorDataSource(), modelClass));
        this.conditions.add(new DeleteFromCondition(modelClass));
    }

    @Override
    public DeleteWhereFunImpl<M, GetterFun<M, Object>, Object> where() {
        return new DeleteWhereFunImpl(conditions,this.modelClass);
    }
}
