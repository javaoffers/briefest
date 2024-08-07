package com.javaoffers.brief.modelhelper.sqlserver;

import com.javaoffers.brief.modelhelper.core.MoreSQLInfo;
import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.core.parse.InsertConditionParse;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertIntoCondition;
import com.javaoffers.brief.modelhelper.fun.condition.mark.OnDuplicateKeyUpdateMark;
import com.javaoffers.brief.modelhelper.utils.Assert;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 10:44
 */
public class SqlServerInsertConditionParse extends InsertConditionParse {

    public static ConditionTag conditionTag = ConditionTag.INSERT_INTO;

    @Override
    public SQLStatement doParse(LinkedList<Condition> conditions) {
        return parseInsert(conditions);
    }

    private SQLStatement parseInsert(LinkedList<Condition> conditions) {
        InsertIntoCondition insertIntoTableCondition = (InsertIntoCondition) conditions.pollFirst();

        String insertIntoTableSql = insertIntoTableCondition.getSql(); //insert into table
        StringBuilder insertColNamesAppender = new StringBuilder(); // ( colName ,,, )
        ArrayList<String> moreSql = new ArrayList<>();
        StringBuilder insertValueAppender = new StringBuilder(); // values ( #{xx} ,,, )
        ArrayList<Map<String, Object>> paramsList = new ArrayList<>(); // for colAll(xx)
        HashMap<String, Object> valuesParam = new HashMap<>(); // for col(xx)
        boolean isColValueCondition = false;
        boolean isDupUpdateSql = conditions.peekLast() instanceof OnDuplicateKeyUpdateMark;// for dupUpdate

        Condition condition = null;
        while ((condition = conditions.pollFirst()) != null) {
            if (condition instanceof ColValueCondition) {
                ColValueCondition colValueCondition = (ColValueCondition) condition;
                Map<String, Object> params = condition.getParams();//只有一个值
                Assert.isTrue(params.size() == 1, "必须存在一个值");
                if (insertColNamesAppender.length() == 0) {
                    isColValueCondition = true;
                    // ( colName ,,,)
                    insertColNamesAppender.append("(");

                    // values (#{xx},,,)
                    insertValueAppender.append(ConditionTag.VALUES.getTag());
                    insertValueAppender.append("(");

                } else {
                    insertColNamesAppender.append(",");
                    insertValueAppender.append(",");

                }
                insertColNamesAppender.append(colValueCondition.getSql());// colName
                Set<String> strings = params.keySet();
                String key = strings.iterator().next();
                insertValueAppender.append("#{");
                insertValueAppender.append(key);
                insertValueAppender.append("}");
                valuesParam.put(key, params.get(key));

            } else if (condition instanceof InsertAllColValueCondition) {

                InsertAllColValueCondition allColValueCondition = (InsertAllColValueCondition) condition;
                Class modelClass = allColValueCondition.getModelClass();
                Object model = allColValueCondition.getModel();
                SqlServerInsertAllColValueCondition sqlServerInsertAllColValueCondition = new SqlServerInsertAllColValueCondition(modelClass, model);
                sqlServerInsertAllColValueCondition.init(isDupUpdateSql);
                if(StringUtils.isBlank(sqlServerInsertAllColValueCondition.getOnDuplicate())){
                    isDupUpdateSql = false; //no primary col
                    insertValueAppender = new StringBuilder();
                    insertColNamesAppender = new StringBuilder();
                    // ( colName ,,,)
                    insertColNamesAppender.append(sqlServerInsertAllColValueCondition.getSql());
                    // values ( #{xx} ,,,)
                    insertValueAppender.append(ConditionTag.VALUES.getTag());
                    insertValueAppender.append(sqlServerInsertAllColValueCondition.getValuesSql());
                    // (colName ,,, ) values ( #{xx} ,,,)
                    moreSql.add(insertColNamesAppender.append(insertValueAppender.toString()).toString());
                } else {
                    moreSql.add(sqlServerInsertAllColValueCondition.getOnDuplicateString());
                }
                paramsList.add(sqlServerInsertAllColValueCondition.getParams());
            }
        }

        if (isColValueCondition) {
            insertColNamesAppender.append(")");
            insertValueAppender.append(")");
            paramsList.add(valuesParam);
            if(isDupUpdateSql){
                SqlServerInsertAllColValueCondition sqlServerInsertAllColValueCondition =
                        new SqlServerInsertAllColValueCondition(insertIntoTableCondition.getModelClass(), null);
                sqlServerInsertAllColValueCondition.setParam(valuesParam);
                sqlServerInsertAllColValueCondition.setSqlColNames(insertColNamesAppender.toString());
                //remove values keyword
                sqlServerInsertAllColValueCondition.setSqlValues(insertValueAppender.toString().substring(ConditionTag.VALUES.getTag().length()));
                sqlServerInsertAllColValueCondition.parseDupInsertSql();
                // merge info
                if(StringUtils.isNotBlank(sqlServerInsertAllColValueCondition.getOnDuplicate())){
                    moreSql.add(sqlServerInsertAllColValueCondition.getOnDuplicateString());
                }else{
                    // 保存数据中没有唯一索引，
                    isDupUpdateSql = false;
                }
            }

            if(!isDupUpdateSql) {
                // ( colName ,,,) values ( #{xx},,,)
                moreSql.add(insertColNamesAppender.append(insertValueAppender.toString()).toString());
            }
        }

        // insert into or merge into
        insertIntoTableSql = isDupUpdateSql ?
                insertIntoTableSql.replaceAll(ConditionTag.INSERT_INTO.getTag(), ConditionTag.MERGE_INTO.getTag()) : insertIntoTableSql;

        Assert.isTrue(moreSql.size() == paramsList.size(), " data asymmetry ");
        MoreSQLInfo moreSQLInfo = new MoreSQLInfo();
        HashMap<String, SQLStatement> batch = new HashMap<>();
        for (int i = 0; i < moreSql.size(); i++) {
            String sql = insertIntoTableSql + moreSql.get(i);
            Map<String, Object> sqlParam = paramsList.get(i);
            SQLStatement sqlStatement = batch.get(sql);
            if (sqlStatement == null) {
                ArrayList parems = new ArrayList<>();
                parems.add(sqlParam);
                sqlStatement = SQLStatement.builder()
                        .aClass(insertIntoTableCondition.getModelClass())
                        .params(parems)
                        .sql(sql)
                        .status(true)
                        .build();
                batch.put(sql, sqlStatement);
            } else {
                sqlStatement.getParams().add(sqlParam);
            }

        }
        moreSQLInfo.addAllSqlInfo(batch.values());
        return moreSQLInfo;
    }
}
