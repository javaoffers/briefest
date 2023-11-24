package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.DeleteFromCondition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 10:44
 */
public class DeleteConditionParse extends AbstractParseCondition {
    public static ConditionTag conditionTag  = ConditionTag.DELETE_FROM;
    @Override
    public SQLStatement doParse(LinkedList<Condition> conditions) {
        return parseDelete(conditions);
    }

    private SQLStatement parseDelete(LinkedList<Condition> conditions) {
        DeleteFromCondition condition = (DeleteFromCondition)conditions.pollFirst();
        StringBuilder deleteAppender = new StringBuilder(condition.getSql());
        HashMap<String, Object> deleteParams = new HashMap<>();
        parseWhereCondition(conditions, deleteParams, deleteAppender);
        return SQLStatement.builder().sql(deleteAppender.toString())
                .params(Arrays.asList(deleteParams))
                .aClass(condition.getModelClass())
                .status(true)
                .build();
    }
}
