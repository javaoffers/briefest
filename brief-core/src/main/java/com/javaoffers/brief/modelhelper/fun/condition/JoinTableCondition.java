package com.javaoffers.brief.modelhelper.fun.condition;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: left join 语句 table 名称
 * @Auther: create by cmj on 2022/5/4 19:23
 */
public class JoinTableCondition implements Condition {

    private String joinTableName; //表名称

    private ConditionTag tag;

    @Override
    public ConditionTag getConditionTag() {
        return this.tag;
    }

    @Override
    public String getSql() {
        return getConditionTag().getTag() + joinTableName +" ";
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }

    public String getLeftJoinTableName() {
        return joinTableName;
    }

    @Override
    public String toString() {
        return "LeftJoinTableCondition{" +
                "leftJoinTableName='" + joinTableName + '\'' +
                '}';
    }

    public JoinTableCondition(String joinTableName, ConditionTag tag) {
        this.joinTableName = joinTableName;
        this.tag = tag;
    }
}
