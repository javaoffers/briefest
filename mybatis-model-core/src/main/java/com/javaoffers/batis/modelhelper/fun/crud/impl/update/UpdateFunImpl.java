package com.javaoffers.batis.modelhelper.fun.crud.impl.update;

import com.javaoffers.batis.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.update.SmartUpdateFun;
import com.javaoffers.batis.modelhelper.fun.crud.update.UpdateFun;
import org.springframework.jdbc.core.JdbcTemplate;

public class UpdateFunImpl <M, C extends GetterFun<M, Object>, V> implements UpdateFun<M,C,V> {

    private Class modelClass;
    private JdbcTemplate jdbcTemplate;

    public UpdateFunImpl(Class modelClass) {
        this.modelClass = modelClass;
        this.jdbcTemplate = CrudMapperMethodThreadLocal.getExcutorJdbcTemplate();
    }

    @Override
    public SmartUpdateFun<M, C, V> updateNull() {
        return new SmartUpdateFunImpl<>(modelClass, true,jdbcTemplate);
    }

    @Override
    public SmartUpdateFun<M, C, V> npdateNull() {
        return new SmartUpdateFunImpl<>(modelClass, false,jdbcTemplate);
    }
}
