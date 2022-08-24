package com.javaoffers.batis.modelhelper.fun.condition.insert;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

import java.lang.reflect.Field;
import java.util.*;

/**
 * create by cmj
 */
public class InsertAllColValueCondition implements InsertCondition {

    private Object model;

    private Class modelClass;

    //应该是表的全字段.
    private String sqlColNames;

    private String sqlValues;

    private HashMap<String, Object> param = new LinkedHashMap<>();


    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.VALUES;
    }

    @Override
    public String getSql() {
        return sqlColNames;
    }

    @Override
    public Map<String, Object> getParams() {
        return param;
    }

    @Override
    public String getValuesSql() {
        return this.sqlValues;
    }

    public InsertAllColValueCondition(Class modelClass, Object model) {
        this.model = model;
        this.modelClass = modelClass;
        //left: colName. right: fieldName
        Map<String, Field> colAllAndFieldOnly = TableHelper.getColAllAndFieldOnly(this.modelClass);
        colAllAndFieldOnly.forEach((colName, field)->{
            try {
                Object oValue = field.get(model);
                //value 可以为null
                if(oValue != null){
                    param.putIfAbsent(colName, oValue);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        });
        Set<String> colNamesSet = param.keySet();
        this.sqlColNames = "( "+ String.join(", ", colNamesSet)+" )";
        StringBuilder valuesAppender = new StringBuilder("(");
        LinkedList<String> colNames = new LinkedList<>();
        for(String colName : colNamesSet){
            colNames.add("#{"+colName+"}");
        }
        valuesAppender.append(String.join(",", colNames));
        valuesAppender.append(")");
        this.sqlValues = valuesAppender.toString();
    }
}
