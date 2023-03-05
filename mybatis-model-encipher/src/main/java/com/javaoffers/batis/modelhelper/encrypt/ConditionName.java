package com.javaoffers.batis.modelhelper.encrypt;

/**
 * @author cmj
 * @Description TODO
 * @createTime 2023年03月04日 14:22:00
 */
public enum ConditionName {

    SELECT_COL_NAME("selectColName",1),
    WHERE("where",2),
    ON("On", 3)
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
