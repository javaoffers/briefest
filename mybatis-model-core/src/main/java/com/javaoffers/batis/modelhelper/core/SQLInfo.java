package com.javaoffers.batis.modelhelper.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 封装
 * @Auther: create by cmj on 2022/5/22 17:43
 */
public class SQLInfo {
    boolean status; //有效状态
    Class aClass;
    String sql;
    List<Map<String,Object>> params = new LinkedList<>();

    public SQLInfo() {
    }

    public SQLInfo(boolean status, Class aClass, String sql, List<Map<String, Object>> params){
        this.status = status;
        this.aClass = aClass;
        this.sql = sql;
        this.params = params;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Class getAClass() {
        return aClass;
    }

    public void setAClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }

    public static SQLInfoBuilder builder(){
        return SQLInfoBuilder.aSQLInfo();
    }

    public static final class SQLInfoBuilder {
        boolean status;
        Class aClass;
        String sql;
        List<Map<String,Object>> params = new LinkedList<>();

        private SQLInfoBuilder() {
        }

        public static SQLInfoBuilder aSQLInfo() {
            return new SQLInfoBuilder();
        }

        public SQLInfoBuilder status(boolean status) {
            this.status = status;
            return this;
        }

        public SQLInfoBuilder aClass(Class aClass) {
            this.aClass = aClass;
            return this;
        }

        public SQLInfoBuilder sql(String sql) {
            this.sql = sql;
            return this;
        }

        public SQLInfoBuilder params(List<Map<String, Object>> params) {
            this.params = params;
            return this;
        }

        public SQLInfo build() {
            return new SQLInfo(status, aClass, sql, params);
        }
    }
}
