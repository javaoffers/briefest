package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.parser.TableInfoParser;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.ReflectionUtils;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SmartTableInfoParser implements TableInfoParser {

    //TableInfoParser
    private static final Map<DBType, TableInfoParser> tableInfoParserMap = new HashMap<>();
    //加载TableInfoParser
    static Set<TableInfoParser> childInstance = ReflectionUtils.getChildInstance(TableInfoParser.class);

    static {
        childInstance.stream().filter(tableInfoParser -> {
            return !(tableInfoParser instanceof SmartTableInfoParser);
        }).forEach(tableInfoParser -> {
            tableInfoParserMap.put(tableInfoParser.getDBType(), tableInfoParser );
        });
    }

    @Override
    public void parseTableInfo(Connection connection, TableInfo tableInfo) {
        chose(connection).parseTableInfo(connection, tableInfo);
    }

    //通过 connection的url选取对应的db解析器
    public TableInfoParser chose(Connection connection){
        try {
            String url = connection.getMetaData().getURL();
            List<DBType> dbTypeList = tableInfoParserMap.keySet().stream().filter(dbType -> {
                return url.contains(dbType.name().toLowerCase());
            }).collect(Collectors.toList());

            if(dbTypeList.size() != 1){
                throw new BriefException("chose db type error");
            }
            return tableInfoParserMap.get(dbTypeList.get(0));
        }catch (Exception e){
            throw new BriefException("chose db type error");
        }
    }

    @Override
    public DBType getDBType() {
        throw new UnsupportedOperationException();
    }
}
