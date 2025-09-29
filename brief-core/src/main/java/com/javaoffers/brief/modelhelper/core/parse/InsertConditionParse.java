package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.brief.modelhelper.core.SmartSQLInfo;
import com.javaoffers.brief.modelhelper.core.CrudSQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertIntoCondition;
import com.javaoffers.brief.modelhelper.fun.condition.mark.OnDuplicateKeyUpdateMark;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfo;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfoPosition;
import com.javaoffers.brief.modelhelper.utils.ModelInfo;
import com.javaoffers.brief.modelhelper.utils.SqlColInfo;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.*;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 10:44
 */
public class InsertConditionParse extends AbstractParseCondition {

    public static ConditionTag conditionTag  = ConditionTag.INSERT_INTO;

    @Override
    public CrudSQLStatement doParse(LinkedList<Condition> conditions) {
        return parseInsert(conditions);
    }

    private CrudSQLStatement parseInsert(LinkedList<Condition> conditions) {
        InsertIntoCondition insertIntoTableCondition = (InsertIntoCondition)conditions.pollFirst();

        String insertIntoTableSql = insertIntoTableCondition.getSql();

        ArrayList<String> moreSql = new ArrayList<>();
        StringBuilder insertColNamesAppender = new StringBuilder();
        StringBuilder insertValueAppender = new StringBuilder();
        ArrayList<Map<String, Object>> paramsList = new ArrayList<>();
        boolean isDupUpdateSql = conditions.peekLast() instanceof OnDuplicateKeyUpdateMark;

        List<String> dupUpdateSql = new ArrayList<>();
        Condition condition = null;
        InsertAllColValueCondition insertAllColValueCondition = null;
        while( (condition = conditions.pollFirst()) != null){
            //将colVal 转换为 ColAll
            if(condition instanceof ColValueCondition) {
                Map<String, Object> params = condition.getParams();//只有一个值
                Assert.isTrue(params.size() == 1,"必须存在一个值");
                ColValueCondition colValueCondition = (ColValueCondition) condition;
                SqlColInfo sqlColInfo = colValueCondition.getSqlColInfo();
                TableInfo tableInfo = sqlColInfo.getTableInfo();
                ModelInfo modelInfo = sqlColInfo.getModelInfo();
                Object modelObject = modelInfo.getConstructor().newc();
                ArrayList<Object> valueList = new ArrayList<>();
                valueList.add(colValueCondition.getValue());
                ArrayList<String> colNames = new ArrayList<>();
                colNames.add(colValueCondition.getColName());
                //获取全部colValue
                while ((condition = conditions.pollFirst()) != null){
                    if(condition instanceof ColValueCondition){
                        colValueCondition = (ColValueCondition) condition;
                        valueList.add(colValueCondition.getValue());
                        colNames.add(colValueCondition.getColName());
                    }
                }
                List<ModelFieldInfoPosition> onesCol = modelInfo.getOnesCol(colNames);
                Assert.isTrue(onesCol.size() == colNames.size(),"insert col error");

                for (int i = 0; i < onesCol.size(); i++) {
                    ModelFieldInfoPosition oneCol = onesCol.get(i);
                    Object value = valueList.get(i);
                    ModelFieldInfo modelFieldInfo = oneCol.getModelFieldInfo();
                    Class fieldGenericClass = modelFieldInfo.getFieldGenericClass();
                    Object acValue = ConvertRegisterSelectorDelegate.convert.converterObject(fieldGenericClass, value);
                    modelFieldInfo.getSetter().setter(modelObject, acValue);
                }
                insertAllColValueCondition = new InsertAllColValueCondition(tableInfo.getModelClass(), modelObject);
                conditions.addFirst(insertAllColValueCondition);

            } else if(condition instanceof InsertAllColValueCondition){
                insertValueAppender = new StringBuilder();
                insertColNamesAppender = new StringBuilder();
                InsertAllColValueCondition allColValueCondition = (InsertAllColValueCondition) condition;
                allColValueCondition.init(isDupUpdateSql);

                // ( colName ,,, )
                insertColNamesAppender.append(allColValueCondition.getSql());

                // values (colName ,,,)
                insertValueAppender.append(ConditionTag.VALUES.getTag());
                insertValueAppender.append(allColValueCondition.getValuesSql());
                paramsList.add(allColValueCondition.getParams());

                // (colName ,,, ) values (colName ,,,)
                moreSql.add(insertColNamesAppender.append(insertValueAppender.toString()).toString());

                //on duplicate key update
                if(isDupUpdateSql){
                    dupUpdateSql.add(allColValueCondition.getOnDuplicateString());
                }
            }
        }

        Assert.isTrue(moreSql.size() == paramsList.size()," data asymmetry ");
        SmartSQLInfo moreSQLInfo = new SmartSQLInfo();
        HashMap<String, CrudSQLStatement> batch = new HashMap<>();
        for(int i =0; i < moreSql.size(); i++){
            String sql = insertIntoTableSql + moreSql.get(i);
            if(isDupUpdateSql){
                sql = sql + dupUpdateSql.get(i);
            }
            Map<String, Object> sqlParam = paramsList.get(i);
            CrudSQLStatement sqlStatement = batch.get(sql);

            if(sqlStatement == null){
                ArrayList parems = new ArrayList<>();
                parems.add(sqlParam);
                sqlStatement = CrudSQLStatement.builder()
                        .aClass(insertIntoTableCondition.getModelClass())
                        .params(parems)
                        .sql(sql)
                        .status(true)
                        .build();
                batch.put(sql, sqlStatement);
            }else{
                sqlStatement.getParams().add(sqlParam);
            }

        }
        moreSQLInfo.addAllSqlInfo(batch.values());
        return moreSQLInfo;
    }


}
