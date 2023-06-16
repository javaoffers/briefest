package com.javaoffers.brief.modelhelper.anno.fun.parse;

/**
 * @author mingJie
 */
public class ParseSqlFunResult {
    String sqlFun;
    boolean isFun;
    boolean excludeColAll;

    public ParseSqlFunResult(String sqlFun, boolean isFun, boolean excludeColAll) {
        this.sqlFun = sqlFun;
        this.isFun = isFun;
        this.excludeColAll = excludeColAll;
    }

    public String getSqlFun() {
        return sqlFun;
    }

    public boolean isFun() {
        return isFun;
    }

    public boolean isExcludeColAll(){
        return excludeColAll;
    }

    public void setExcludeColAll(boolean excludeColAll) {
        this.excludeColAll = excludeColAll;
    }

    public void setFun(boolean fun) {
        isFun = fun;
    }
}
