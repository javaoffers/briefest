package com.javaoffers.brief.modelhelper.parser;

import com.javaoffers.brief.modelhelper.utils.DBTypeLabel;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.sql.Connection;

/**
 * 解析TableInfo. 不同的DB会有不同的解析.
 * @author mingJie
 */
public interface TableInfoParser extends DBTypeLabel {

    /**
     * 解析TableInfo
     * @param connection
     * @param tableInfo
     */
    void parseTableInfo(Connection connection, TableInfo tableInfo);

}
