package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.exception.FindColException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author mingJie
 */
public class SqlColInfo {

    private String tableName;

    private String colName;

    //fieldName
    private String aliasName;

    boolean sqlFun = false;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColName() {
        return colName;
    }

    public String getColNameNotBlank(){
        if(StringUtils.isBlank(colName)){
            //The corresponding column name was not found in the user table
            throw new FindColException("No matching col name found in the " +
                    tableName + " table for " + this.getAliasName() +" field");
        }
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public boolean isSqlFun() {
        return sqlFun;
    }

    public void setSqlFun(boolean sqlFun) {
        this.sqlFun = sqlFun;
    }

    public SqlColInfo(String tableName, String colName, String aliasName, boolean sqlFun) {
        this.tableName = tableName;
        this.colName = colName;
        this.aliasName = aliasName;
        this.sqlFun = sqlFun;
    }

}
