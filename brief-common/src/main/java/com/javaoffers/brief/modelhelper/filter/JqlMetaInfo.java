package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: create by cmj on 2023/6/1 17:27
 */
public class JqlMetaInfo {

    private String sql;

    private List<Map<String,Object>> params = new ArrayList<>();

    private Class modelClass;

    private TableInfo tableInfo;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }

    public Class getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class modelClass) {
        this.modelClass = modelClass;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public JqlMetaInfo(String sql, List<Map<String, Object>> params, Class modelClass) {
        this.sql = sql;
        this.params = params;
        this.modelClass = modelClass;
        this.tableInfo = TableHelper.getTableInfo(modelClass);
    }

    public JqlMetaInfo(String sql, Class modelClass) {
        this.sql = sql;
        this.modelClass = modelClass;
    }

}
