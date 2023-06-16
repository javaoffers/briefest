package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LFCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereOnCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateCondition;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * create by cmj on 2022-06-20 2:50:07
 */
public class LinkedConditions<T extends Condition> extends LinkedList<T> {

    private List<BiConsumer<T, T>> beforeAddProcess = new LinkedList<>();

    {
        this.beforeAdd((before, current) -> {
            if (before == null) {
                return;
            } else {
                if (before instanceof OrderWordCondition && current instanceof OrderWordCondition) {
                    ((OrderWordCondition) current).asChild();
                } else if (before instanceof LFCondition) {
                    if (current instanceof WhereOnCondition) {
                        ((WhereOnCondition) current).cleanAndOrTag();
                    }
                }
            }
        });
    }

    public boolean add(T condition) {
        beforeAddProcess.forEach(biConsumer -> {
            biConsumer.accept(this.peekLast(), condition);
        });
        if (condition instanceof WhereOnCondition) {
            ((WhereOnCondition) condition).setHeadCondition((HeadCondition) this.peekFirst());
        } else if (condition instanceof UpdateCondition) {
            ((UpdateCondition) condition).setHeadCondition((HeadCondition) this.peekFirst());
        } else if(condition instanceof HeadCondition && this.peekFirst() instanceof HeadCondition){
            this.pollFirst();
            this.addFirst(condition);
            return true;
        }
        return super.add(condition);
    }

    public boolean addAll(Collection<? extends T> c) {
        c.forEach(a -> {
            add(a);
        });
        return true;
    }

    private void beforeAdd(BiConsumer<T, T> biConsumer) {
        beforeAddProcess.add(biConsumer);
    }


}
