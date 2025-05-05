package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcessor;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.LinkedList;

/**
 * @Description: 用于解析Condition
 * @Auther: create by cmj on 2022/5/22 13:47
 */
public class StatementParserAdepter implements BriefContextPostProcessor {

    private static volatile BriefContext briefContext;

    public static BaseSQLStatement statementParse(LinkedList<Condition> conditions) {
        HeadCondition headCondition = (HeadCondition)conditions.pollFirst();
        Condition conditionTag = conditions.peekFirst();
        DBType dbType = TableHelper.getTableInfo(headCondition.getModelClass()).getDbType();
        BaseSQLStatement sqlStatement = briefContext.getStatementParser(dbType).parse(conditions);
        //for reuse select(), delete()...
        conditions.add(headCondition.clone());
        conditions.add(conditionTag);
        return sqlStatement;
    }


    @Override
    public void postProcess(BriefContext briefContext) {
        StatementParserAdepter.briefContext = briefContext;
    }
}
