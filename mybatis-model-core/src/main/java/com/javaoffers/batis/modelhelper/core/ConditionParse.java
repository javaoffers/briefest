package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.core.parse.DeleteConditionParse;
import com.javaoffers.batis.modelhelper.core.parse.InsertConditionParse;
import com.javaoffers.batis.modelhelper.core.parse.ParseCondition;
import com.javaoffers.batis.modelhelper.core.parse.SelectConditionParse;
import com.javaoffers.batis.modelhelper.core.parse.UpdateConditionParse;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.EnumMap;
import java.util.LinkedList;

/**
 * @Description: 用于解析Condition
 * @Auther: create by cmj on 2022/5/22 13:47
 */
public class ConditionParse {

    static final EnumMap<ConditionTag, ParseCondition> crudConditionParse = new EnumMap<ConditionTag, ParseCondition>(ConditionTag.class);
    static {
        crudConditionParse.put(SelectConditionParse.conditionTag, new SelectConditionParse());
        crudConditionParse.put(DeleteConditionParse.conditionTag, new DeleteConditionParse());
        crudConditionParse.put(InsertConditionParse.conditionTag, new InsertConditionParse());
        crudConditionParse.put(UpdateConditionParse.conditionTag, new UpdateConditionParse());
    }

    public static SQLInfo conditionParse(LinkedList<Condition> conditions) {

        //判断类型
        Condition condition = conditions.get(0);

        ConditionTag conditionTag = condition.getConditionTag();
        return crudConditionParse.get(conditionTag).parse(conditions);
//        switch (conditionTag) {
//            case SELECT_FROM:
//                //解析select
//                return parseSelect(conditions);
//            case INSERT_INTO:
//                return parseInsert(conditions);
//            case UPDATE:
//                return parseUpdate(conditions);
//            case DELETE_FROM:
//                return parseDelete(conditions);
//        }
    }








}
