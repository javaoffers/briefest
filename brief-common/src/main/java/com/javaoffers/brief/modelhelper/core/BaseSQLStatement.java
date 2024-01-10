package com.javaoffers.brief.modelhelper.core;

import java.util.List;
import java.util.Map;

/**
 * sql信息
 * @author mingJie
 */
public interface BaseSQLStatement {
    /**
     * 是否有效
     * @return
     */
    public boolean isStatus();

    /**
     * 获取sql
     * @return
     */
    public String getSql();

    /**
     * 获取sql中的参数
     * @return
     */
    public List<Map<String, Object>> getParams();
}
