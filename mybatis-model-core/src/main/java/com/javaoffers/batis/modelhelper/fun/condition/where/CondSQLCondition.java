package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.core.SQLParse;
import com.javaoffers.batis.modelhelper.exception.ParseParamException;
import com.javaoffers.batis.modelhelper.fun.condition.where.WhereOnCondition;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
                if(paramValue == null){
                    throw new ParseParamException("param is null for " + paramKey);
                }
                int length = 0 ;
                //If is an array, the array into the list
                if(paramValue.getClass().isArray() &&  (length = Array.getLength(paramValue)) > 0){
                    ArrayList<Object> list = new ArrayList<>(length);
                    for(int i = 0 ; i < length; i++){
                        list.add(Array.get(paramValue, i));
                    }
                    paramValue = list;
                }
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
