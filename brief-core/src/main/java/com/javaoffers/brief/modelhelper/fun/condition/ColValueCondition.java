package com.javaoffers.brief.modelhelper.fun.condition;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateCondition;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * create by cmj.
 */
public class ColValueCondition implements UpdateCondition {

    private String colName;

    private Object value;

    private DBType dbType;

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.INSERT_COL_VALUE;
    }

    @Override
    public String getSql() {
        return this.getConditionTag().getTag() + getExpressionColName();
    }

    @Override
    public Map<String, Object> getParams() {
        HashMap<String, Object> param = new HashMap<>();
        param.put(colName, value);
        return param;
    }

    public ColValueCondition(GetterFun colNameGetterFun, Object value) {
        String colName = TableHelper.getColNameOnly(colNameGetterFun);
        this.colName = colName;
        this.value = value;
    }

    public String getColName() {
        return colName;
    }

    public String getExpressionColName() {
        return this.dbType.getQuote() + colName + this.dbType.getQuote();
    }

    @Override
    public void setHeadCondition(HeadCondition headCondition) {
        Class modelClass = headCondition.getModelClass();
        this.dbType = TableHelper.getTableInfo(modelClass).getDbType();
    }
}
