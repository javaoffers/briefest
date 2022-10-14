package com.javaoffers.batis.modelhelper.anno.fun.parse;

/**
 * @author mingJie
 */
public class ParseSqlFunResult {
    String sqlFun;
    boolean isFun;

    public ParseSqlFunResult(String sqlFun, boolean isFun) {
        this.sqlFun = sqlFun;
        this.isFun = isFun;
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

    public void setFun(boolean fun) {
        isFun = fun;
    }
}
