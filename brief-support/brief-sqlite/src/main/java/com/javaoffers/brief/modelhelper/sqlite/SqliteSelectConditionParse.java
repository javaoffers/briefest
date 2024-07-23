package com.javaoffers.brief.modelhelper.sqlite;

import com.javaoffers.brief.modelhelper.core.parse.SelectConditionParse;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;

import java.util.LinkedList;

public class SqliteSelectConditionParse extends SelectConditionParse {

    @Override
    public void beforeSelectCol(LinkedList<Condition> conditions, StringBuilder selectCols) {
        //检测是否有分页
        Condition condition = conditions.peekLast();
        if(condition instanceof LimitWordCondition){
            //弹出mysql的limit语法
            LimitWordCondition limit = (LimitWordCondition)conditions.pollLast();

            //生成 sqlite 分页语法
            SqliteLimitWordCondition sqliteSqlLimitWordCondition = new SqliteLimitWordCondition(limit.pageNum, limit.pageSize);
            sqliteSqlLimitWordCondition.setHeadCondition(((LimitWordCondition<?>) condition).getHeadCondition());
            conditions.addLast(sqliteSqlLimitWordCondition);

        }
    }
}
