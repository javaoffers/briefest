package com.javaoffers.batis.modelhelper.encrypt;

import net.sf.jsqlparser.schema.Column;

/**
 * @author cmj
 * @createTime 2023年03月05日 11:42:00
 */

public class ColNameProcessorInfo {

    private String tableName;

    private Column column;

    private ConditionName conditionName;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public ConditionName getConditionName() {
        return conditionName;
    }

    public void setConditionName(ConditionName conditionName) {
        this.conditionName = conditionName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
