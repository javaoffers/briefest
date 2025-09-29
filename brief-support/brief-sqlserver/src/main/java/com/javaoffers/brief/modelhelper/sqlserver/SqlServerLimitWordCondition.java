package com.javaoffers.brief.modelhelper.sqlserver;

import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;

/**
 *
 *
 */
public class SqlServerLimitWordCondition extends LimitWordCondition {

    private String sql;

    public SqlServerLimitWordCondition(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    @Override
    public String getSql() {
        if(sql == null){
            String startIndexTag = getNextTag();
            String lenTag = getNextTag();
            this.getParams().put(startIndexTag, super.startIndex);
            this.getParams().put(lenTag, super.pageSize);
            // A 是表的别名
            this.sql = " OFFSET  #{" + startIndexTag + "} ROWS FETCH NEXT #{" + lenTag + "} ROWS ONLY";
        }
        return sql;
    }

    @Override
    public String cleanLimit(String limitSql) {
        String token = " OFFSET  ? ROWS FETCH NEXT ? ROWS ONLY";
        return limitSql.substring(0,limitSql.length() - token.length());
    }
}
