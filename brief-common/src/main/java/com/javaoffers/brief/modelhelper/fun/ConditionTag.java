package com.javaoffers.brief.modelhelper.fun;

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

    IS_NULL(112, " is null ",CategoryTag.WHERE_ON),

    IS_NOT_NULL(113," is not null ", CategoryTag.WHERE_ON),

    WHERE(199," where ",CategoryTag.WHERE_ON),

    /**delete select **/
    SELECT(200," select ",CategoryTag.SELECT_COL),

    SELECT_FROM(201," from ",CategoryTag.SELECT_FROM),

    DELETE_FROM(201,"delete from ",CategoryTag.DELETE_FROM),

    LEFT_JOIN(300," left join ",CategoryTag.JOIN_TABLE),

    INNER_JOIN(301," inner join ",CategoryTag.JOIN_TABLE),

    RIGHT_JOIN(302," right join ",CategoryTag.JOIN_TABLE),

    ON(303," on ",CategoryTag.JOIN_TABLE),

    /**insert**/
    INSERT_INTO(2002," insert into ", CategoryTag.INSERT_INTO),

    VALUES(2003," values ", CategoryTag.INSERT_INTO),

    INSERT_COL_VALUE(2004,"", CategoryTag.INSERT_INTO),

    ON_DUPLICATE_KEY_UPDATE(2005," on duplicate key update ", CategoryTag.INSERT_INTO),

    @Deprecated
    REPLACE_INTO(2006," replace into ", CategoryTag.INSERT_INTO),

    USING(2007, " using ", CategoryTag.INSERT_INTO),

    WHEN_MATCHED_THEN(2008, " when matched then ", CategoryTag.INSERT_INTO),

    WHEN_NOT_MATCHED_THEN(2009, " when not matched then ", CategoryTag.INSERT_INTO),

    INSERT(2010, " insert ", CategoryTag.INSERT_INTO),

    MERGE_INTO(2011, " merge into ", CategoryTag.INSERT_INTO),

    /**update**/
    UPDATE(2100, " update ", CategoryTag.UPDATE_SET),

    SET(2101, " set ", CategoryTag.UPDATE_SET),

    /**group by**/

    GROUP_BY(401, " group by ", CategoryTag.WHERE_ON),

    HAVING(402," having ", CategoryTag.WHERE_ON),

    /***limit**/
    LIMIT(501," limit ", CategoryTag.WHERE_ON),
    ORDER(502," order by ", CategoryTag.WHERE_ON),

    /**特殊符号**/
    LK(601," ( ", CategoryTag.WHERE_ON),
    RK(602," ) ", CategoryTag.WHERE_ON),
    BLANK(603,"", CategoryTag.WHERE_ON),
    COMMA(604,", ", CategoryTag.WHERE_ON),
    QUOTE(605, "`", CategoryTag.WHERE_ON),
    QUOTATION(606, "'", CategoryTag.WHERE_ON),
    PERIOD(607, ".", CategoryTag.WHERE_ON),

    /**key word**/
    DISTINCT(700," distinct ",CategoryTag.SELECT_COL),
    AS(701," as ",CategoryTag.SELECT_COL),

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
