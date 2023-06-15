package com.javaoffers.batis.modelhelper.anno.derive.flag;

import java.lang.reflect.Field;

/**
 * @description: info
 * @author: create by cmj on 2023/6/11 19:48
 */
public class DeriveInfo {

    private String colName;
    private Field field;
    private boolean isDelField;
    private boolean isRowStatusField;

    public DeriveInfo(String colName, Field field) {
        this.colName = colName;
        this.field = field;
        Class<?> fieldType = field.getType();
        if(fieldType.equals(IsDel.class)){
            isDelField = true;
        }else if(fieldType.equals(RowStatus.class)){
            isRowStatusField = true;
        }
    }

    public String getColName() {
        return colName;
    }

    public Field getField() {
        return field;
    }

    public boolean isDelField() {
        return isDelField;
    }

    public boolean isRowStatusField() {
        return isRowStatusField;
    }
}
