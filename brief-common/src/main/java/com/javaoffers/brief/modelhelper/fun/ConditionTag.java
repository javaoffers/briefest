package com.javaoffers.brief.modelhelper.fun;

/**
 * @Description: 条件枚举, sql 关键字
 * @Auther: create by cmj on 2022/5/2 02:29
 */
public enum ConditionTag {

    EQ(1000," = ",CategoryTag.WHERE_ON),

    UEQ(1000, " <> ",CategoryTag.WHERE_ON),

    GT(1000," > ",CategoryTag.WHERE_ON),

    LT(1000," < ",CategoryTag.WHERE_ON),

    GT_EQ(1000," >= ",CategoryTag.WHERE_ON),

    LT_EQ(1000," <= ",CategoryTag.WHERE_ON),

    BETWEEN(1000," between ",CategoryTag.WHERE_ON),

    NOT_BETWEEN(1000," not between ",CategoryTag.WHERE_ON),

    LIKE(1000," like ",CategoryTag.WHERE_ON),

    LIKE_LEFT(1000," like ",CategoryTag.WHERE_ON),

    LIKE_RIGHT(1000," like ",CategoryTag.WHERE_ON),

    IN(1000," in ",CategoryTag.WHERE_ON),

    NOT_IN(1000," not in ",CategoryTag.WHERE_ON),

    EXISTS(1000," exists ", CategoryTag.WHERE_ON),

    IS_NULL(1000, " is null ",CategoryTag.WHERE_ON),

    IS_NOT_NULL(1000," is not null ", CategoryTag.WHERE_ON),

    //虚拟的where条件，通常是where和关键字的一种转换
    VIRTUAL(1200,"", CategoryTag.WHERE_ON),

    /**delete select **/
    SELECT(2000," select ",CategoryTag.SELECT_COL),

    SELECT_FROM(2000," from ",CategoryTag.SELECT_FROM),

    DELETE_FROM(2000,"delete from ",CategoryTag.DELETE_FROM),

    LEFT_JOIN(2000," left join ",CategoryTag.JOIN_TABLE),

    INNER_JOIN(2000," inner join ",CategoryTag.JOIN_TABLE),

    RIGHT_JOIN(2000," right join ",CategoryTag.JOIN_TABLE),

    ON(2200," on ",CategoryTag.JOIN_TABLE),

    /**insert**/
    INSERT_INTO(3000," insert into ", CategoryTag.INSERT_INTO),

    VALUES(3000," values ", CategoryTag.INSERT_INTO),

    INSERT_COL_VALUE(3000,"", CategoryTag.INSERT_INTO),

    ON_DUPLICATE_KEY_UPDATE(3000," on duplicate key update ", CategoryTag.INSERT_INTO),

    @Deprecated
    REPLACE_INTO(3000," replace into ", CategoryTag.INSERT_INTO),

    USING(3000, " using ", CategoryTag.INSERT_INTO),

    WHEN_MATCHED_THEN(3000, " when matched then ", CategoryTag.INSERT_INTO),

    WHEN_NOT_MATCHED_THEN(3000, " when not matched then ", CategoryTag.INSERT_INTO),

    INSERT(3000, " insert ", CategoryTag.INSERT_INTO),

    MERGE_INTO(3000, " merge into ", CategoryTag.INSERT_INTO),

    ON_CONFLICT(3000, " on conflict ", CategoryTag.INSERT_INTO),

    /**update**/
    UPDATE(3000, " update ", CategoryTag.UPDATE_SET),

    SET(3000, " set ", CategoryTag.UPDATE_SET),

    /**group by**/

    GROUP_BY(3000, " group by ", CategoryTag.KEY_WORD),

    HAVING(3000," having ", CategoryTag.KEY_WORD),

    /***limit**/
    LIMIT(4000," limit ", CategoryTag.KEY_WORD),
    ORDER(4001," order by ", CategoryTag.KEY_WORD),
    AND(1100," and ",CategoryTag.KEY_WORD),
    OR(1100," or ",CategoryTag.KEY_WORD),
    WHERE(1100," where ",CategoryTag.KEY_WORD),
    /**特殊符号**/
    LK(5000," ( ", CategoryTag.KEY_WORD),
    RK(5000," ) ", CategoryTag.KEY_WORD),
    BLANK(5000,"", CategoryTag.KEY_WORD),
    COMMA(5000,", ", CategoryTag.KEY_WORD),
    QUOTE(5000, "`", CategoryTag.KEY_WORD),
    QUOTATION(5000, "'", CategoryTag.KEY_WORD),
    PERIOD(5000, ".", CategoryTag.KEY_WORD),

    /**key word**/
    DISTINCT(6000," distinct ",CategoryTag.SELECT_COL),
    AS(6000," as ",CategoryTag.SELECT_COL),
    DO(6000," do ",CategoryTag.SELECT_COL),

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
