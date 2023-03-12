package com.javaoffers.batis.modelhelper.encrypt.parser;

import com.javaoffers.thrid.jsqlparser.schema.Column;

/**
 * @author cmj
 * @createTime 2023年03月05日 11:42:00
 */
public class ColNameProcessorInfo {

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
}
