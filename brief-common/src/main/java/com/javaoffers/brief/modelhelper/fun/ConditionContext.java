package com.javaoffers.brief.modelhelper.fun;

import java.util.List;

/**
 * condition context.
 *
 * @author cao ming jie create by 2025/5/2
 */
public interface ConditionContext {
    /**
     * 获取派生后的conditionContext.
     * 右一个ConditionContext 派生出多个不同的 condition context
     * @return 派生的condition context
     */
    List<? extends ConditionContext> getPeerConditionContexts();

    /**
     * 获取所有当前的condition
     * @return list
     */
    List<? extends Condition> getConditions();

}
