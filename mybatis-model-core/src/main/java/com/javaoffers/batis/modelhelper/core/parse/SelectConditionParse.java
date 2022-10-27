package com.javaoffers.batis.modelhelper.core.parse;

import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.condition.JoinTableCondition;
import com.javaoffers.batis.modelhelper.fun.condition.mark.OnConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.mark.WhereConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.select.SelectColumnCondition;
import com.javaoffers.batis.modelhelper.fun.condition.select.SelectTableCondition;
import com.javaoffers.batis.modelhelper.fun.condition.where.OrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.where.WhereOnCondition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @description:
 * @author: create by cmj on 2022/10/23 10:44
 */
public class SelectConditionParse implements ParseCondition {
    public static ConditionTag conditionTag  = ConditionTag.SELECT_FROM;
    @Override
    public SQLInfo parse(LinkedList<Condition> conditions) {
        return parseSelect(conditions);
    }

    private  SQLInfo parseSelect(LinkedList<Condition> conditions) {
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
                        on = conditions.pollFirst();
                    }else if(on instanceof WhereOnCondition){
                        and = ((WhereOnCondition) on).getAndOrTag();
                    }
                    whereAndOn(params, selectB, and, on);

                }
            }
        } else {
            selectB.append(fromTable);
        }

        // where
        parseWhereCondition(conditions, params, selectB);

        return SQLInfo.builder().aClass(((SelectTableCondition) condition).getmClass())
                .params(Arrays.asList(params))
                .sql(selectB.toString())
                .build();
    }


    private  void parseSelectClo(LinkedList<Condition> conditions, StringBuilder selectB) {
        SelectColumnCondition selectCol;
        for (; conditions.peekFirst() instanceof SelectColumnCondition; ) {
            selectCol = (SelectColumnCondition) conditions.pollFirst();
            selectB.append(selectCol.getDelimiter());
            selectB.append(selectCol.getSql());
        }
    }

    private  void parseSelectClo(boolean status, LinkedList<Condition> conditions, StringBuilder selectB) {
        SelectColumnCondition selectCol;
        for (; conditions.peekFirst() instanceof SelectColumnCondition; ) {
            selectCol = (SelectColumnCondition) conditions.pollFirst();
            String sql = selectCol.getSql();
            if(status){
                selectB.append(sql);
                status = false;
            }else {
                selectB.append(selectCol.getDelimiter());
                selectB.append(sql);
            }


        }
    }

}