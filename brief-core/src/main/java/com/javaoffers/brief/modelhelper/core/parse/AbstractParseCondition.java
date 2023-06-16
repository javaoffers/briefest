package com.javaoffers.brief.modelhelper.core.parse;

import com.javaoffers.brief.modelhelper.core.SQLInfo;
import com.javaoffers.brief.modelhelper.fun.Condition;

import java.util.LinkedList;


public abstract class AbstractParseCondition implements ParseCondition{

    public SQLInfo parse(LinkedList<Condition> conditions){
        SQLInfo sqlInfo = doParse(conditions);
        return sqlInfo;
    }

    abstract SQLInfo doParse(LinkedList<Condition> conditions);
}

