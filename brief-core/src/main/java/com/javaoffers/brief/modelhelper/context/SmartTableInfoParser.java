package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.anno.NoneCol;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.parser.TableInfoParser;
import com.javaoffers.brief.modelhelper.utils.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public class SmartTableInfoParser implements TableInfoParser {

    //TableInfoParser
    private static final Map<DBType, TableInfoParser> tableInfoParserMap = new HashMap<>();
    //加载TableInfoParser
    static Set<TableInfoParser> childInstance = ReflectionUtils.getChildInstance(TableInfoParser.class);

    static {
        childInstance.stream().filter(tableInfoParser -> {
            return !(tableInfoParser.getClass() == SmartTableInfoParser.class);
        }).forEach(tableInfoParser -> {
            tableInfoParserMap.put(tableInfoParser.getDBType(), tableInfoParser );
        });
    }

    @Override
    public void parseTableInfo(Connection connection, TableInfo tableInfo) {
        chose(connection).parseTableInfo(connection, tableInfo);
    }

    public void parseTableInfo(TableInfo tableInfo){
        DBType dbType = getDBType();
        tableInfo.setDbType(dbType);

        List<Field> filedList = Utils.getFields(tableInfo.getModelClass()).stream().filter(field -> {
            return field.getDeclaredAnnotation(NoneCol.class) == null
                     && !Utils.isBaseModel(field)
                     && !field.getType().isAssignableFrom(Collection.class)
                     && !field.getType().isAssignableFrom(Map.class);
        }).collect(Collectors.toList());

        //处理主键
        filedList.stream().filter(field -> {
            return field.getDeclaredAnnotation(BaseUnique.class) != null;
        }).forEach(field -> {
            BaseUnique baseUniqueAnno = field.getDeclaredAnnotation(BaseUnique.class);
            String columnName = Utils.conLine(field.getName());
            if (StringUtils.isNotBlank(baseUniqueAnno.value())) {
                columnName = baseUniqueAnno.value();
            }
            ColumnInfo columnInfo = new ColumnInfo(columnName);
            tableInfo.putPrimaryColNames(columnName, columnInfo);
            tableInfo.getColumnInfos().add(columnInfo);
            tableInfo.putColNames(columnName, columnInfo);
        });

        //处理非主键,非@NoneCol 统一按照ColName处理(包含sql fun).
        filedList.forEach(field -> {
            String columnName = Utils.conLine(field.getName());
            ColumnInfo columnInfo = new ColumnInfo(columnName);
            tableInfo.getColumnInfos().add(columnInfo);
            tableInfo.putColNames(columnName, columnInfo);
        });
    }

    //通过 connection的url选取对应的db解析器
    public TableInfoParser chose(Connection connection){
        try {
            String url = connection.getMetaData().getURL().toLowerCase();
            List<DBType> dbTypeList = tableInfoParserMap.keySet().stream().filter(dbType -> {
                return url.contains("jdbc:"+dbType.toString().toLowerCase());
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
