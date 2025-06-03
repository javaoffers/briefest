package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.ShardingCondition;
import com.javaoffers.brief.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.DeleteFromCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertIntoCondition;
import com.javaoffers.brief.modelhelper.fun.condition.select.SelectTableCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateSetCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereCondition;
import com.javaoffers.brief.modelhelper.sharding.context.ShardingStrategyContext;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * 按照月进行sharding.
 *
 * @author cao ming jie create by 2025/5/2
 */
public final class ShardingTableProcessor implements ShardingProcessor {

    @Override
    public void processWhere(ShardingStrategyContext strategyContext) {
        ConditionContext orgConditionContext = strategyContext.getConditionContext();
        WhereCondition condition = (WhereCondition) strategyContext.getCondition();
        ShardingTableStrategy shardingTableStrategy = strategyContext.getShardingTableStrategy();
        ConditionTag conditionTag = condition.getConditionTag();
        Result result = getResult(orgConditionContext);
        Assert.isTrue(!result.shardingCondition.isDone(), "Duplicate sharding of the same table is not allowed");
        String orgTableName = result.shardingCondition.getTableName();
        ShardingParams<Object> objectShardingParams =
                new ShardingParams<Object>(condition, orgTableName, condition.getColName());
        switch (conditionTag) {
            case EQ:
//            case INSERT_INTO:
                String shardingTable = shardingTableStrategy.shardingExactly(objectShardingParams);
                Assert.isTrue(shardingTable != null, "sharding table name is null");
                result.shardingCondition.shardingTableName(shardingTable);
                break;
            default:
                List<String> shardingTableList = shardingTableStrategy.shardingRange(objectShardingParams);
                Assert.isTrue(CollectionUtils.isNotEmpty(shardingTableList), "sharding table list is empty");
                //org sharding
                Collection<ConditionContext> newPeerShardingList = shardingCondition(shardingTableList,
                        result.shardingConditionIdx, orgConditionContext);

                //peer sharding
                List<ConditionContext> peerConditionContexts = (List<ConditionContext>) orgConditionContext.getPeerConditionContexts();
                List<ConditionContext> allNewPeerConditionContexts = Lists.newArrayList();
                for (ConditionContext peerConditionContext : peerConditionContexts){
                    Collection<ConditionContext> newPeerShardingList2 = shardingCondition(shardingTableList,
                            result.shardingConditionIdx, peerConditionContext);
                    allNewPeerConditionContexts.addAll(newPeerShardingList2);
                }

                peerConditionContexts.addAll(newPeerShardingList);
                peerConditionContexts.addAll(allNewPeerConditionContexts);
                break;
        }
    }

    private static Result getResult(ConditionContext orgConditionContext) {
        List<? extends Condition> conditions = orgConditionContext.getConditions();
        ListIterator<? extends Condition> iterator = conditions.listIterator();
        ShardingCondition shardingCondition = null;
        int shardingConditionIdx = conditions.size();
        for (; iterator.hasPrevious(); ) {
            shardingConditionIdx--;
            Condition previous = iterator.previous();
//            if(previous instanceof SelectTableCondition){
//                break;
//            } else if (previous instanceof InsertIntoCondition){
//                break;
//            } else if (previous instanceof UpdateSetCondition) {
//                break;
//            } else if (previous instanceof UpdateAllColValueCondition) {
//                break;
//            } else if (previous instanceof DeleteFromCondition) {
//                break;
//            }
            if (previous instanceof ShardingCondition) {
                shardingCondition = (ShardingCondition) previous;
                break;
            }
        }
        Result result = new Result(shardingCondition, shardingConditionIdx);
        return result;
    }

    @Override
    public void processInsert(ShardingStrategyContext shardingStrategyContext) {
        ConditionContext orgConditionContext = shardingStrategyContext.getConditionContext();
        ShardingTableStrategy shardingTableStrategy = shardingStrategyContext.getShardingTableStrategy();
        Condition condition = shardingStrategyContext.getCondition();

        String orgTableName = null;
        String colName = null;
        if(condition instanceof ColValueCondition){
            ColValueCondition colValueCondition = (ColValueCondition) condition;
            orgTableName = colValueCondition.getSqlColInfo().getTableName();
            colName = colValueCondition.getColName();
        }else if(condition instanceof InsertAllColValueCondition){

        }

        ShardingParams<Object> objectShardingParams =
                new ShardingParams<Object>(condition, orgTableName, colName);
        String shardingTable = shardingTableStrategy.shardingExactly(objectShardingParams);
        Assert.isTrue(shardingTable != null, "sharding table name is null");
        Result result = getResult(orgConditionContext);
        result.shardingCondition.shardingTableName(shardingTable);
    }

    private Collection<ConditionContext> shardingCondition(List<String> shardingTableList,
                                                           int shardingConditionIdx,
                                                           ConditionContext orgConditionContext) {
        //first Sharding for org
        String shardingTableOne = shardingTableList.get(0);
        List<? extends Condition> conditionList = orgConditionContext.getConditions();
        ShardingCondition shardingCondition = (ShardingCondition)conditionList.get(shardingConditionIdx);
        shardingCondition.shardingTableName(shardingTableOne);

        if(shardingTableList.size()==1){
            return Collections.emptyList();
        }

        // new peer Sharding
        HashMap<String, ConditionContext> newPeerShardingMap = new HashMap<>();
        for (int idx = 1; idx < shardingTableList.size(); idx++) {
            String shardingTableOther = shardingTableList.get(idx);
            ConditionContext conditionContextPeer = new LinkedConditions(false);
            newPeerShardingMap.put(shardingTableOther, conditionContextPeer);
        }
        Set<Map.Entry<String, ConditionContext>> entries = newPeerShardingMap.entrySet();
        for (int idx = 0; idx < conditionList.size(); idx++) {
            Condition orgCondition = conditionList.get(idx);
            for (Map.Entry<String, ConditionContext> newPeerSharding : entries) {
                if (idx == shardingConditionIdx) {
                    newPeerSharding.getValue().directFillingCondition(shardingCondition.clone(newPeerSharding.getKey()));
                } else {
                    newPeerSharding.getValue().directFillingCondition(orgCondition);
                }
            }
        }

        return newPeerShardingMap.values();
    }

    private static class Result {
        public final ShardingCondition shardingCondition;
        public final int shardingConditionIdx;

        public Result(ShardingCondition shardingCondition, int shardingConditionIdx) {
            this.shardingCondition = shardingCondition;
            this.shardingConditionIdx = shardingConditionIdx;
        }
    }
}
