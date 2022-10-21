package com.javaoffers.batis.modelhelper.fun.condition.update;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.HeadCondition;

/**
 * @author create by cmj
 */
public interface UpdateCondition extends Condition {

    void setHeadCondition(HeadCondition headCondition);
}
