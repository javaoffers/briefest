package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.core.parse.DeleteConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.InsertConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.ParseCondition;
import com.javaoffers.brief.modelhelper.core.parse.SelectConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.UpdateConditionParse;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.EnumMap;
import java.util.LinkedList;

/**
 * @Description: 用于解析Condition
 * @Auther: create by cmj on 2022/5/22 13:47
 */
public class StatementParserAdepter implements BriefContextPostProcess {

    private static BriefContext briefContext;

    public static BaseSQLStatement statementParse(LinkedList<Condition> conditions) {
        HeadCondition headCondition = (HeadCondition)conditions.pollFirst();
        DBType dbType = TableHelper.getTableInfo(headCondition.getModelClass()).getDbType();
        return briefContext.getStatementParser(dbType).parse(conditions);

    }


    @Override
    public void postProcess(BriefContext briefContext) {
        StatementParserAdepter.briefContext = briefContext;
    }
}
