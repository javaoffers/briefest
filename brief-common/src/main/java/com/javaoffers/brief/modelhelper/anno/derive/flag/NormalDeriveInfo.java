package com.javaoffers.brief.modelhelper.anno.derive.flag;

import java.lang.reflect.Field;

/**
 * 关于删除字段的处理.
 *
 * @author cao ming jie create by 2025/4/30
 */
public class NormalDeriveInfo extends DeriveInfo{
    private boolean isDelField;
    private boolean isRowStatusField;

    public NormalDeriveInfo(String colName, Field field) {
        super(colName, field);
        Class<?> fieldType = field.getType();
        if(fieldType.equals(IsDel.class)){
            isDelField = true;
        }else if(fieldType.equals(RowStatus.class)){
            isRowStatusField = true;
        }
    }

    public boolean isDelField() {
        return isDelField;
    }

    public boolean isRowStatusField() {
        return isRowStatusField;
    }
}
