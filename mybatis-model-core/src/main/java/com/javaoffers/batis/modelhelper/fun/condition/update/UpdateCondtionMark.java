package com.javaoffers.batis.modelhelper.fun.condition.update;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

import java.util.Collections;
import java.util.Map;

public class UpdateCondtionMark implements Condition {

    private String tableName;

    private Class modelCalss;

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.UPDATE;
    }

    @Override
    public String getSql() {
        return ConditionTag.UPDATE.getTag() + tableName ;
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }

    public UpdateCondtionMark( Class modelCalss) {
        this.tableName = TableHelper.getTableName(modelCalss);
        this.modelCalss = modelCalss;
    }

    public Class getModelCalss(){
        return this.modelCalss;
    }
}
