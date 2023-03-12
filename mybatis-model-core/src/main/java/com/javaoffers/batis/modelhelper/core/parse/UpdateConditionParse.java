package com.javaoffers.batis.modelhelper.core.parse;

import com.javaoffers.batis.modelhelper.core.MoreSQLInfo;
import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.condition.mark.WhereConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.where.AddPatchMarkCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateAllColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateCondtionMark;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

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
    public SQLInfo doParse(LinkedList<Condition> conditions) {
        return parseUpdate(conditions);
    }

     SQLInfo parseUpdate(LinkedList<Condition> conditions) {
        MoreSQLInfo moreSQLInfo = new MoreSQLInfo();
        SQLInfo sqlInfo = parseUpdate2(conditions);
        moreSQLInfo.addSqlInfo(sqlInfo);
        Condition condition = null;
        while((condition = conditions.peek()) != null){
            sqlInfo = parseUpdate2(conditions);
            moreSQLInfo.addSqlInfo(sqlInfo);
        }
        return moreSQLInfo;
    }

     SQLInfo parseUpdate2(LinkedList<Condition> conditions) {
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
            //没有要更新的字段跳到下一个UpdateCondtionMark
            while (condition != null && !(condition instanceof UpdateCondtionMark)){
                condition = conditions.pollFirst();
            }
            if(condition instanceof UpdateCondtionMark){
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
                    if(conditions.size()>0 && conditions.peekFirst() instanceof UpdateCondtionMark) {
                        break;
                    }
                }else if(condition instanceof AddPatchMarkCondition){
                    break;
                }
            }
            return SQLInfo.builder().sql(updateAppender.toString())
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
            return SQLInfo.builder().sql(updateAppender.toString())
                    .params(Arrays.asList(upateParam))
                    .aClass(modelClass)
                    .status(status)
                    .build();
        }
        Assert.isTrue(false,"sql 表达式出现错误");
        return null;
    }
}
