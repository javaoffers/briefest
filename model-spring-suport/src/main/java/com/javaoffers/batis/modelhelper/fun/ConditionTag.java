package com.javaoffers.batis.modelhelper.fun;

/**
 * @Description: 条件枚举, sql 关键字
 * @Auther: create by cmj on 2022/5/2 02:29
 */
public enum ConditionTag {

    OR(100,"or"),

    EQ(101,"="),

    UEQ(102,"<>"),

    GT(103,">"),

    LT(104,"<"),

    GT_EQ(105,">="),

    LT_EQ(106,"<="),

    BETWEEN(107,"between"),

    LIKE(108,"like"),

    IN(109,"in"),

    ;

    private int code;
    private String tag;
    ConditionTag(int code, String tag){
        this.code = code;
        this.tag = tag;
    }
}
