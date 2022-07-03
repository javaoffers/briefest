package com.javaoffers.batis.modelhelper.fun.crud.impl.delete;

import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.delete.DeleteFun;

public class DeleteFunImpl<M> implements DeleteFun<M, GetterFun<M,Object>,Object> {

    private LinkedConditions<Condition> conditions = new LinkedConditions<>();

    private Class modelClass;

    @Override
    public DeleteWhereFun<M, GetterFun<M, Object>, Object> where() {
        return new DeleteWhereFun<>(conditions,this.modelClass);
    }

    public DeleteFunImpl(Class modelClass) {
        this.modelClass = modelClass;
    }
}
