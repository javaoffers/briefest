package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

/**
 * @Description: 支持分组
 * @Auther: create by cmj on 2022/6/5 20:02
 */
public class GroupByWordCondition extends WhereOnCondition implements IgnoreAndOrWordCondition {

    GetterFun[] getterFuns;
    private ConditionTag tag;

    public GroupByWordCondition(GetterFun[] colName, ConditionTag tag) {
        super(colName, null, tag);
        this.getterFuns = getterFuns;
        this.tag = tag;
    }

    public GroupByWordCondition(String[] colName, ConditionTag tag) {
        super(colName,null,tag);
        this.getterFuns = getterFuns;
        this.tag = tag;
    }

    public GroupByWordCondition(String[] colName, Object o, ConditionTag tag) {
        super(colName,o,tag);
    }
    public GroupByWordCondition(GetterFun[] colName, Object o, ConditionTag tag) {
        super(colName,o,tag);
    }

    @Override
    public ConditionTag getTag() {
        return this.tag;
    }

    @Override
    public String getSql() {
       return tag.getTag()+super.getColName()+" ";
    }
}
