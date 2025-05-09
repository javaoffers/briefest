package com.javaoffers.brief.modelhelper.fun.condition.insert;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.ShardingCondition;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;

public class InsertIntoCondition implements InsertCondition, ShardingCondition {

    private String tableName;

    private Class modelClass;

    private boolean shardingState;

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.INSERT_INTO;
    }

    @Override
    public String getSql() {
        return getConditionTag().getTag() + tableName;
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

    public String getTableName() {
        return tableName;
    }

    @Override
    public boolean isDone() {
        return this.shardingState;
    }

    @Override
    public ShardingCondition clone(String tableName) {
        InsertIntoCondition clone = new InsertIntoCondition(modelClass);
        clone.tableName = tableName;
        return clone;
    }

    @Override
    public void shardingTableName(String tableName) {
        Assert.isTrue(!shardingState,"Duplicate sharding of the same table is not allowed");
        this.tableName = tableName;
        this.shardingState = true;
    }

}
