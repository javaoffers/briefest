package com.javaoffers.brief.modelhelper.oracle;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.core.parse.*;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import com.javaoffers.brief.modelhelper.utils.DBType;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

public class OracleStatementParser implements StatementParser<Condition, BaseSQLStatement> , BriefContextPostProcess {

    static final EnumMap<ConditionTag, ParseCondition> crudConditionParse = new EnumMap<ConditionTag, ParseCondition>(ConditionTag.class);

    static {
        crudConditionParse.put(SelectConditionParse.conditionTag, new OracleSelectConditionParse());
        crudConditionParse.put(DeleteConditionParse.conditionTag, new DeleteConditionParse());
        crudConditionParse.put(InsertConditionParse.conditionTag, new OracleInsertConditionParse());
        crudConditionParse.put(UpdateConditionParse.conditionTag, new UpdateConditionParse());
    }

    @Override
    public DBType getDBType() {
        return DBType.ORACLE;
    }

    @Override
    public BaseSQLStatement parse(LinkedList<Condition> conditions) {
        //判断类型
        ConditionTag conditionTag = conditions.get(0).getConditionTag();
        return crudConditionParse.get(conditionTag).parse(conditions);

    }

    @Override
    public void postProcess(BriefContext briefContext) {
        SmartBriefContext smartBriefContext = (SmartBriefContext) briefContext;
        Map<DBType, StatementParser> statementParserMap = smartBriefContext.getStatementParserMap();
        statementParserMap.put(getDBType(), this);

    }
}
