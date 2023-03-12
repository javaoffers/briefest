package com.javaoffers.batis.modelhelper.core.parse;

import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.batis.modelhelper.utils.InterceptorLoader;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;


public abstract class AbstractParseCondition implements ParseCondition{

    public SQLInfo parse(LinkedList<Condition> conditions){
        SQLInfo sqlInfo = doParse(conditions);
        return sqlInfo;
    }

    abstract SQLInfo doParse(LinkedList<Condition> conditions);
}

