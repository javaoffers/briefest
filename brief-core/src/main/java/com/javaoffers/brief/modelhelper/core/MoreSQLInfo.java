package com.javaoffers.brief.modelhelper.core;


import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MoreSQLInfo extends SQLStatement {

    /**
     * 用于sharding
     */
    HeadCondition headCondition;

    /**
     * 解析后的sql片段
     */
    private List<SQLStatement> sqlStatements = new LinkedList<>();

    public MoreSQLInfo() {
        super();
    }

    public void addSqlInfo(SQLStatement sqlStatement){
        if(sqlStatement !=null && sqlStatement.isStatus()){
            if(sqlStatement instanceof MoreSQLInfo){
                MoreSQLInfo moreSQLInfo = (MoreSQLInfo) sqlStatement;
                addAllSqlInfo(moreSQLInfo.getSqlStatements());
            }else{
                sqlStatements.add(sqlStatement);
            }

        }
    }

    public void addAllSqlInfo(Collection<SQLStatement> sqlStatements){
        if(!CollectionUtils.isEmpty(sqlStatements)){
            for(SQLStatement sqlStatement : sqlStatements){
                this.addSqlInfo(sqlStatement);
            }
        }
    }

    public List<SQLStatement> getSqlStatements(){
        return sqlStatements;
    }

    public HeadCondition getHeadCondition() {
        return headCondition;
    }

    public void setHeadCondition(HeadCondition headCondition) {
        this.headCondition = headCondition;
    }

    @Override
    public String getSql() {
        StringBuilder sqlAppender = new StringBuilder();
        for (SQLStatement sqlStatement : sqlStatements) {
            if(sqlAppender.length()>0){
                sqlAppender.append("\n");
            }
            sqlAppender.append(sqlStatement.getSql());
        }
        return sqlAppender.toString();
    }

    @Override
    public List<Map<String, Object>> getParams() {
        List<Map<String, Object>> params = new ArrayList<>();
        for (SQLStatement sqlStatement : sqlStatements) {
            params.addAll(sqlStatement.getParams());
        }
        return params;
    }

    @Override
    public boolean isStatus() {
        return !sqlStatements.isEmpty();
    }
}
