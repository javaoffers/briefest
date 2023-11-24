package com.javaoffers.brief.modelhelper.core;

import java.util.List;
import java.util.Map;

/**
 * @author mingJie
 */
public interface BaseSQLStatement {
    public boolean isStatus();
    public String getSql();
    public List<Map<String, Object>> getParams();
}
