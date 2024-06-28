package com.javaoffers.brief.modelhelper.oracle;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertCondition;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfo;
import com.javaoffers.brief.modelhelper.utils.ModelInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create by cmj
 */
public class OracleInsertAllColValueCondition implements InsertCondition {

    private Object model;

    private Class modelClass;

    //Should be the full field of the table.
    private String sqlColNames;

    private String sqlValues;

    private HashMap<String, Object> param = new LinkedHashMap<>();

    private StringBuilder onDuplicate = new StringBuilder();

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

    public OracleInsertAllColValueCondition(Class modelClass, Object model) {
        this.model = model;
        this.modelClass = modelClass;
    }

    //Initialize information to ensure that sql is generated during parsing
    public void init() {
        //生成唯一key
        gkeyProcess(modelClass, model);
        //left: colName. right: fieldName
        Map<String, List<Field>> colAllAndFieldOnly = TableHelper.getOriginalColAllAndFieldOnly(this.modelClass);
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
        //给字段增加``
        Set<String> colNamesSet = param.keySet();
        List<String> expressionColNamesSet = param.keySet().stream().map(colName->"`"+colName+"`").collect(Collectors.toList());
        this.sqlColNames = "( "+ String.join(", ", expressionColNamesSet)+" )";
        byte status = 0;
        for(String colName : expressionColNamesSet){
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

    private void gkeyProcess(Class modelClass, Object model) {
        ModelInfo modelInfo = TableHelper.getModelInfo(modelClass);
        List<ModelFieldInfo> gkeyUniqueModels = modelInfo.getGkeyUniqueModels();
        if(CollectionUtils.isNotEmpty(gkeyUniqueModels)){
            gkeyUniqueModels.forEach(gkeyUniqueModel->{
                Object uniqueValue = gkeyUniqueModel.getGetter().getter(model);
                if(uniqueValue == null
                        ||(uniqueValue instanceof Number && uniqueValue.toString().equals("0"))){

                    uniqueValue = gkeyUniqueModel.getUniqueKeyGenerate().generate();
                    gkeyUniqueModel.getSetter().setter(model, uniqueValue);
                }
            });
        }
    }

    public String getOnDuplicate() {
        return onDuplicate.toString();
    }
}
