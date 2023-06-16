package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;

/**
 * @description:
 * @author: create by cmj on 2022/10/22 09:37
 */
public interface WhereCondition extends Condition {

    public void setHeadCondition(HeadCondition headCondition);

    public void setAndOrTag(String andOrTag);

    public String getAndOrTag();

    public long getNextLong();

    public String getNextTag();

    public void cleanAndOrTag();

}
