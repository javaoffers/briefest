package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.HeadCondition;
import org.springframework.util.Assert;

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
