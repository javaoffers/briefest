package com.javaoffers.brief.modelhelper.parser;

import com.javaoffers.brief.modelhelper.utils.DBTypeLabel;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.sql.Connection;

/**
 * @author mingJie
 */
public interface DataSourceParser  extends DBTypeLabel {

    /**
     * 解析TableInfo
     * @param connection
     * @param tableInfo
     */
    void parseTableInfo(Connection connection, TableInfo tableInfo);

}
