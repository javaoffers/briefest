package com.javaoffers.batis.modelhelper.core;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MoreSQLInfo extends SQLInfo {

    private List<SQLInfo> sqlInfos = new LinkedList<>();

    public MoreSQLInfo() {
        super();
    }

    public void addSqlInfo(SQLInfo sqlInfo){
        if(sqlInfo!=null && sqlInfo.isStatus()){
            sqlInfos.add(sqlInfo);
        }
    }

    public List<SQLInfo> getSqlInfos(){
        return sqlInfos;
    }

}
