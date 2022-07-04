package com.javaoffers.batis.modelhelper.fun.crud.impl.update;

import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;
import com.javaoffers.batis.modelhelper.fun.crud.update.PrepareWhereModifyFun;

public class PrepareWhereModifyFunImpl<M, C extends GetterFun<M, Object>, V> implements PrepareWhereModifyFun<M, C, V> {

    private LinkedConditions<Condition> conditions;

    private Class modelClass;

    public PrepareWhereModifyFunImpl(LinkedConditions<Condition> conditions, Class modelClass) {
        this.conditions = conditions;
        this.modelClass = modelClass;
    }

    @Override
    public WhereModifyFun<M, V> where() {
        return new WhereModifyFunImpl<M, V>(conditions, modelClass);
    }

}
