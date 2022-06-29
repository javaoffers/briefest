package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

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
        return this.getConditionTag().getTag() + colName;
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

}
