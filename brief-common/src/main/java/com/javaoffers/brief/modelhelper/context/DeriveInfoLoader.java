package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.lang.reflect.Field;

public interface DeriveInfoLoader {
    void loadDeriveInfo(TableInfo tableInfo, Field colF, String colName);
}
