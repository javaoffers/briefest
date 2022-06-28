package com.javaoffers.batis.modelhelper.fun.condition.insert;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create by cmj
 */
public class AllColValueCondition implements InsertCondition {

    private Object model;

    private Class modelClass;

    private String sqlColNames;

    private String sqlValues;

    private Map<String, Object> param = new HashMap<>();


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

    public AllColValueCondition(Class modelClass, Object model) {
        this.model = model;
        this.modelClass = modelClass;
        //left: colName. right: fieldName
        Map<String, Field> colAllAndFieldOnly = TableHelper.getColAllAndFieldOnly(this.modelClass);
        colAllAndFieldOnly.forEach((colName, field)->{
            try {
                Object oValue = field.get(model);
                if(oValue != null){
                    param.put(colName + getNextLong(), oValue);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        Set<String> colNamesSet = colAllAndFieldOnly.keySet();
        this.sqlColNames = String.join(", ", colNamesSet);
        StringBuilder valuesAppender = new StringBuilder("(");
        valuesAppender.append(String.join(",", param.keySet()));
        valuesAppender.append(")");
        this.sqlValues = valuesAppender.toString();
    }
}
