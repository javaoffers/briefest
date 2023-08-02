package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.utils.SQLType;

import java.util.List;
import java.util.Map;

public interface BaseSQLInfo {
    public String getSql();
    public List<Map<String, Object>> getParams();
    public void resetSql(String sql);
    public int getBatchSize();
    public List<Object[]> getArgsParam();
    public SQLType getSqlType();
}
