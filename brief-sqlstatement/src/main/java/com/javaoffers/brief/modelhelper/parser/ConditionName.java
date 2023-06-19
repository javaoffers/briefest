package com.javaoffers.brief.modelhelper.parser;

/**
 * @author cmj
 * @createTime 2023年03月04日 14:22:00
 */
public enum ConditionName {

    SELECT_COL_NAME("select_col_name",1),
    WHERE("where",2),
    ON("on", 3),
    VALUES("values", 4),
    UPDATE_SET("update_set", 5),
    ;
    private String name;
    private int code;

    ConditionName(String name, int code){
        this.name = name;
        this.code = code;
    }

    public static boolean isWhereOnName(ConditionName conditionName){
        return conditionName == WHERE || conditionName == ON;
    }

}
