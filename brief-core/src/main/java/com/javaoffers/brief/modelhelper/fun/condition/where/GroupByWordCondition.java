package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.IgnoreAndOrWordCondition;

/**
 * @Description: 支持分组
 * @Auther: create by cmj on 2022/6/5 20:02
 */
public class GroupByWordCondition extends WhereOnCondition implements IgnoreAndOrWordCondition {

    GetterFun[] getterFuns;
    private ConditionTag tag;

    public GroupByWordCondition(GetterFun[] colName, ConditionTag tag) {
        super(colName, null, tag);
        this.getterFuns = colName;
        this.tag = tag;
        cleanAndOrTag();
    }

    public GroupByWordCondition(String[] colName, ConditionTag tag) {
        super(colName,null,tag);
        this.tag = tag;
        cleanAndOrTag();
    }

    public GroupByWordCondition(String[] colName, Object o, ConditionTag tag) {
        super(colName,o,tag);
        cleanAndOrTag();
    }
    public GroupByWordCondition(GetterFun[] colName, Object o, ConditionTag tag) {
        super(colName,o,tag);
        cleanAndOrTag();
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
