package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.mark.WhereConditionMark;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereOnCondition;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 10:48
 */
public interface ParseCondition {

    SQLStatement parse(LinkedList<Condition> conditions);


    //解析Where 语句
    default void parseWhereCondition(LinkedList<Condition> conditions, HashMap<String, Object> params, StringBuilder sql) {
        String and = ConditionTag.AND.getTag();
        if (conditions.peekFirst() instanceof WhereConditionMark) {
            sql.append(conditions.pollFirst().getSql());
            sql.append(" 1=1 ");
            WhereOnCondition where = null;
            for (; conditions.peekFirst() instanceof WhereOnCondition && (where = (WhereOnCondition) conditions.pollFirst()) != null; ) {
                if (where instanceof OrCondition) {
                    and = where.getSql();
                    where = (WhereOnCondition) conditions.pollFirst();
                } else {
                    and = where.getAndOrTag();
                }
                whereAndOn(params, sql, and, where);
            }
        }
    }

    //拼接and
    default void whereAndOn(HashMap<String, Object> params, StringBuilder selectB, String andOr, Condition where) {
        selectB.append(andOr);
        selectB.append(where.getSql());
        params.putAll(where.getParams());
    }

}
