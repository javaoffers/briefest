package com.javaoffers.brief.modelhelper.sqlite;

import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;

/**
 * cmj
 */
public class SqliteLimitWordCondition extends LimitWordCondition {

    private String sql;

    public SqliteLimitWordCondition(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    @Override
    public String getSql() {
        if(sql == null){
            String startIndexTag = getNextTag();
            String lenTag = getNextTag();
            this.getParams().put(startIndexTag, super.startIndex);
            this.getParams().put(lenTag, super.pageSize);
            this.sql = getTag().getTag() +" #{"+lenTag+"} offset #{"+startIndexTag+"}";
        }
        return sql;
    }

    @Override
    public String cleanLimit(String limitSql) {
        String token = getTag().getTag() +" ? offset ?";
        return limitSql.substring(0, limitSql.length() - token.length());
    }
}
