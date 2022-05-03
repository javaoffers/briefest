package com.javaoffers.batis.modelhelper.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 表的描述信息
 * @Auther: create by cmj on 2022/5/4 00:00
 */
public class TableInfo {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * K: 属性名称
     * V: 对应的表字段
     * 懒加载存放
     */
    private Map<String,ColumnInfo> colName = new HashMap<>();

    /**
     * 存放字段信息
     */
    private Set<ColumnInfo> columnInfos = new HashSet<>();

    public String getTableName() {
        return tableName;
    }

    public Map<String, ColumnInfo> getColName() {
        return colName;
    }

    public Set<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public TableInfo(String tableName) {
        this.tableName = tableName;
    }
}
