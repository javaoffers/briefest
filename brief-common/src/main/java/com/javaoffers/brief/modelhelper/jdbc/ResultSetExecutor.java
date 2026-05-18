package com.javaoffers.brief.modelhelper.jdbc;

import java.util.List;

/**
 * @description: BriefResultSet
 * @author: create by cmj on 2023/7/30 13:25
 */
public interface ResultSetExecutor {

    List<String> getColNames();

    Object getColValueByColName(String colName);

    Object getColValueByColPosition(int position);

    /**
     *Get an alias, usually the property name of the Model class
     * @param position  p
     * @return name
     */
    String getAliasColName(int position);

    int getCols();

    public boolean nextRow();
}
