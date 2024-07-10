package com.javaoffers.brief.modelhelper.fun.condition.insert;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfo;
import com.javaoffers.brief.modelhelper.utils.ModelInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

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

    private StringBuilder onDuplicate = new StringBuilder();

    private List<String> expressionColNames;

    private ModelInfo modelInfo;

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
    }

    //Initialize information to ensure that sql is generated during parsing
    public void init(boolean isDuplicate) {
        //生成唯一key
        gkeyProcess();
        //left: colName. right: fieldName
        parseParams();
        //给字段增加``
        parseInsertSql();
        //parse dup
        if (isDuplicate) {
            parseDupInsertSql();
        }
    }

    public void parseInsertSql() {
        Set<String> colNamesSet = this.getParams().keySet();
        String quote = TableHelper.getTableInfo(modelClass).getDbType().getQuote();
        //给字段增加``
        this.expressionColNames = this.getParams()
                .keySet()
                .stream()
                .map(colName -> quote + colName + quote) // `xx`
                .collect(Collectors.toList());

        //解析 ( colName ,,,)
        this.sqlColNames = ConditionTag.LK.getTag()
                + String.join(ConditionTag.COMMA.getTag(), this.expressionColNames)
                + ConditionTag.RK.getTag();

        //解析 ( #{xx}.. ) without values keyword
        StringBuilder valuesAppender = new StringBuilder(ConditionTag.LK.getTag());
        LinkedList<String> colNames = new LinkedList<>();
        for (String colName : colNamesSet) {
            colNames.add("#{" + colName + "}");
        }

        valuesAppender.append(String.join(ConditionTag.COMMA.getTag(), colNames)); // ,
        valuesAppender.append(ConditionTag.RK.getTag());
        this.sqlValues = valuesAppender.toString();
    }

    public void parseDupInsertSql() {
        this.onDuplicate.append(ConditionTag.ON_DUPLICATE_KEY_UPDATE.getTag());
        String comma = ConditionTag.BLANK.getTag();
        for (String colName : this.expressionColNames) {
            this.onDuplicate.append(comma);
            this.onDuplicate.append(colName);
            this.onDuplicate.append(" = values(");
            this.onDuplicate.append(colName);
            this.onDuplicate.append(")");
            if (StringUtils.isBlank(comma)) {
                comma = ConditionTag.COMMA.getTag();
            }
        }
    }

    //解析参数
    public void parseParams() {
        Map<String, List<Field>> colAllAndFieldOnly = TableHelper.getOriginalColAllAndFieldOnly(this.modelClass);
        colAllAndFieldOnly.forEach((colName, fields) -> {
            try {
                Object oValue = null;
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    Object o = field.get(this.model);
                    //take the first non-null value
                    if (o != null) {
                        oValue = o;
                        break;
                    }
                }
                if (oValue != null) {
                    this.param.put(colName, oValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // 生成唯一值
    public void gkeyProcess() {
        this.modelInfo = TableHelper.getModelInfo(this.modelClass);
        List<ModelFieldInfo> gkeyUniqueModels = this.modelInfo.getGkeyUniqueModels();
        if (CollectionUtils.isNotEmpty(gkeyUniqueModels)) {
            gkeyUniqueModels.forEach(gkeyUniqueModel -> {
                Object uniqueValue = gkeyUniqueModel.getGetter().getter(this.model);
                if (uniqueValue == null
                        || (uniqueValue instanceof Number && uniqueValue.toString().equals("0"))) {

                    uniqueValue = gkeyUniqueModel.getUniqueKeyGenerate().generate();
                    gkeyUniqueModel.getSetter().setter(this.model, uniqueValue);
                }
            });
        }
    }

    public StringBuilder getOnDuplicate() {
        return this.onDuplicate;
    }

    public String getOnDuplicateString() {
        return this.onDuplicate.toString();
    }

    public Object getModel() {
        return this.model;
    }

    public Class getModelClass() {
        return this.modelClass;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public void setModelClass(Class modelClass) {
        this.modelClass = modelClass;
    }

    public void setSqlColNames(String sqlColNames) {
        this.sqlColNames = sqlColNames;
    }

    public void setSqlValues(String sqlValues) {
        this.sqlValues = sqlValues;
    }

    public void setParam(HashMap<String, Object> param) {
        this.param = param;
    }

    public void setOnDuplicate(StringBuilder onDuplicate) {
        this.onDuplicate = onDuplicate;
    }

    public void setExpressionColNames(List<String> expressionColNames) {
        this.expressionColNames = expressionColNames;
    }

    public String getSqlColNames() {
        return sqlColNames;
    }

    public String getSqlValues() {
        return sqlValues;
    }

    public HashMap<String, Object> getParam() {
        return param;
    }

    public List<String> getExpressionColNames() {
        return expressionColNames;
    }

}
