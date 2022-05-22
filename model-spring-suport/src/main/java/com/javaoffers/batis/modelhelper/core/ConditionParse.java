package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.condition.LeftJoinTableCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OnCondition;
import com.javaoffers.batis.modelhelper.fun.condition.OnConditionMark;
import com.javaoffers.batis.modelhelper.fun.condition.OrCondition;
import com.javaoffers.batis.modelhelper.fun.condition.SelectColumnCondition;
import com.javaoffers.batis.modelhelper.fun.condition.SelectTableCondition;
import com.javaoffers.batis.modelhelper.fun.condition.WhereConditionMark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * @Description: 用于解析Condition
 * @Auther: create by cmj on 2022/5/22 13:47
 */
public class ConditionParse {

    public static SQLInfo conditionParse(LinkedList<Condition> conditions){

        //判断类型
        Condition condition = conditions.get(0);
        ConditionTag conditionTag = condition.getConditionTag();
        switch (conditionTag){
            case SELECT_FROM:
                //解析select
                return parseSelect(conditions);

        }
        return null;
    }

    private static SQLInfo parseSelect(LinkedList<Condition> conditions) {
        HashMap<String, Object> params = new HashMap<>();
        String and = ConditionTag.AND.getTag();

        //获取表
        Condition condition = conditions.pollFirst();
        String fromTable =  condition.getSql();

        //生成select语句
        StringBuilder selectB = new StringBuilder(ConditionTag.SELECT.getTag()+" 1");
        parseSelectClo(conditions, selectB);

        //是否存在left join
        if(conditions.peekFirst() instanceof LeftJoinTableCondition){
            Condition leftJoin = conditions.pollFirst();
            parseSelectClo(conditions, selectB);
            selectB.append(fromTable +" "+ leftJoin.getSql());// a left join b
            //指定on条件
            Condition on = null;
            if(conditions.peekFirst() instanceof OnConditionMark){//.on()
                selectB.append(conditions.pollFirst().getSql());
                selectB.append("1=1");
                for(;!(conditions.peekFirst() instanceof WhereConditionMark);){//如果没有 .where()
                    on = conditions.pollFirst();
                    if(on instanceof OrCondition){
                        and = on.getSql();
                        continue;
                    }
                    whereAndOn(params, selectB, and, on);
                    and = ConditionTag.AND.getTag();
                }
            }
        }else {
            selectB.append(fromTable);
        }

        // where
        if(conditions.peekFirst() instanceof WhereConditionMark){
            selectB.append(conditions.pollFirst().getSql());
            selectB.append(" 1=1 ");
            Condition where = null;
            for(;(where = conditions.pollFirst()) != null;){
                if(where instanceof OrCondition){
                    and = where.getSql();
                    continue;
                }else{
                    whereAndOn(params, selectB, and, where);
                    and = ConditionTag.AND.getTag();
                }
            }
        }
        return SQLInfo.builder().aClass(((SelectTableCondition)condition).getmClass())
                .params(Arrays.asList(params))
                .sql(selectB.toString().replaceAll("  "," "))
                .build();
    }

    //拼接and
    private static void whereAndOn( HashMap<String, Object> params, StringBuilder selectB, String andOr, Condition where) {
        selectB.append(andOr);
        selectB.append(where.getSql());
        params.putAll(where.getParams());
    }

    private static void parseSelectClo(LinkedList<Condition> conditions, StringBuilder selectB) {
        Condition selectCol;
        for(; conditions.peekFirst() instanceof SelectColumnCondition;){
            selectCol = conditions.pollFirst();
            selectB.append(selectCol.getSql());
        }
    }
}
