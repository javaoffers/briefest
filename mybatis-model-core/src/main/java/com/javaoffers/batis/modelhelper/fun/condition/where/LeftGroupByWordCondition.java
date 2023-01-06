package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.where.GroupByWordCondition;

/**
 * @Description: 主表分组之后，子表进行分组. group by aa , xx,xxx,xx
 * group by aa : GroupByWordCondition 主表分组
 * ,xx,xxx,xx  : LeftGroupByWordCondition 子表分组
 * @Auther: create by cmj on 2022/6/5 20:02
 */
public class LeftGroupByWordCondition extends GroupByWordCondition {

    GetterFun[] getterFuns;
    private ConditionTag tag;

    public LeftGroupByWordCondition(GetterFun[] colName, ConditionTag tag) {
        super(colName, null, tag);
        this.getterFuns = colName;
        this.tag = tag;
    }

    public LeftGroupByWordCondition(String[] colName, ConditionTag tag) {
        super(colName,null,tag);
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
