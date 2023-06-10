package com.javaoffers.batis.modelhelper.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: create by cmj on 2023/6/1 17:27
 */
public class SqlMetaInfo {

    private String sql;
    private List<Map<String,Object>> params = new ArrayList<>();
    private Class modelClass;

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

    public SqlMetaInfo(String sql, List<Map<String, Object>> params, Class modelClass) {
        this.sql = sql;
        this.params = params;
        this.modelClass = modelClass;
    }

    public SqlMetaInfo(String sql, Class modelClass) {
        this.sql = sql;
        this.modelClass = modelClass;
    }
}
