package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.condition.IgnoreAndOrWordCondition;
import com.javaoffers.batis.modelhelper.fun.condition.JoinTableCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OnConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.OrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.SelectColumnCondition;
import com.javaoffers.batis.modelhelper.fun.condition.SelectTableCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.insert.InsertIntoCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.AddPatchMarkCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateAllColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateCondtionMark;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


/**
 * @Description: 用于解析Condition
 * @Auther: create by cmj on 2022/5/22 13:47
 */
public class ConditionParse {

    public static SQLInfo conditionParse(LinkedList<Condition> conditions) {

        //判断类型
        Condition condition = conditions.get(0);
        try {
            ConditionTag conditionTag = condition.getConditionTag();
            switch (conditionTag) {
                case SELECT_FROM:
                    //解析select
                    return parseSelect(conditions);
                case INSERT_INTO:
                    return parseInsert(conditions);
                case UPDATE:
                    return parseUpdate(conditions);
            }
        }finally {
            condition.clean();
        }
        return null;
    }

    private static SQLInfo parseUpdate(LinkedList<Condition> conditions) {
        Condition condition = conditions.pollFirst();
        String updateTableSql = null;
        Class modelClass = null;
        if(condition instanceof UpdateCondtionMark){
            UpdateCondtionMark updateCondtionMark = (UpdateCondtionMark) condition;
            updateTableSql = updateCondtionMark.getSql();
            modelClass = updateCondtionMark.getModelCalss();
        }
        Assert.isTrue(updateTableSql != null, "update sql 解析出新问题");
        condition = conditions.pollFirst();
        if(condition == null || (!(condition instanceof UpdateColValueCondition)
                && !(condition instanceof UpdateAllColValueCondition))){
            //没有要更新的字段
            return null;
        }
        if(condition instanceof UpdateColValueCondition){
            UpdateColValueCondition updateColValueCondition = (UpdateColValueCondition) condition;
            StringBuilder updateAppender = new StringBuilder();
            HashMap<String, Object> upateParam = new HashMap<>();
            String colNameSql = updateColValueCondition.getSql();
            updateAppender.append(updateTableSql);
            updateAppender.append(ConditionTag.SET.getTag());
            updateAppender.append(colNameSql);
            upateParam.putAll(updateColValueCondition.getParams());
            while ((condition = conditions.pollFirst()) != null){
                if(condition instanceof UpdateColValueCondition){
                    updateColValueCondition = (UpdateColValueCondition) condition;
                    colNameSql = updateColValueCondition.getSql();
                    updateAppender.append(",");
                    updateAppender.append(colNameSql);
                    upateParam.putAll(updateColValueCondition.getParams());
                }else if(condition instanceof WhereConditionMark){
                    conditions.addFirst(condition);
                    parseWhereCondition(conditions, upateParam, updateAppender);
                }else if(condition instanceof AddPatchMarkCondition){
                    conditions.addFirst(condition);
                    break;
                }
            }
            return SQLInfo.builder().sql(updateAppender.toString())
                    .params(Arrays.asList(upateParam))
                    .aClass(modelClass)
                    .build();
        }else if(condition instanceof UpdateAllColValueCondition){
            UpdateAllColValueCondition ua = (UpdateAllColValueCondition) condition;
            String sql = ua.getSql();

        }
        return null;
    }

    private static SQLInfo parseInsert(LinkedList<Condition> conditions) {
        InsertIntoCondition insertIntoTableCondition = (InsertIntoCondition)conditions.pollFirst();

        String insertIntoTableSql = insertIntoTableCondition.getSql();
        StringBuilder insertColNamesAppender = new StringBuilder();
        StringBuilder insertValueAppender = new StringBuilder();
        LinkedList<Map<String, Object>> paramsList = new LinkedList<>();
        HashMap<String, Object> valuesParam = new HashMap<>();
        boolean isColValueCondition = false;
        for(Condition condition : conditions){
            if(condition instanceof ColValueCondition){
                Map<String, Object> params = condition.getParams();//只有一个值
                Assert.isTrue(params.size() == 1,"必须存在一个值");
                if(insertColNamesAppender.length() == 0){
                    isColValueCondition = true;
                    insertColNamesAppender.append(insertIntoTableSql);
                    insertColNamesAppender.append(" ( ");
                    insertValueAppender.append(ConditionTag.VALUES.getTag());
                    insertValueAppender.append("( ");

                }else{
                    insertColNamesAppender.append(",");
                    insertValueAppender.append(",");
                }
                insertColNamesAppender.append(condition.getSql());
                Set<String> strings = params.keySet();
                String key = strings.iterator().next();
                insertValueAppender.append("#{");
                insertValueAppender.append(key);
                insertValueAppender.append("}");
                valuesParam.put(key,params.get(key));

            } else if(condition instanceof InsertAllColValueCondition){

                InsertAllColValueCondition allColValueCondition = (InsertAllColValueCondition) condition;
                if(insertColNamesAppender.length() == 0){
                    insertColNamesAppender.append(insertIntoTableSql);
                    insertColNamesAppender.append(allColValueCondition.getSql());
                    insertValueAppender.append(ConditionTag.VALUES.getTag());
                    insertValueAppender.append(allColValueCondition.getValuesSql());
                }
                paramsList.add(allColValueCondition.getParams());
            }
        }

        if(isColValueCondition){
            insertColNamesAppender.append(" )");
            insertValueAppender.append(" ) ");
            paramsList.add(valuesParam);
        }

        SQLInfo sqlInfo = SQLInfo.builder().aClass(insertIntoTableCondition.getModelClass())
                .params(paramsList)
                .sql(insertColNamesAppender.append(insertValueAppender.toString()).toString())
                .build();
        return sqlInfo;
    }

