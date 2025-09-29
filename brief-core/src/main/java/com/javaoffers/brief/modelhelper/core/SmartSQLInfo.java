package com.javaoffers.brief.modelhelper.core;


import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 多功能SQl片段
 */
public class SmartSQLInfo extends CrudSQLStatement {

    /**
     * 用于sharding
     */
    private HeadCondition headCondition;

    /**
     * 解析后的sql片段
     */
    private List<CrudSQLStatement> sqlStatements = new ArrayList<>();

    public SmartSQLInfo() {
        super();
    }

    public void addSqlInfo(CrudSQLStatement sqlStatement){
        if(sqlStatement !=null && sqlStatement.isStatus()){
            if(sqlStatement instanceof SmartSQLInfo){
                SmartSQLInfo moreSQLInfo = (SmartSQLInfo) sqlStatement;
                addAllSqlInfo(moreSQLInfo.getSqlStatements());
            }else{
                sqlStatements.add(sqlStatement);
            }

        }
    }

    public void addAllSqlInfo(Collection<CrudSQLStatement> sqlStatements){
        if(!CollectionUtils.isEmpty(sqlStatements)){
            for(CrudSQLStatement sqlStatement : sqlStatements){
                this.addSqlInfo(sqlStatement);
            }
        }
    }

    public List<CrudSQLStatement> getSqlStatements(){
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
        StringBuilder sqlAppender = new StringBuilder(headCondition.isSharding()?" ":"");

        for (CrudSQLStatement sqlStatement : sqlStatements) {
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
        for (CrudSQLStatement sqlStatement : sqlStatements) {
            params.addAll(sqlStatement.getParams());
        }
        return params;
    }

    @Override
    public boolean isStatus() {
        return !sqlStatements.isEmpty();
    }
}
