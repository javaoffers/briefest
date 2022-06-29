package com.javaoffers.batis.modelhelper.fun.condition.update;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.Map;

/**
 * all Col value update include null value
 * @author create by cmj
 */
public class UpdateAllColValueCondition implements UpdateCondition {

    boolean isUpdateNull;

    Class modelClass;

    Map<String,Object> param;

    Object model;


    @Override
    public ConditionTag getConditionTag() {
        return null;
    }

    @Override
    public String getSql() {
        return null;
    }

    @Override
    public Map<String, Object> getParams() {
        return null;
    }

    public UpdateAllColValueCondition(boolean isUpdateNull, Class modelClass, Object model) {
        this.isUpdateNull = isUpdateNull;
        this.modelClass = modelClass;
        this.model = model;
    }
}
