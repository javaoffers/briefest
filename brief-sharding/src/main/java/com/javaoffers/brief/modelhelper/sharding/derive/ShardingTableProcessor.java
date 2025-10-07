package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.fun.*;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
        if(conditionTag.getCategoryTag() != CategoryTag.WHERE_ON){
            return;
        }
        String orgTableName =strategyContext.getOrgTableName();
        Result result = getResult(orgConditionContext, orgTableName);
        ShardingParams<Object> shardingParams = new ShardingParams<Object>(condition, orgTableName, condition.getColName());
        shardingTableStrategy.shardingBefore(shardingParams);
        switch (conditionTag) {
            case EQ:
                String shardingTable = shardingTableStrategy.shardingExactly(shardingParams);
                if(StringUtils.isBlank(shardingTable) || shardingTable.equals(orgTableName)){
                    break;
                }
                result.shardingCondition.shardingTableName(shardingTable + " " + orgTableName);
                shardingTableStrategy.shardingAfter(Lists.newArrayList(shardingTable));
                result.headCondition.setSharding(true);
                break;
            default:
                Set<String> shardingTables = shardingTableStrategy.shardingRange(shardingParams);
                if(CollectionUtils.isEmpty(shardingTables)){
                    break;
                }
                ArrayList<String> shardingTableList = new ArrayList<String>(shardingTables);
                shardingTableStrategy.shardingAfter(shardingTableList);
                //org sharding with make peer sharding
                Collection<ConditionContext> newPeerShardingList = shardingConditionForWhere(shardingTableList,
                        result.shardingConditionIdx, orgConditionContext, shardingParams.getTableName());

                //peer sharding
                List<ConditionContext> peerConditionContexts = (List<ConditionContext>) orgConditionContext.getPeerConditionContexts();
                peerConditionContexts.addAll(newPeerShardingList);
                result.headCondition.setSharding(true);
                break;
        }
    }


    @Override
    public void processInsert(ShardingStrategyContext shardingStrategyContext) {
        ConditionContext orgConditionContext = shardingStrategyContext.getConditionContext();
        ShardingTableStrategy shardingTableStrategy = shardingStrategyContext.getShardingTableStrategy();
        Condition condition = shardingStrategyContext.getCondition();

        String orgTableName = shardingStrategyContext.getOrgTableName();
        String colName = shardingStrategyContext.getColName();
        ShardingParams<Object> objectShardingParams =
                new ShardingParams<Object>(condition, orgTableName, colName);
        shardingTableStrategy.shardingBefore(objectShardingParams);
        String shardingTable = shardingTableStrategy.shardingExactly(objectShardingParams);
        shardingTableStrategy.shardingAfter(Lists.newArrayList(shardingTable));
        Assert.isTrue(shardingTable != null, "sharding table name is null");
        Result result = getResult(orgConditionContext, orgTableName);
        result.shardingCondition.shardingTableName(shardingTable);
        result.headCondition.setSharding(true);
    }

    private static Result getResult(ConditionContext orgConditionContext, String orgTableName) {
        List<? extends Condition> conditions = orgConditionContext.getConditions();
        ListIterator<? extends Condition> iterator = conditions.listIterator(conditions.size());
        ShardingCondition shardingCondition = null;
        HeadCondition headCondition = (HeadCondition)conditions.get(0);
        int shardingConditionIdx = conditions.size();
        for (; iterator.hasPrevious(); ) {
            if(shardingCondition == null){
                shardingConditionIdx--;
            }
            Condition previous = iterator.previous();
            if (previous instanceof ShardingCondition && !((ShardingCondition) previous).isDone()) {
                ShardingCondition shardingConditionTmp = (ShardingCondition) previous;
                if(shardingConditionTmp.getTableName().equalsIgnoreCase(orgTableName)){
                    shardingCondition = shardingConditionTmp;
                    break;
                }
            }
        }
        Assert.isTrue(shardingCondition != null, "sharding table name "+orgTableName+" is error, please check if there are duplicate shards");
        Result result = new Result(shardingCondition, shardingConditionIdx, headCondition);
        return result;
    }

    private static Result getResultForInsertAll(ConditionContext orgConditionContext,String orgTableName, Condition conditionInsertAll) {
        List<? extends Condition> conditions = orgConditionContext.getConditions();
        ListIterator<? extends Condition> iterator = conditions.listIterator(conditions.size());
        ShardingCondition shardingCondition = null;
        HeadCondition headCondition = (HeadCondition)conditions.get(0);
        int shardingConditionIdx = conditions.size();
        for (; iterator.hasPrevious(); ) {
            if(shardingCondition == null){
                shardingConditionIdx--;
            }
            Condition previous = iterator.previous();
            if (previous instanceof ShardingCondition) {
                shardingCondition = (ShardingCondition) previous;
                break;
            }
        }
        // Judge whether it has been sharded, and if it has been sharded,
        // it means that it is being processed in batches. What comes in is a collection
        if(shardingCondition.isDone()){
            LinkedConditions conditionContextPeer = new LinkedConditions(false);
            for (int idx = 0; idx < conditions.size(); idx++) {
                Condition condition = conditions.get(idx);
                if(idx == shardingConditionIdx){
                    shardingCondition = (ShardingCondition) condition;
                    condition = shardingCondition.clone(orgTableName);
                    shardingCondition = (ShardingCondition) condition;
                }
                //skip
                if(condition instanceof InsertAllColValueCondition){
                    continue;
                }
                conditionContextPeer.directFillingCondition(condition);
            }
            conditionContextPeer.directFillingCondition(conditionInsertAll);
            List  peerConditionContexts = orgConditionContext.getPeerConditionContexts();
            peerConditionContexts.add(conditionContextPeer);
        }else{
            orgConditionContext.directFillingCondition(conditionInsertAll);
        }

        Result result = new Result(shardingCondition, shardingConditionIdx, headCondition);
        return result;
    }
    private Collection<ConditionContext> shardingConditionForWhere(List<String> shardingTableList,
                                                           int shardingConditionIdx,
                                                           ConditionContext orgConditionContext,
                                                           String orgTableName) {
        //first Sharding for org
        String shardingTableOne = shardingTableList.get(0);
        List<? extends Condition> conditionList = orgConditionContext.getConditions();
        ShardingCondition shardingCondition = (ShardingCondition) conditionList.get(shardingConditionIdx);
        shardingCondition.shardingTableName(shardingTableOne + " " + orgTableName);

        //表示自身，而不用进行派生condition
        if (shardingTableList.size() == 1) {
            return Collections.emptyList();
        }

        // new peer Sharding
        HashMap<String, ConditionContext> newPeerShardingMap = new HashMap<>();
        //form idx as 1, because idx 0 is first sharding.
        for (int idx = 1; idx < shardingTableList.size(); idx++) {
            String shardingTableOther = shardingTableList.get(idx) + " " + orgTableName;
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

    public void processInsertALL(ShardingStrategyContext shardingStrategyContext, Condition insertAllColValueCondition) {
        ConditionContext orgConditionContext = shardingStrategyContext.getConditionContext();
        ShardingTableStrategy shardingTableStrategy = shardingStrategyContext.getShardingTableStrategy();
        Condition condition = shardingStrategyContext.getCondition();

        String orgTableName = shardingStrategyContext.getOrgTableName();
        String colName = shardingStrategyContext.getColName();
        ShardingParams<Object> objectShardingParams =
                new ShardingParams<Object>(condition, orgTableName, colName);
        shardingTableStrategy.shardingBefore(objectShardingParams);
        String shardingTable = shardingTableStrategy.shardingExactly(objectShardingParams);
        shardingTableStrategy.shardingAfter(Lists.newArrayList(shardingTable));
        Assert.isTrue(shardingTable != null, "sharding table name is null");
        Result result = getResultForInsertAll(orgConditionContext, orgTableName, insertAllColValueCondition);
        result.shardingCondition.shardingTableName(shardingTable);
        result.headCondition.setSharding(true);
    }

    private static class Result {
        public final ShardingCondition shardingCondition;
        public final int shardingConditionIdx;
        public final HeadCondition headCondition;
        public Result(ShardingCondition shardingCondition, int shardingConditionIdx,HeadCondition headCondition) {
            this.shardingCondition = shardingCondition;
            this.shardingConditionIdx = shardingConditionIdx;
            this.headCondition = headCondition;
        }
    }
}
