package com.javaoffers.batis.modelhelper.utils;

/**
 * @Description: 字段信息
 * @Auther: create by cmj on 2022/5/4 01:48
 */
public class ColumnInfo {

    private String colNamme;

    private String colType;

    private boolean isAutoincrement;

    public ColumnInfo(String colNamme, String colType, boolean isAutoincrement, Object defaultValue) {
        this.colNamme = colNamme;
        this.colType = colType;
        this.isAutoincrement = isAutoincrement;
        this.defaultValue = defaultValue;
    }

    private Object defaultValue;


    public String getColNamme() {
        return colNamme;
    }

    public String getColType() {
        return colType;
    }

    public boolean isAutoincrement() {
        return isAutoincrement;
    }
    public Object getDefaultValue(){
        return this.defaultValue;
    }
}
