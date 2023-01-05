package com.javaoffers.batis.modelhelper.anno.fun.parse;

/**
 * @author mingJie
 */
public class ParseSqlFunResult {
    String sqlFun;
    boolean isFun;
    //Is it a group function, group fun is a special case of isFun
    boolean isGroup;

    public ParseSqlFunResult(String sqlFun, boolean isFun, boolean isGroup) {
        this.sqlFun = sqlFun;
        this.isFun = isFun;
        this.isGroup = isGroup;
    }

    public String getSqlFun() {
        return sqlFun;
    }

    public void setSqlFun(String sqlFun) {
        this.sqlFun = sqlFun;
    }

    public boolean isFun() {
        return isFun;
    }

    public boolean isGroup(){
        return isGroup;
    }

    public void setFun(boolean fun) {
        isFun = fun;
    }
}
