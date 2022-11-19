package com.javaoffers.batis.modelhelper.fun.condition.insert;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import com.javaoffers.batis.modelhelper.utils.TableInfo;

import java.lang.reflect.Field;
import java.util.*;

/**
 * create by cmj
 */
public class InsertAllColValueCondition implements InsertCondition {

    private Object model;

    private Class modelClass;

    //Should be the full field of the table.
    private String sqlColNames;

    private String sqlValues;

    private HashMap<String, Object> param = new LinkedHashMap<>();

    private StringBuilder onDuplicate = new StringBuilder(ConditionTag.ON_DUPLICATE_KEY_UPDATE.getTag());

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
        Map<String, List<Field>> colAllAndFieldOnly = TableHelper.getOriginalColAllAndFieldOnly(this.modelClass);
        TableInfo tableInfo = TableHelper.getTableInfo(this.modelClass);
        colAllAndFieldOnly.forEach((colName, fields)->{
            try {
                Object oValue = null;
                for(int i = 0; i < fields.size(); i++){
                    Field field = fields.get(i);
                    Object o = field.get(model);
                    //take the first non-null value
                    if(o != null){
                        oValue = o;
                        break;
                    }
                }

                if(oValue != null){
                    param.put(colName, oValue);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        });
        Set<String> colNamesSet = param.keySet();
        this.sqlColNames = "( "+ String.join(", ", colNamesSet)+" )";
        byte status = 0;
        for(String colName : colNamesSet){
            if(status != 0){
                this.onDuplicate.append(",");
            }
            status += 1;
            this.onDuplicate.append(colName);
            this.onDuplicate.append( " = values(");
            this.onDuplicate.append(colName);
            this.onDuplicate.append(")");
        }
        StringBuilder valuesAppender = new StringBuilder("(");
        LinkedList<String> colNames = new LinkedList<>();
        for(String colName : colNamesSet){
            colNames.add("#{"+colName+"}");
        }
        valuesAppender.append(String.join(",", colNames));
        valuesAppender.append(")");
        this.sqlValues = valuesAppender.toString();
    }

    public String getOnDuplicate() {
        return onDuplicate.toString();
    }
}
