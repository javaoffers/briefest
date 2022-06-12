package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @Description: 支持分组
 * @Auther: create by cmj on 2022/6/5 20:02
 */
public class LeftGroupByCondition extends GroupByCondition {

    GetterFun[] getterFuns;
    private ConditionTag tag;

    public LeftGroupByCondition(GetterFun[] colName, ConditionTag tag) {
        super(colName, null, tag);
        this.getterFuns = getterFuns;
        this.tag = tag;
    }

    public LeftGroupByCondition(String[] colName, ConditionTag tag) {
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
