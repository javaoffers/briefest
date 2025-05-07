package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextAware;
import com.javaoffers.brief.modelhelper.context.ConditionInterceptor;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LFCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereOnCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateCondition;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * create by cmj on 2022-06-20 2:50:07
 */
public class LinkedConditions<T extends Condition> extends LinkedList<T> implements BriefContextAware, ConditionContext {
    private static volatile BriefContext briefContext;
    private static final List<BiConsumer<Condition, Condition>> beforeAddProcess = new LinkedList<>();
    //设置公共拦截器
    static {
        beforeAddProcess.add((before, current) -> {
            if (before != null)  {
                if (before instanceof OrderWordCondition && current instanceof OrderWordCondition) {
                    ((OrderWordCondition) current).asChild();
                } else if (before instanceof LFCondition) {
                    if (current instanceof WhereOnCondition) {
                        ((WhereOnCondition<?>) current).cleanAndOrTag();
                    }
                }
            }
        });
    }

    /**
     * 获取派生的condition.
     */
    private List<LinkedConditions> peerContexts = new ArrayList<>();

    public boolean add(T condition) {
        beforeAddProcess.forEach(biConsumer -> {
            biConsumer.accept(this.peekLast(), condition);
        });
        if (condition instanceof WhereOnCondition) {
            ((WhereOnCondition<?>) condition).setHeadCondition((HeadCondition) this.peekFirst());
        } else if (condition instanceof UpdateCondition) {
            ((UpdateCondition) condition).setHeadCondition((HeadCondition) this.peekFirst());
        } else if(condition instanceof HeadCondition && this.peekFirst() instanceof HeadCondition){
            //说明开始了一个新的批次.通常在addBatch时会重新添加一个新的head
            this.pollFirst();
            this.addFirst(condition);
            return true;
        }
        //处理condition拦截器
        List<ConditionInterceptor> conditionInterceptor = briefContext.getConditionInterceptor();
        if (CollectionUtils.isNotEmpty(conditionInterceptor)) {
            conditionInterceptor.forEach(biConsumer -> {
                biConsumer.process(this, condition);
            });
        }

        //处理派生的condition
        for (LinkedConditions conditionContext: peerContexts){
            conditionContext.add(condition);
        }

        return super.add(condition);
    }

    public boolean addAll(Collection<? extends T> c) {
        c.forEach(this::add);
        return true;
    }


    @Override
    public void setBriefContext(BriefContext briefContext) {
        LinkedConditions.briefContext = briefContext;
    }


    @Override
    public List<? extends ConditionContext> getPeerConditionContexts() {
        return this.peerContexts;
    }

    @Override
    public List<? extends Condition> getConditions() {
        return this;
    }

    @Override
    public void directFillingConditions(List<? extends Condition> conditions) {
        for (Condition condition : conditions) {
            super.add((T) condition);
        }
    }
}
