package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

/**
 * @Description: select 语句：查询字段
 * @Auther: create by cmj on 2022/5/2 16:31
 */
public class SelectColumnCondition implements Condition  {

    private String colName;

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.SELECT;
    }

    public SelectColumnCondition(String colName) {
        this.colName = colName;
    }

    public SelectColumnCondition(GetterFun colName) {
        this.colName = TableHelper.getColName(colName);
    }

    @Override
    public String toString() {
        return colName+" : "+getConditionTag().toString();
    }
}
