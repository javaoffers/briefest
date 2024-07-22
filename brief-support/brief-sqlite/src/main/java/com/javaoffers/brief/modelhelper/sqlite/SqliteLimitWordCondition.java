package com.javaoffers.brief.modelhelper.sqlite;

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
public class SqliteLimitWordCondition extends LimitWordCondition {

    public SqliteLimitWordCondition(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    @Override
    public String getSql() {
        String startIndexTag = getNextTag();
        String lenTag = getNextTag();
        this.getParams().put(startIndexTag, super.startIndex);
        this.getParams().put(lenTag, super.len);
        return getTag().getTag() +" #{"+lenTag+"} offset #{"+startIndexTag+"}";
    }

}
