package com.javaoffers.brief.modelhelper.parser;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.utils.DBTypeLabel;

/**
 * Condition解析器,针对于一下特殊处理的Condition进行处理.
 * 比如{@code LimitWordCondition} 不同的数据库语法不同,因此需要特殊解析进行处理.
 * @author mingJie
 */
public interface MarkConditionParser<T extends Condition> extends DBTypeLabel {

    /**
     * 处理具体的condition
     * @param condition
     */
    void process(T condition);
}
