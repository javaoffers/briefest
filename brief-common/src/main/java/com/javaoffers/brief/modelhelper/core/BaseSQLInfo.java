package com.javaoffers.brief.modelhelper.core;

import java.util.List;
import java.util.Map;

public interface BaseSQLInfo {
    public String getSql();
    public List<Map<String, Object>> getParams();
    public void resetSql(String sql);
}
