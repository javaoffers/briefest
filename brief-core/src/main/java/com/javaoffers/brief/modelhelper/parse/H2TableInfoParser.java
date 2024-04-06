package com.javaoffers.brief.modelhelper.parse;

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

public class H2TableInfoParser implements TableInfoParser {

    @Override
    public void parseTableInfo(Connection connection, TableInfo tableInfo) {
        try {
            tableInfo.setDbType(DBType.H2);
            Object md = connection.prepareStatement("  show columns from "+tableInfo.getTableName()+"; ").executeQuery().getMetaData();
            Field resultF = md.getClass().getDeclaredField("result");
            resultF.setAccessible(true);
            Object result  = resultF.get(md);
            Field rowsF = result.getClass().getDeclaredField("rows");
            rowsF.setAccessible(true);
            List<Object> rows = (List)rowsF.get(result);
            rows.forEach(row->{
                String columnName = String.valueOf(Array.get(row,0)).replaceAll("'","").toLowerCase();
                String columnType = String.valueOf(Array.get(row,1)).replaceAll("'","");
                String defaultValue = null;
                boolean isAutoincrement = String.valueOf(Array.get(row,4)).contains("SYSTEM_SEQUENCE");
                boolean isPrimary = String.valueOf(Array.get(row,3)).contains("PRI");
                ColumnInfo columnInfo = new ColumnInfo(columnName, columnType, isAutoincrement, defaultValue);
                tableInfo.getColumnInfos().add(columnInfo);
                tableInfo.putColNames(columnName, columnInfo);
                if(isPrimary){
                    tableInfo.putPrimaryColNames(columnName, columnInfo);
                }
            });
        }catch (Exception e){
            throw new BriefException("parse h2 db Table Info error");
        }

    }

    @Override
    public DBType getDBType() {
        return DBType.H2;
    }
}
