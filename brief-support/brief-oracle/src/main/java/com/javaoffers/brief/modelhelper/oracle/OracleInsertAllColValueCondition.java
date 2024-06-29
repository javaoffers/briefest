package com.javaoffers.brief.modelhelper.oracle;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.brief.modelhelper.utils.*;
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
public class OracleInsertAllColValueCondition extends InsertAllColValueCondition {

    private Object model;

    private Class modelClass;

    //Should be the full field of the table.
    private String sqlColNames;

    private String sqlValues;

    private HashMap<String, Object> param = new LinkedHashMap<>();

    private StringBuilder onDuplicate = new StringBuilder(ConditionTag.USING.getTag());

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

    public OracleInsertAllColValueCondition(Class modelClass, Object model) {
        super(modelClass, model);
        this.model = model;
        this.modelClass = modelClass;
    }

    //Initialize information to ensure that sql is generated during parsing
    public void init(boolean isDupUpdate) {
        modelInfo = TableHelper.getModelInfo(modelClass);
        //生成唯一key
        gkeyProcess();
        //left: colName. right: fieldName
        parseParams();
        // parse:（col ,,,）values (#{xx},,,). note: no insert into keyword
        parseInsertSql();
        //parse merge into
        if(isDupUpdate){
            parseDupInsertSql();
        }
    }

    /**
     * MERGE INTO orders tgt
     * USING (
     *     SELECT 1001 AS order_number, 150 AS amount FROM dual
     * ) src
     * ON (tgt.order_number = src.order_number)
     * WHEN MATCHED THEN
     *     UPDATE SET tgt.amount = src.amount
     * WHEN NOT MATCHED THEN
     *     INSERT (order_number, customer_name, amount)
     *     VALUES ('xx', 'New Customer', 'xx');
     */
    public void parseDupInsertSql(){
        onDuplicate.append(ConditionTag.LK.getTag());
        //parse: SELECT 1001 AS order_number, 150 AS amount FROM dual
        onDuplicate.append(ConditionTag.SELECT.getTag());

        TableInfo tableInfo = TableHelper.getTableInfo(this.modelClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        List<String> primaryColNameList = this.param.keySet().stream().filter(primaryColNames::containsKey).collect(Collectors.toList());
        //处理 on 条件
        StringBuilder onCondition = new StringBuilder();
        onCondition.append(ConditionTag.ON.getTag());
        onCondition.append(ConditionTag.LK.getTag());
        // SELECT 1001 AS order_number, 150 AS amount FROM dual
        primaryColNameList.forEach( uniqueCol->{
            Object value = this.param.get(uniqueCol);
            if(value instanceof String){
                onDuplicate.append(ConditionTag.QUOTATION.getTag());
                onDuplicate.append(value);
                onDuplicate.append(ConditionTag.QUOTATION.getTag());
            }else{
                onDuplicate.append(value);
            }
            onDuplicate.append(ConditionTag.AS.getTag());
            onDuplicate.append(uniqueCol);


            // 拼接 ON (tgt.order_number = src.order_number)
            onCondition.append("src");
            onCondition.append(ConditionTag.PERIOD.getTag()); // .
            onCondition.append(uniqueCol);

            onCondition.append(tableInfo.getTableName());
            onCondition.append(ConditionTag.PERIOD.getTag()); // .
            onCondition.append(uniqueCol);
        });
        onDuplicate.append(ConditionTag.SELECT_FROM.getTag());// from
        onDuplicate.append("dual ");
        onDuplicate.append(ConditionTag.RK.getTag());
        onDuplicate.append("src ");

        onCondition.append(ConditionTag.RK.getTag()); // )
        onDuplicate.append(onCondition); // 拼接 on 条件

        //WHEN MATCHED THEN
        onDuplicate.append(ConditionTag.WHEN_MATCHED_THEN.getTag());
        onDuplicate.append(ConditionTag.UPDATE.getTag());
        onDuplicate.append(ConditionTag.SET.getTag());
        this.param.forEach((colName,value)->{
            onDuplicate.append(tableInfo.getTableName());
            onDuplicate.append(ConditionTag.PERIOD.getTag());
            onDuplicate.append("#{");
            onDuplicate.append(colName);
            onDuplicate.append("}");
        });

        //WHEN NOT MATCHED THEN
        onDuplicate.append(ConditionTag.WHEN_NOT_MATCHED_THEN.getTag());
        onDuplicate.append(ConditionTag.INSERT.getTag());
        onDuplicate.append(this.sqlColNames);
        onDuplicate.append(ConditionTag.VALUES.getTag());
        onDuplicate.append(this.sqlValues);

    }

    //解析普通插入
    public void parseInsertSql(){
        Set<String> colNamesSet = param.keySet();
        //给字段增加``
        List<String> expressionColNamesSet =
                param.keySet().stream()
                .map(colName -> ConditionTag.QUOTE + colName + ConditionTag.QUOTE) // `xx`
                .collect(Collectors.toList());
        //解析 ( colName ,,,)
        this.sqlColNames = ConditionTag.LK.getTag()+ String.join(ConditionTag.COMMA.getTag(), expressionColNamesSet)+ConditionTag.RK.getTag();

        //解析 ( #{xx}.. ) without values keyword
        StringBuilder valuesAppender = new StringBuilder(ConditionTag.LK.getTag());
        LinkedList<String> colNames = new LinkedList<>();
        for(String colName : colNamesSet){
            colNames.add("#{"+colName+"}");
        }
        valuesAppender.append(String.join(ConditionTag.COMMA.getTag(), colNames)); // ,
        valuesAppender.append(ConditionTag.RK.getTag());
        this.sqlValues = valuesAppender.toString();
    }

    public String getOnDuplicate() {
        return onDuplicate.toString();
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
}
