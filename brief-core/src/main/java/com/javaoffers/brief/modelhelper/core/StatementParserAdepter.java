package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextAware;
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
public class StatementParserAdepter implements BriefContextAware {

    private static volatile BriefContext briefContext;

    public static MoreSQLInfo statementParse(LinkedList<Condition> conditions) {
        HeadCondition headCondition = (HeadCondition)conditions.pollFirst();
        Condition conditionTag = conditions.peekFirst();
        DBType dbType = TableHelper.getTableInfo(headCondition.getModelClass()).getDbType();
        MoreSQLInfo sqlStatement = (MoreSQLInfo) briefContext.getStatementParser(dbType).parse(conditions);
        //for reuse select(), delete()...
        conditions.add(headCondition.clone());
        conditions.add(conditionTag);
        sqlStatement.setHeadCondition(headCondition);
        return sqlStatement;
    }

    @Override
    public void setBriefContext(BriefContext briefContext) {
        StatementParserAdepter.briefContext = briefContext;
    }
}
