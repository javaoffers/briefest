package com.javaoffers.brief.modelhelper.fun.condition.update;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import java.util.Collections;
import java.util.Map;

public class UpdateCondtionMark implements Condition {

    private String tableName;

    private Class modelCalss;

    private HeadCondition headCondition;

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

    public UpdateCondtionMark( Class modelCalss, HeadCondition headCondition) {
        this.tableName = TableHelper.getTableName(modelCalss);
        this.modelCalss = modelCalss;
        this.headCondition = headCondition;
    }

    public Class getModelCalss(){
        return this.modelCalss;
    }

    public long getNextLong(){
        Assert.isTrue(this.headCondition != null ,"head condition is null");
        return this.headCondition.getNextLong();
    }
}
