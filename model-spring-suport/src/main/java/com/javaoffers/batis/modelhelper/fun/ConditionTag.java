package com.javaoffers.batis.modelhelper.fun;

/**
 * @Description: 条件枚举, sql 关键字
 * @Auther: create by cmj on 2022/5/2 02:29
 */
public enum ConditionTag {

    OR(100,"or",CategoryTag.WHERE_ON),

    EQ(101,"=",CategoryTag.WHERE_ON),

    UEQ(102,"<>",CategoryTag.WHERE_ON),

    GT(103,">",CategoryTag.WHERE_ON),

    LT(104,"<",CategoryTag.WHERE_ON),

    GT_EQ(105,">=",CategoryTag.WHERE_ON),

    LT_EQ(106,"<=",CategoryTag.WHERE_ON),

    BETWEEN(107,"between",CategoryTag.WHERE_ON),

    LIKE(108,"like",CategoryTag.WHERE_ON),

    IN(109,"in",CategoryTag.WHERE_ON),

    EXISTS(110,"exists", CategoryTag.WHERE_ON),

    /**select **/
    SELECT(200,"select",CategoryTag.SELECT_COL),

    ;

    private int code;
    private String tag;
    private CategoryTag categoryTag;
    ConditionTag(int code, String tag,CategoryTag categoryTag){
        this.code = code;
        this.tag = tag;
        this.categoryTag = categoryTag;
    }

    public int getCode() {
        return code;
    }

    public String getTag() {
        return tag;
    }

    public CategoryTag getCategoryTag() {
        return categoryTag;
    }
}
