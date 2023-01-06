package com.javaoffers.batis.modelhelper.utils;

/**
 * @author mingJie
 */
public class FieldNameAnnoInfo {
    private String fieldName;
    private boolean excludeColAll;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isExcludeColAll() {
        return excludeColAll;
    }

    public void setExcludeColAll(boolean excludeColAll) {
        this.excludeColAll = excludeColAll;
    }
}
