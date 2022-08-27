package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.core.SQLParse;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class CondSQLCondition extends WhereOnCondition<Object> {
    private String sql;
    private Map<String,Object> params = Collections.EMPTY_MAP;

    public CondSQLCondition(String sql, Map<String, Object> params) {
        HashMap<String, Object> paramsNew = new HashMap<>();
        int idx = 0;
        this.sql = sql;
        StringBuilder paramKeyNew = new StringBuilder();
        if(params!=null){
            Matcher matcher = SQLParse.compile.matcher(sql);
            while(matcher.find()) {
                String result = matcher.group(1);
                String paramKey = result.substring(2, result.length() - 1);
                Object paramValue = params.get(paramKey);
                if(paramValue instanceof Collection){
                    Collection pvs = (Collection) paramValue;
                    for(Object pv : pvs){
                        if(paramKeyNew.length() != 0){
                            paramKeyNew.append(", ");
                        }
                        String sqlParm = paramKey + idx;
                        ++idx;
                        paramKeyNew.append("#{");
                        paramKeyNew.append(sqlParm);
                        paramKeyNew.append("}");
                        paramsNew.put(sqlParm, pv);
                    }
                    this.sql = this.sql.replace("#{"+paramKey+"}", paramKeyNew);
                }else{
                    paramsNew.put(paramKey,paramValue);
                }
            }
        }
        this.params = paramsNew;
    }

    public CondSQLCondition(String sql) {
        this.sql = sql;
    }

    @Override
    public String getSql() {
        return this.sql;
    }

    @Override
    public Map<String, Object> getParams() {
        return this.params;
    }
}