    private static SQLInfo parseSelect(LinkedList<Condition> conditions) {
        HashMap<String, Object> params = new HashMap<>();
        String and = ConditionTag.AND.getTag();

        //获取表
        Condition condition = conditions.pollFirst();
        String fromTable = condition.getSql();

        //生成select语句
        StringBuilder selectB = new StringBuilder(ConditionTag.SELECT.getTag());
        if(conditions.peekFirst() == null || !(conditions.peekFirst() instanceof SelectColumnCondition)){
            //没有要查询的字段
            return null;
        }
        parseSelectClo(true, conditions, selectB);

        //是否存在left join
        if (conditions.peekFirst() instanceof JoinTableCondition) {
            Condition leftJoin = conditions.pollFirst();
            parseSelectClo(conditions, selectB);
            selectB.append(fromTable);// a left join b
            selectB.append(" ");
            selectB.append(leftJoin.getSql());
            //指定on条件
            Condition on = null;
            if (conditions.peekFirst() instanceof OnConditionMark) {//.on()
                selectB.append(conditions.pollFirst().getSql());
                selectB.append("1=1");
                for (; !(conditions.peekFirst() instanceof WhereConditionMark); ) {//如果没有 .where()
                    on = conditions.pollFirst();
                    if (on instanceof OrCondition) {
                        and = on.getSql();
                        continue;
                    }
                    whereAndOn(params, selectB, and, on);
                    and = ConditionTag.AND.getTag();
                }
            }
        } else {
            selectB.append(fromTable);
        }

        // where
        parseWhereCondition(conditions, params, selectB);

        condition.clean();
        return SQLInfo.builder().aClass(((SelectTableCondition) condition).getmClass())
                .params(Arrays.asList(params))
                .sql(selectB.toString().replaceAll(" +", " "))
                .build();
    }

    //解析Where 语句
    private static void parseWhereCondition(LinkedList<Condition> conditions, HashMap<String, Object> params,  StringBuilder sql) {
        String and = ConditionTag.AND.getTag();
        if (conditions.peekFirst() instanceof WhereConditionMark) {
            sql.append(conditions.pollFirst().getSql());
            sql.append(" 1=1 ");
            Condition where = null;
            for (; (where = conditions.pollFirst()) != null; ) {
                if (where instanceof OrCondition) {
                    and = where.getSql();
                    continue;
                } else {
                    whereAndOn(params, sql, and, where);
                    and = ConditionTag.AND.getTag();
                }
            }

        }
    }

    //拼接and
    private static void whereAndOn(HashMap<String, Object> params, StringBuilder selectB, String andOr, Condition where) {
        if (where instanceof IgnoreAndOrWordCondition) {
            andOr = "";
        }
        andOr = where.andOrWord(andOr);
        selectB.append(andOr);
        selectB.append(where.getSql());
        params.putAll(where.getParams());
    }

    private static void parseSelectClo(LinkedList<Condition> conditions, StringBuilder selectB) {
        Condition selectCol;
        for (; conditions.peekFirst() instanceof SelectColumnCondition; ) {
            selectCol = conditions.pollFirst();
            selectB.append(selectCol.getSql());
        }
    }

    private static void parseSelectClo(boolean status, LinkedList<Condition> conditions, StringBuilder selectB) {
        Condition selectCol;
        for (; conditions.peekFirst() instanceof SelectColumnCondition; ) {
            selectCol = conditions.pollFirst();
            String sql = selectCol.getSql();
            if(status && sql.contains(",")){
                sql = sql.substring(sql.indexOf(",")+1);
                status = false;
            }
            selectB.append(sql);
        }
    }
}
