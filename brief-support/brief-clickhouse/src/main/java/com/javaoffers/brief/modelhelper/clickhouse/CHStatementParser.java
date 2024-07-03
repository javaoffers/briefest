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

public class CHStatementParser extends ClickHouseStatementParser {

    @Override
    public DBType getDBType() {
        return DBType.CLICK_HOUSE;
    }

}
