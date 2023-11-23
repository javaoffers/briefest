package com.javaoffers.brief.modelhelper.db;

import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.sql.Connection;

/**
 * @author mingJie
 */
public interface DataSourceParser {

    /**
     * 解析TableInfo
     * @param connection
     * @param tableInfo
     */
    void parseTableInfo(Connection connection, TableInfo tableInfo);

    /**
     * 获取指定的ColName. 不同的DB类型会有不同的处理方式
     * @param tableName
     * @param colName
     * @return
     */
    String getSpecialColName(String tableName, String colName);

    /**
     *
     * @return
     */
    String onDuplicate(Class modelClass, Object model);

}
