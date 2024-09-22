package com.javaoffers.brief.modelhelper.fun.crud.impl.update;

import com.javaoffers.brief.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.update.SmartUpdateFun;
import com.javaoffers.brief.modelhelper.fun.crud.update.UpdateFun;

import javax.sql.DataSource;

public class UpdateFunImpl <M, C extends GetterFun<M, Object>, V> implements UpdateFun<M,C,V> {

    private Class modelClass;
    private DataSource dataSource;

    public UpdateFunImpl(Class modelClass) {
        this.modelClass = modelClass;
        this.dataSource = CrudMapperMethodThreadLocal.getExcutorDataSource();
    }

    @Override
    public SmartUpdateFunImpl<M, C, V> updateNull() {
        return new SmartUpdateFunImpl<>(modelClass, true,dataSource);
    }

    @Override
    public SmartUpdateFunImpl<M, C, V> npdateNull() {
        return new SmartUpdateFunImpl<>(modelClass, false,dataSource);
    }
}
