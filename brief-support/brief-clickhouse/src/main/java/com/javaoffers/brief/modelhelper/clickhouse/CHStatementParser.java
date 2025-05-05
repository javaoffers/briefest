package com.javaoffers.brief.modelhelper.clickhouse;

import com.javaoffers.brief.modelhelper.utils.DBType;

public class CHStatementParser extends ClickHouseStatementParser {

    @Override
    public DBType getDBType() {
        return DBType.CLICK_HOUSE;
    }

}
