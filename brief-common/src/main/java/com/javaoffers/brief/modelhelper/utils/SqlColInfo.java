package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.exception.FindColException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author mingJie
 */
public class SqlColInfo {

    private TableInfo tableInfo;

    private  ModelInfo modelInfo;

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

    public boolean isSqlFun() {
        return sqlFun;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public ModelInfo getModelInfo() {
        return modelInfo;
    }

    public SqlColInfo(ModelInfo modelInfo, TableInfo tableInfo, String colName, String aliasName, boolean sqlFun) {
        this.tableInfo = tableInfo;
        this.modelInfo = modelInfo;
        this.tableName = tableInfo.getTableName();
        this.colName = colName;
        this.aliasName = aliasName;
        this.sqlFun = sqlFun;
    }

}
