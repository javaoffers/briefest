package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.JoinTableCondition;
import com.javaoffers.brief.modelhelper.fun.condition.KeyWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.mark.OnConditionMark;
import com.javaoffers.brief.modelhelper.fun.condition.mark.WhereConditionMark;
import com.javaoffers.brief.modelhelper.fun.condition.select.SelectColumnCondition;
import com.javaoffers.brief.modelhelper.fun.condition.select.SelectTableCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereOnCondition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 10:44
 */
public class SelectConditionParse extends AbstractParseCondition {
    public static ConditionTag conditionTag  = ConditionTag.SELECT_FROM;
    @Override
    public SQLStatement doParse(LinkedList<Condition> conditions) {
        return parseSelect(conditions);
    }

    private SQLStatement parseSelect(LinkedList<Condition> conditions) {
        HashMap<String, Object> params = new HashMap<>();
        String and = ConditionTag.AND.getTag();

        //获取表
        Condition condition = conditions.pollFirst();
        String fromTable = condition.getSql();

        //生成select语句
        StringBuilder selectCols = new StringBuilder(ConditionTag.SELECT.getTag());
        if(conditions.peekFirst() == null || !(conditions.peekFirst() instanceof SelectColumnCondition)) {
            //没有要查询的字段
            return null;
        }
        beforeSelectCol(conditions, selectCols);
        parseSelectClo(conditions, selectCols);

        //append table
        StringBuilder fromTables = new StringBuilder(fromTable);
        //appender on xxx
        StringBuilder onCondition = new StringBuilder();

        //是否存在 join table
        while (conditions.peekFirst() instanceof JoinTableCondition) {
            Condition leftJoin = conditions.pollFirst();
            parseSelectClo(conditions, selectCols);
            //append join table name
            fromTables.append(leftJoin.getSql());
            //指定on条件
            Condition on = null;
            if (conditions.peekFirst() instanceof OnConditionMark) {//.on()
                onCondition.append(conditions.pollFirst().getSql());
                onCondition.append("1=1");
                for (; !(conditions.peekFirst() instanceof WhereConditionMark); ) {//如果没有 .where()
                    on = conditions.pollFirst();
                    if (on instanceof OrCondition) {
                        and = on.getSql();
                        on = conditions.pollFirst();
                    }else if(on instanceof WhereOnCondition){
                        and = ((WhereOnCondition) on).getAndOrTag();
                    }else if(on instanceof JoinTableCondition){
                        conditions.addFirst(on);
                        break;
                    }
                    whereAndOn(params, onCondition, and, on);
                }
            }
            fromTables.append(onCondition);
            onCondition = new StringBuilder();
        }

        selectCols.append(fromTables);

        // where
        parseWhereCondition(conditions, params, selectCols);

        return SQLStatement.builder()
                .aClass(((SelectTableCondition) condition).getmClass())
                .params(Arrays.asList(params))
                .sql(selectCols.toString())
                .status(true)
                .build();
    }

    //此方法通常其他类型的DB做适配。在解析select字段之前做一次操作。
    public void beforeSelectCol(LinkedList<Condition> conditions, StringBuilder selectCols) {
        //ignore
    }

    public void parseSelectClo(LinkedList<Condition> conditions, StringBuilder selectB) {
        SelectColumnCondition selectCol;
        for (; conditions.peekFirst() instanceof SelectColumnCondition; ) {
            selectCol = (SelectColumnCondition) conditions.pollFirst();
            String sql = selectCol.getSql();
            if(selectCol instanceof KeyWordCondition){
                selectB.append(sql);
            }else{
                selectB.append(selectCol.getDelimiter());
                selectB.append(sql);
            }
        }
    }

}
