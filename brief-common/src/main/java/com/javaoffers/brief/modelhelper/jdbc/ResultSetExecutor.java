package com.javaoffers.brief.modelhelper.jdbc;

import java.util.List;

/**
 * @description: BriefResultSet
 * @author: create by cmj on 2023/7/30 13:25
 */
public interface ResultSetExecutor {

    List<String> getColNames();

    Object getColValueByColName(String colName);

    public boolean nextRow();
}
