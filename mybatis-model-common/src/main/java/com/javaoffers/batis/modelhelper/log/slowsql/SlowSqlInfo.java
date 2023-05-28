package com.javaoffers.batis.modelhelper.log.slowsql;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 15:55
 */
public class SlowSqlInfo<R,T> {
    private R result;
    private T sqlInfo;

    public SlowSqlInfo(R result, T sqlInfo) {
        this.result = result;
        this.sqlInfo = sqlInfo;
    }

    public R getResult() {
        return result;
    }

    public T getSqlInfo() {
        return sqlInfo;
    }
}
