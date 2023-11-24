package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.SQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;

import java.util.LinkedList;


public abstract class AbstractParseCondition implements ParseCondition{

    public SQLStatement parse(LinkedList<Condition> conditions){
        SQLStatement sqlStatement = doParse(conditions);
        return sqlStatement;
    }

    abstract SQLStatement doParse(LinkedList<Condition> conditions);
}

