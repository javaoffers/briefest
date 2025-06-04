package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.core.MoreSQLInfo;
import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.DeleteFromCondition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 10:44
 */
public class DeleteConditionParse extends AbstractParseCondition {
    public static ConditionTag conditionTag  = ConditionTag.DELETE_FROM;
    @Override
    public SQLStatement doParse(LinkedList<Condition> conditions) {
        return parseDeleteStatement(conditions);
    }

    private SQLStatement parseDeleteStatement(LinkedList<Condition> conditions) {
        DeleteFromCondition condition = (DeleteFromCondition)conditions.pollFirst();
        StringBuilder deleteAppender = new StringBuilder(condition.getSql());
        HashMap<String, Object> deleteParams = new HashMap<>();
        parseWhereCondition(conditions, deleteParams, deleteAppender);
        SQLStatement sqlStatement = SQLStatement.builder().sql(deleteAppender.toString())
                .params(Arrays.asList(deleteParams))
                .aClass(condition.getModelClass())
                .status(true)
                .build();
        return sqlStatement;
    }

}
