package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.lang.reflect.Field;

public interface DeriveProcess {
    void processDerive(TableInfo tableInfo, Field colF, String colName);
}
