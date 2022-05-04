package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

/**
 * @Description: select from  语句 table 名称
 * @Auther: create by cmj on 2022/5/4 19:23
 */
public class SelectTableCondition implements Condition {

    private String fromTableName; //表名称

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.FROM;
    }


    public SelectTableCondition(String fromTableName) {
        this.fromTableName = fromTableName;
    }

    public String getFromTableName() {
        return fromTableName;
    }

    @Override
    public String toString() {
        return "SelectTableCondition{" +
                "fromTableName='" + fromTableName + '\'' +
                '}';
    }
}
