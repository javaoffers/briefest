package com.javaoffers.brief.modelhelper.clickhouse;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.core.parse.DeleteConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.InsertConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.ParseCondition;
import com.javaoffers.brief.modelhelper.core.parse.SelectConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.UpdateConditionParse;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import com.javaoffers.brief.modelhelper.utils.DBType;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

public class ClickHouseStatementParser implements StatementParser<Condition, BaseSQLStatement> , BriefContextPostProcess {

    static final EnumMap<ConditionTag, ParseCondition> crudConditionParse = new EnumMap<ConditionTag, ParseCondition>(ConditionTag.class);

    static {
        crudConditionParse.put(SelectConditionParse.conditionTag, new CHSelectConditionParse());
        crudConditionParse.put(DeleteConditionParse.conditionTag, new DeleteConditionParse());
        crudConditionParse.put(InsertConditionParse.conditionTag, new InsertConditionParse());
        crudConditionParse.put(UpdateConditionParse.conditionTag, new UpdateConditionParse());
    }

    @Override
    public DBType getDBType() {
        return DBType.CLICKHOUSE;
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
        statementParserMap.put(this.getDBType(), this);

    }
}
