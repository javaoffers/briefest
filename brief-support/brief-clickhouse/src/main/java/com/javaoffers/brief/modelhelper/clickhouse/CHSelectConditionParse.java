package com.javaoffers.brief.modelhelper.clickhouse;

import com.javaoffers.brief.modelhelper.core.parse.SelectConditionParse;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.condition.select.SelectColumnCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;

import java.util.LinkedList;

public class CHSelectConditionParse extends SelectConditionParse {

    @Override
    public void beforeSelectCol(LinkedList<Condition> conditions, StringBuilder selectCols) {
        for(Condition condition : conditions){
            if(condition instanceof SelectColumnCondition){
                String sql = condition.getSql();
                String[] split = sql.split("\\.");
                if(split.length == 2){
                    ((SelectColumnCondition) condition).setColName(split[1]);
                }
            }
        }
    }
}
