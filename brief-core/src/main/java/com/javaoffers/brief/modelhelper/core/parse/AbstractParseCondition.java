package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.core.SmartSQLInfo;
import com.javaoffers.brief.modelhelper.core.CrudSQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;


public abstract class AbstractParseCondition implements ParseCondition{

    public SmartSQLInfo parse(LinkedConditions<Condition> conditions){
        SmartSQLInfo moreSQLInfo = new SmartSQLInfo();
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

    abstract CrudSQLStatement doParse(LinkedList<Condition> conditions);
}

