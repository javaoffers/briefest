package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.fun.condition.DeleteFromCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertIntoCondition;
import com.javaoffers.brief.modelhelper.fun.condition.select.SelectColumnCondition;
import com.javaoffers.brief.modelhelper.fun.condition.select.SelectTableCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateSetCondition;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 按照月进行sharding.
 *
 * @author cao ming jie create by 2025/5/2
 */
public final class MonthShardingTableProcessor implements ShardingProcessor{

    @Override
    public void processEq(ConditionContext conditionContext, Condition condition) {
        List<? extends Condition> conditions = conditionContext.getConditions();
        ListIterator<? extends Condition> iterator = conditions.listIterator();
        for(;iterator.hasPrevious();){
            Condition previous = iterator.previous();
            if(previous instanceof SelectTableCondition){

            } else if (previous instanceof InsertIntoCondition){
                
            } else if (previous instanceof UpdateSetCondition) {
                
            } else if (previous instanceof UpdateAllColValueCondition) {

            } else if (previous instanceof DeleteFromCondition) {}
        }
    }
}
