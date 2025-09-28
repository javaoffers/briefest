package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.core.Limit;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.IgnoreAndOrWordCondition;

/**
 * @Description: exists sql 语句
 * @Auther: create by cmj on 2022/5/3 02:54
 */
public class LimitWordCondition<V> extends WhereOnCondition<V> implements IgnoreAndOrWordCondition, Limit {

    public int pageNum;

    public int pageSize;

    public int startIndex;

    public int len;

    public String sql;

    public LimitWordCondition(int pageNum, int pageSize) {
        limit(pageNum, pageSize);
    }

    @Override
    public void setHeadCondition(HeadCondition headCondition) {
        super.setHeadCondition(headCondition);
        headCondition.setLimitWordCondition(this);
    }

    @Override
    public String getSql() {
        if(sql==null){
            String startIndexTag = getNextTag();
            String lenTag = getNextTag();
            this.getParams().put(startIndexTag, this.startIndex);
            this.getParams().put(lenTag, this.len);
            this.sql = getTag().getTag() + " #{" + startIndexTag + "} , #{" + lenTag + "}";
        }
        return sql;
    }

    @Override
    public ConditionTag getTag() {
        return ConditionTag.LIMIT;
    }

    @Override
    public int pageNum() {
        return this.pageNum;
    }

    @Override
    public int pageSize() {
        return this.pageSize;
    }

    @Override
    public int startIndex() {
        return this.startIndex;
    }

    @Override
    public int len() {
        return this.len;
    }

    @Override
    public String cleanLimit(String limitSql) {
        return limitSql.replaceAll( getTag().getTag() + " ? , ?","");
    }

    public void limit(int pageNum, int pageSize){
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startIndex = (pageNum - 1) * pageSize;
        this.len = pageSize;
        cleanAndOrTag();
    }

}
