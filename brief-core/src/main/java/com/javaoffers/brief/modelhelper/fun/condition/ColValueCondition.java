package com.javaoffers.brief.modelhelper.fun.condition;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * create by cmj.
 */
public class ColValueCondition implements Condition {

    private String colName;

    private Object value ;

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

    public ColValueCondition() {
    }

    public String getColName() {
        return colName;
    }

    public String getExpressionColName(){
        return "`"+colName+"`";
    }
}
