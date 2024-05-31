package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.context.SmartTableInfoParser;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.parser.TableInfoParser;
import com.javaoffers.brief.modelhelper.utils.ColumnInfo;
import com.javaoffers.brief.modelhelper.utils.ColumnLabel;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class H2TableInfoParser extends SmartTableInfoParser {

    @Override
    public void parseTableInfo(Connection connection, TableInfo tableInfo) {
        super.parseTableInfo(tableInfo);
    }

    @Override
    public DBType getDBType() {
        return DBType.H2;
    }
}
