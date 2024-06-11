package com.javaoffers.brief.modelhelper.fun.condition.update;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import java.util.Collections;
import java.util.Map;

public class UpdateSetCondition implements Condition {

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

    public UpdateSetCondition(Class modelCalss) {
        this.tableName = TableHelper.getTableName(modelCalss);
        this.modelCalss = modelCalss;
    }

    public Class getModelCalss(){
        return this.modelCalss;
    }

}
