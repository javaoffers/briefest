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

    private String leftJoinTableName; //表名称

    private ConditionTag tag;

    @Override
    public ConditionTag getConditionTag() {
        return this.tag;
    }

    @Override
    public String getSql() {
        return getConditionTag().getTag() + leftJoinTableName +" ";
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
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

    public JoinTableCondition(String leftJoinTableName, ConditionTag tag) {
        this.leftJoinTableName = leftJoinTableName;
        this.tag = tag;
    }
}
