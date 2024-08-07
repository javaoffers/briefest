package com.javaoffers.brief.modelhelper.fun.condition.select;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: select 语句：查询字段
 * @Auther: create by cmj on 2022/5/2 16:31
 */
public class SelectColumnCondition implements Condition {

    //It may also be a child-query, or a statistical function
    private String colName;

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.SELECT;
    }

    public String getDelimiter(){
        return ", ";
    }

    @Override
    public String getSql() {
        return colName;
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }

    public SelectColumnCondition(String colName) {
        this.colName = colName;
    }

    public SelectColumnCondition( GetterFun colName) {
        this.colName = TableHelper.getColNameForSelect(colName);
    }

    @Override
    public String toString() {
        return colName+" : "+getConditionTag().toString();
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}
