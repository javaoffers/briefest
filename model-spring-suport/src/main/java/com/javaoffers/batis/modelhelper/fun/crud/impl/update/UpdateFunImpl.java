package com.javaoffers.batis.modelhelper.fun.crud.impl.update;

import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.update.SmartUpdateFun;
import com.javaoffers.batis.modelhelper.fun.crud.update.UpdateFun;

public class UpdateFunImpl <M, C extends GetterFun<M, Object>, V> implements UpdateFun<M,C,V> {

    private Class modelClass;


    public UpdateFunImpl(Class modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public SmartUpdateFun<M, C, V> updateNull() {
        return new SmartUpdateFunImpl<>(modelClass, true);
    }

    @Override
    public SmartUpdateFun<M, C, V> npdateNull() {
        return new SmartUpdateFunImpl<>(modelClass, false);
    }
}
