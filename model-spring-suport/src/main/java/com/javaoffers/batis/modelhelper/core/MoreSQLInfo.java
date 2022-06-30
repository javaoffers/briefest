package com.javaoffers.batis.modelhelper.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MoreSQLInfo extends SQLInfo {

    private List<SQLInfo> sqlInfos = new LinkedList<>();

    MoreSQLInfo(Class aClass, String sql, List<Map<String, Object>> params) {
        super(aClass, sql, params);
        sqlInfos.add(new SQLInfo(aClass,sql,params));
    }

    public void addSqlInfo(SQLInfo sqlInfo){
        sqlInfos.add(sqlInfo);
    }

    public List<SQLInfo> getSqlInfos(){
        return sqlInfos;
    }

}
