package com.javaoffers.batis.modelhelper.fun;

/**
 * @Description: 条件枚举, sql 关键字
 * @Auther: create by cmj on 2022/5/2 02:29
 */
public enum ConditionTag {

    AND(99," and ",CategoryTag.WHERE_ON),

    OR(100," or ",CategoryTag.WHERE_ON),

    EQ(101," = ",CategoryTag.WHERE_ON),

    UEQ(102, " <> ",CategoryTag.WHERE_ON),

    GT(103," > ",CategoryTag.WHERE_ON),

    LT(104," < ",CategoryTag.WHERE_ON),

    GT_EQ(105," >= ",CategoryTag.WHERE_ON),

    LT_EQ(106," <= ",CategoryTag.WHERE_ON),

    BETWEEN(107," between ",CategoryTag.WHERE_ON),

    NOT_BETWEEN(1071," not between ",CategoryTag.WHERE_ON),

    LIKE(108," like ",CategoryTag.WHERE_ON),

    LIKE_LEFT(1081," like ",CategoryTag.WHERE_ON),

    LIKE_RIGHT(1082," like ",CategoryTag.WHERE_ON),

    IN(109," in ",CategoryTag.WHERE_ON),

    NOT_IN(1091," not in ",CategoryTag.WHERE_ON),

    EXISTS(110," exists ", CategoryTag.WHERE_ON),

    WHERE(111," where ",CategoryTag.WHERE_ON),

    /**delete select **/
    SELECT(200," select ",CategoryTag.SELECT_COL),

    SELECT_FROM(201," from ",CategoryTag.SELECT_FROM),

    DELETE_FROM(201,"delete from ",CategoryTag.DELETE_FROM),

    LEFT_JOIN(300," left join ",CategoryTag.LEFT_JOIN),

    ON(301," on ",CategoryTag.LEFT_JOIN),

    /**group by**/

    GROUP_BY(401, " group by ", CategoryTag.WHERE_ON),

    HAVING(402," having ", CategoryTag.WHERE_ON),

    /***limit**/
    LIMIT(501," limit ", CategoryTag.WHERE_ON),
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
