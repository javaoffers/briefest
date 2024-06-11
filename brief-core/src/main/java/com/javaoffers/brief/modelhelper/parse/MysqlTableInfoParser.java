package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.anno.ColName;
import com.javaoffers.brief.modelhelper.anno.NoneCol;
import com.javaoffers.brief.modelhelper.context.SmartTableInfoParser;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.parser.TableInfoParser;
import com.javaoffers.brief.modelhelper.utils.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MysqlTableInfoParser extends SmartTableInfoParser {

    @Override
    public void parseTableInfo(Connection connection, TableInfo tableInfo) {
        super.parseTableInfo(tableInfo);
    }

    @Override
    public DBType getDBType() {
        return DBType.MYSQL;
    }
}
