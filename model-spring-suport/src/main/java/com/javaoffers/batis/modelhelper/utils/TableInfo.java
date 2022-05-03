package com.javaoffers.batis.modelhelper.utils;

import java.util.HashMap;
import java.util.Map;

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
     */
    private Map<String,ColumnInfo> colName = new HashMap<>();

}
