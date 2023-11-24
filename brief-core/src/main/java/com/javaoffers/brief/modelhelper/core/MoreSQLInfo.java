package com.javaoffers.brief.modelhelper.core;


import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MoreSQLInfo extends SQLStatement {

    private List<SQLStatement> sqlStatements = new LinkedList<>();

    public MoreSQLInfo() {
        super();
    }

    public void addSqlInfo(SQLStatement sqlStatement){
        if(sqlStatement !=null && sqlStatement.isStatus()){
            sqlStatements.add(sqlStatement);
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

}
