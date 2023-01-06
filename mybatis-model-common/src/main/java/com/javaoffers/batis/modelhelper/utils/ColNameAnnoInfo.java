package com.javaoffers.batis.modelhelper.utils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author mingJie
 */
public class ColNameAnnoInfo {

    private String colName;

    private boolean isSqlFun;

    private boolean excludeColAll;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public boolean isSqlFun() {
        return isSqlFun;
    }

    public void setSqlFun(boolean sqlFun) {
        isSqlFun = sqlFun;
    }

    public boolean isExcludeColAll() {
        return excludeColAll;
    }

    public void setExcludeColAll(boolean excludeColAll) {
        this.excludeColAll = excludeColAll;
    }
}
