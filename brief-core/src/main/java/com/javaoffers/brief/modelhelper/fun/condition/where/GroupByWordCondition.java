package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.IgnoreAndOrWordCondition;

/**
 * @Description: 支持分组
 * @Auther: create by cmj on 2022/6/5 20:02
 */
public class GroupByWordCondition extends KeyWordCondition {

    public GroupByWordCondition(GetterFun[] colName, ConditionTag tag) {
        super(colName, null,tag);

    }

    public GroupByWordCondition(String[] colName, ConditionTag tag) {
        super(colName,null,tag);

    }

    public GroupByWordCondition(String[] colName, Object o, ConditionTag tag) {
        super(colName,o,tag);

    }
    public GroupByWordCondition(GetterFun[] colName, Object o, ConditionTag tag) {
        super(colName,o, tag);
    }

}
