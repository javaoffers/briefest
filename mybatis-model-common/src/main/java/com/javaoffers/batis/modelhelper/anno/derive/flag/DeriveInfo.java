package com.javaoffers.batis.modelhelper.anno.derive.flag;

import java.lang.reflect.Field;

/**
 * @description: info
 * @author: create by cmj on 2023/6/11 19:48
 */
public class DeriveInfo {

    private String colName;
    private Field field;

    public DeriveInfo(String colName, Field field) {
        this.colName = colName;
        this.field = field;
    }
}
