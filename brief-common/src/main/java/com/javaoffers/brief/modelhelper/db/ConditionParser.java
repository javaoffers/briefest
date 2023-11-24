package com.javaoffers.brief.modelhelper.db;

import com.javaoffers.brief.modelhelper.fun.Condition;

/**
 * Condition解析器,针对于一下特殊处理的Condition进行处理.
 * 比如{@code LimitWordCondition} 不同的数据库语法不同,因此需要特殊解析进行处理.
 * @author mingJie
 */
public interface ConditionParser<T extends Condition> {

    /**
     * 处理具体的condition
     * @param condition
     */
    void process(T condition);
}
