package com.javaoffers.brief.modelhelper.sqlserver;

import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;

/**
 * oracle的分页如下：每页查询5条， 第一页 1-5， 第二页： 6-10
 * SELECT * FROM
 * (
 * SELECT A.*, ROWNUM RN
 * FROM (SELECT * FROM TABLE_NAME) A
 * WHERE ROWNUM <= 5
 * )
 * WHERE RN > 0
 *
 */
public class SqlServerLimitWordCondition extends LimitWordCondition {

    public SqlServerLimitWordCondition(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    /**
     * 追加后半部分：
     * A
     * WHERE ROWNUM <= 40
     * )
     * WHERE RN >= 21
     */
    @Override
    public String getSql() {
        String startIndexTag = getNextTag();
        String lenTag = getNextTag();
        this.getParams().put(startIndexTag, super.startIndex);
        this.getParams().put(lenTag, super.len);
        // A 是表的别名
        return " OFFSET  #{" + startIndexTag + "} ROWS FETCH NEXT #{" + lenTag + "} ROWS ONLY";
    }

}
