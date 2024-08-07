package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.MoreSQLInfo;
import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.mark.WhereConditionMark;
import com.javaoffers.brief.modelhelper.fun.condition.where.AddPatchMarkCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateSetCondition;
import org.apache.commons.lang3.StringUtils;
import com.javaoffers.brief.modelhelper.utils.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 10:44
 */
public class UpdateConditionParse extends AbstractParseCondition {
    public static ConditionTag conditionTag  = ConditionTag.UPDATE;
    @Override
    public SQLStatement doParse(LinkedList<Condition> conditions) {
        return parseUpdate(conditions);
    }

     SQLStatement parseUpdate(LinkedList<Condition> conditions) {
        MoreSQLInfo moreSQLInfo = new MoreSQLInfo();
        SQLStatement sqlStatement = parseUpdate2(conditions);
        moreSQLInfo.addSqlInfo(sqlStatement);

        while(( conditions.peek()) != null){
            sqlStatement = parseUpdate2(conditions);
            moreSQLInfo.addSqlInfo(sqlStatement);
        }
        return moreSQLInfo;
    }

     SQLStatement parseUpdate2(LinkedList<Condition> conditions) {
        Condition condition = conditions.pollFirst();
        String updateTableSql = null;
        Class modelClass = null;
        if(condition instanceof UpdateSetCondition){
            UpdateSetCondition updateCondtionMark = (UpdateSetCondition) condition;
            updateTableSql = updateCondtionMark.getSql();
            modelClass = updateCondtionMark.getModelCalss();
        }
        Assert.isTrue(updateTableSql != null, "update sql 解析出新问题");
        condition = conditions.pollFirst();
        if(condition == null || (!(condition instanceof UpdateColValueCondition)
                && !(condition instanceof UpdateAllColValueCondition))){
            //没有要更新的字段跳到下一个UpdateCondtionMark
            while (condition != null && !(condition instanceof UpdateSetCondition)){
                condition = conditions.pollFirst();
            }
            if(condition instanceof UpdateSetCondition){
                conditions.addFirst(condition);
            }
            return null;
        }
        boolean status = false;
        if(condition instanceof UpdateColValueCondition){
            status = true;
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
                }
                else if(condition instanceof WhereConditionMark){
                    conditions.addFirst(condition);
                    parseWhereCondition(conditions, upateParam, updateAppender);
                    if(conditions.size()>0 && conditions.peekFirst() instanceof UpdateSetCondition) {
                        break;
                    }
                }else if(condition instanceof AddPatchMarkCondition){
                    break;
                }
            }
            return SQLStatement.builder().sql(updateAppender.toString())
                    .params(Arrays.asList(upateParam))
                    .aClass(modelClass)
                    .status(status)
                    .build();
        }else if(condition instanceof UpdateAllColValueCondition){
            UpdateAllColValueCondition ua = (UpdateAllColValueCondition) condition;
            String sql = ua.getSql();
            if(!StringUtils.isBlank(sql)){
                status = true;
            }
            StringBuilder updateAppender = new StringBuilder(sql);
            HashMap<String, Object> upateParam = new HashMap<>(ua.getParams());
            while ((condition = conditions.pollFirst()) != null){
                if(condition instanceof WhereConditionMark){
                    conditions.addFirst(condition);
                    parseWhereCondition(conditions, upateParam, updateAppender);
                }else if(condition instanceof AddPatchMarkCondition){
                    break;
                }
            }
            return SQLStatement.builder().sql(updateAppender.toString())
                    .params(Arrays.asList(upateParam))
                    .aClass(modelClass)
                    .status(status)
                    .build();
        }
        Assert.isTrue(false,"sql 表达式出现错误");
        return null;
    }
}
