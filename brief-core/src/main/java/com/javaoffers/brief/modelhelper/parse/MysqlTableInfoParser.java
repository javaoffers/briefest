package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.parser.TableInfoParser;
import com.javaoffers.brief.modelhelper.utils.ColumnInfo;
import com.javaoffers.brief.modelhelper.utils.ColumnLabel;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.LinkedList;

public class MysqlTableInfoParser implements TableInfoParser {

    @Override
    public void parseTableInfo(Connection connection, TableInfo tableInfo) {
        try {
            tableInfo.setDbType(DBType.MYSQL);
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tableResultSet = metaData.getTables(connection.getCatalog(), connection.getSchema(), tableInfo.getTableName(), null);
            ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableInfo.getTableName());
            LinkedList<String> primaryKeyList = new LinkedList<>();
            while (primaryKeys.next()) {
                String primaryColName = primaryKeys.getString(ColumnLabel.COLUMN_NAME);
                primaryKeyList.add(primaryColName);
            }
            while (tableResultSet.next()) {
                // Get table field structure
                ResultSet columnResultSet = metaData.getColumns(connection.getCatalog(), "", tableInfo.getTableName(), "%");
                while (columnResultSet.next()) {
                    // Col Name
                    String columnName = columnResultSet.getString(ColumnLabel.COLUMN_NAME).toLowerCase();
                    // type of data
                    String columnType = columnResultSet.getString(ColumnLabel.TYPE_NAME);
                    //the default value of the field
                    Object defaultValue = columnResultSet.getString(ColumnLabel.COLUMN_DEF);
                    //Whether to auto increment
                    boolean isAutoincrement = "YES".equalsIgnoreCase(columnResultSet.getString(ColumnLabel.IS_AUTOINCREMENT));
                    ColumnInfo columnInfo = new ColumnInfo(columnName, columnType, isAutoincrement, defaultValue);
                    tableInfo.getColumnInfos().add(columnInfo);
                    tableInfo.putColNames(columnName, columnInfo);
                    if (primaryKeyList.contains(columnName)) {
                        tableInfo.putPrimaryColNames(columnName, columnInfo);
                    }
                }
            }
        }catch (Exception e){
            throw new BriefException("parse mysql db Table Info error");
        }

    }

    @Override
    public DBType getDBType() {
        return DBType.MYSQL;
    }
}
