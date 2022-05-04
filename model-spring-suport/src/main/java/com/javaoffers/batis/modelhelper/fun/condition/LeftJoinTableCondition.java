package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: left join 语句 table 名称
 * @Auther: create by cmj on 2022/5/4 19:23
 */
public class LeftJoinTableCondition implements Condition {

    private String leftJoinTableName; //表名称

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.LEFT_JOIN;
    }

    public LeftJoinTableCondition(String leftJoinTableName) {
        this.leftJoinTableName = leftJoinTableName;
    }

    public String getLeftJoinTableName() {
        return leftJoinTableName;
    }

    @Override
    public String toString() {
        return "LeftJoinTableCondition{" +
                "leftJoinTableName='" + leftJoinTableName + '\'' +
                '}';
    }
}
