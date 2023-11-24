package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.core.parse.DeleteConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.InsertConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.ParseCondition;
import com.javaoffers.brief.modelhelper.core.parse.SelectConditionParse;
import com.javaoffers.brief.modelhelper.core.parse.UpdateConditionParse;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.EnumMap;
import java.util.LinkedList;

/**
 * @Description: 用于解析Condition
 * @Auther: create by cmj on 2022/5/22 13:47
 */
public class StatementParserAdepter {

    static final EnumMap<ConditionTag, ParseCondition> crudConditionParse = new EnumMap<ConditionTag, ParseCondition>(ConditionTag.class);
    static final EnumMap<DBType, StatementParser> dbTypeStatementParser = new EnumMap<DBType, StatementParser>(DBType.class);

    static {
        crudConditionParse.put(SelectConditionParse.conditionTag, new SelectConditionParse());
        crudConditionParse.put(DeleteConditionParse.conditionTag, new DeleteConditionParse());
        crudConditionParse.put(InsertConditionParse.conditionTag, new InsertConditionParse());
        crudConditionParse.put(UpdateConditionParse.conditionTag, new UpdateConditionParse());

        dbTypeStatementParser.putAll();
    }

    public static SQLStatement statementParse(LinkedList<Condition> conditions) {
        HeadCondition headCondition = (HeadCondition)conditions.pollFirst();
        DBType dbType = TableHelper.getTableInfo(headCondition.getModelClass()).getDbType();

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
