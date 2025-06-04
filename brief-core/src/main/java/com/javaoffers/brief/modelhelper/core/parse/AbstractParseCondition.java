package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.core.MoreSQLInfo;
import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;


public abstract class AbstractParseCondition implements ParseCondition{

    public MoreSQLInfo parse(LinkedConditions<Condition> conditions){
        MoreSQLInfo moreSQLInfo = new MoreSQLInfo();
        moreSQLInfo.addSqlInfo(doParse(conditions));
        List<? extends ConditionContext> peerConditionContexts = conditions.getPeerConditionContexts();
        if(CollectionUtils.isNotEmpty(peerConditionContexts)){
            for(ConditionContext peerConditionContext : peerConditionContexts){
                LinkedConditions<Condition> peerConditions = (LinkedConditions<Condition>) peerConditionContext;
                peerConditions.pollFirst();//主要弹出headCondition
                moreSQLInfo.addSqlInfo(doParse(peerConditions));
            }
        }
        return moreSQLInfo;
    }

    abstract SQLStatement doParse(LinkedList<Condition> conditions);
}

