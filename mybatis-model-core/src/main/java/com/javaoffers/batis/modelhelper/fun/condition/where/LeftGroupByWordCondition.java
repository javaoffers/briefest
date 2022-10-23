package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.where.GroupByWordCondition;

/**
 * @Description: 支持分组. group by
 * @Auther: create by cmj on 2022/6/5 20:02
 */
public class LeftGroupByWordCondition extends GroupByWordCondition {

    GetterFun[] getterFuns;
    private ConditionTag tag;

    public LeftGroupByWordCondition(GetterFun[] colName, ConditionTag tag) {
        super(colName, null, tag);
        this.getterFuns = getterFuns;
        this.tag = tag;
    }

    public LeftGroupByWordCondition(String[] colName, ConditionTag tag) {
        super(colName,null,tag);
        this.getterFuns = getterFuns;
        this.tag = tag;
    }

    @Override
    public ConditionTag getTag() {
        return this.tag;
    }

    @Override
    public String getSql() {
       return ","+super.getColName()+" ";
    }
}
