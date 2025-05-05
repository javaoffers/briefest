package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.lang.reflect.Field;

/**
 * 处理自定义衍生功能
 */
public interface DeriveInfoLoader {
    /**
     * 加载衍生配置.
     * @param tableInfo 表信息
     * @param colF 表字段
     * @param colName 表名称
     */
    void loadDeriveInfo(TableInfo tableInfo, Field colF, String colName);
}
