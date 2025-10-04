package com.javaoffers.brief.modelhelper.core;


import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 多功能SQl片段
 */
public class SmartSQLInfo extends CrudSQLStatement {

    /**
     * 用于sharding. native sql 将不会存在此对象.
     */
    private HeadCondition headCondition;

    /**
     * Parsed SQL snippet.
     * key: native sql.
     * value: Sql Statement
     */
    private Map<String, CrudSQLStatement> sqlStatements = new HashMap<>();

    public SmartSQLInfo() {
        super();
    }

    public void addSqlInfo(CrudSQLStatement sqlStatement){
        if(sqlStatement !=null && sqlStatement.isStatus()){
            if(sqlStatement instanceof SmartSQLInfo){
                SmartSQLInfo moreSQLInfo = (SmartSQLInfo) sqlStatement;
                addAllSqlInfo(moreSQLInfo.getSqlStatements());
            }else{
                //same sql, merge statement
                CrudSQLStatement sqlStatementOld = sqlStatements.get(sqlStatement.getSql());
                if(sqlStatementOld == null){
                    sqlStatements.put(sqlStatement.getSql(), sqlStatement);
                    return;
                }
                sqlStatementOld.getParams().addAll(sqlStatement.getParams());
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
        return new ArrayList<>(sqlStatements.values());
    }

    public HeadCondition getHeadCondition() {
        return headCondition;
    }

    public void setHeadCondition(HeadCondition headCondition) {
        this.headCondition = headCondition;
    }

    @Override
    public String getSql() {
        StringBuilder sqlAppender = new StringBuilder(headCondition != null && headCondition.isSharding()?" ":"");

        for (CrudSQLStatement sqlStatement : sqlStatements.values()) {
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
        for (CrudSQLStatement sqlStatement : sqlStatements.values()) {
            params.addAll(sqlStatement.getParams());
        }
        return params;
    }

    @Override
    public boolean isStatus() {
        return !sqlStatements.isEmpty();
    }
}
