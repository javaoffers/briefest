package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: or 关键字
 * @Auther: create by cmj on 2022/5/4 20:01
 */
public class OrCondition implements Condition {
    private String or = " or ";
    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.OR;
    }

    @Override
    public String toString() {
        return "OrCondition{" +
                "or='" + or + '\'' +
                '}';
    }
}
