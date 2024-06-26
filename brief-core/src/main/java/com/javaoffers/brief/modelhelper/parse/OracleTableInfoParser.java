package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.context.SmartTableInfoParser;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.sql.Connection;

public class OracleTableInfoParser extends SmartTableInfoParser {

    @Override
    public void parseTableInfo(Connection connection, TableInfo tableInfo) {
        super.parseTableInfo(tableInfo);
    }

    @Override
    public DBType getDBType() {
        return DBType.ORACLE;
    }
}
