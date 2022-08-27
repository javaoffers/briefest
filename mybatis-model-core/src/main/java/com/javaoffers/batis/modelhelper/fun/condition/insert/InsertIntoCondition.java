package com.javaoffers.batis.modelhelper.fun.condition.insert;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;

public class InsertIntoCondition implements InsertCondition {

    String tableName;

    Class modelClass;

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.INSERT_INTO;
    }

    @Override
    public String getSql() {
        return getConditionTag().getTag()+tableName;
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }

    public InsertIntoCondition(Class modelClass) {
        String tableName = TableHelper.getTableName(modelClass);
        this.tableName = tableName;
        this.modelClass = modelClass;
    }

    public Class getModelClass() {
        return modelClass;
    }

    @Override
    public String getValuesSql() {
        return StringUtils.EMPTY;
    }
}
