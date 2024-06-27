package com.javaoffers.brief.modelhelper.sqlserver;

import com.javaoffers.brief.modelhelper.core.parse.SelectConditionParse;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;

import java.util.LinkedList;

public class SqlServerSelectConditionParse extends SelectConditionParse {

    @Override
    public void beforeSelectCol(LinkedList<Condition> conditions, StringBuilder selectCols) {
        //检测是否有分页
        Condition condition = conditions.peekLast();
        if(condition instanceof LimitWordCondition){

            //弹出mysql的limit语法
            LimitWordCondition limit = (LimitWordCondition)conditions.pollLast();

            //生成oracle 分页语法
            SqlServerLimitWordCondition oracleLimitWordCondition = new SqlServerLimitWordCondition(limit.pageNum, limit.pageSize);
            oracleLimitWordCondition.setHeadCondition(((LimitWordCondition<?>) condition).getHeadCondition());

            conditions.addLast(oracleLimitWordCondition);

        }
    }
}
