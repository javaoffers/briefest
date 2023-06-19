package com.javaoffers.brief.modelhelper.parser;

import java.util.HashSet;
import java.util.Set;

/**
 * create by cmj
 */
public class TableColumns {
    private String tableName;
    private Set<String> columns = new HashSet<>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<String> getColumns() {
        return columns;
    }

    public void setColumns(Set<String> columns) {
        this.columns = columns;
    }
}