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
        ShardingParams<Object> shardingParams =
                new ShardingParams<Object>(condition, orgTableName, condition.getColName());
        shardingTableStrategy.shardingBefore(shardingParams);
        switch (conditionTag) {
            case EQ:
                String shardingTable = shardingTableStrategy.shardingExactly(shardingParams);
                if(StringUtils.isBlank(shardingTable) || shardingTable.equals(orgTableName)){
                    break;
                }
                result.shardingCondition.shardingTableName(shardingTable + " " + orgTableName);
                break;
            default:
                ArrayList<String> shardingTableList = new ArrayList<String>(shardingTableStrategy.shardingRange(shardingParams));
                if(CollectionUtils.isEmpty(shardingTableList)){
                    break;
                }
                //org sharding
                Collection<ConditionContext> newPeerShardingList = shardingCondition(shardingTableList,
                        result.shardingConditionIdx, orgConditionContext, shardingParams.getTableName());

                //peer sharding
                List<ConditionContext> peerConditionContexts = (List<ConditionContext>) orgConditionContext.getPeerConditionContexts();
                List<ConditionContext> allNewPeerConditionContexts = Lists.newArrayList();
                for (ConditionContext peerConditionContext : peerConditionContexts) {
                    Collection<ConditionContext> newPeerShardingList2 = shardingCondition(shardingTableList,
                            result.shardingConditionIdx, peerConditionContext, shardingParams.getTableName());
                    allNewPeerConditionContexts.addAll(newPeerShardingList2);
                }

                peerConditionContexts.addAll(newPeerShardingList);
                peerConditionContexts.addAll(allNewPeerConditionContexts);
                if(CollectionUtils.isNotEmpty(orgConditionContext.getPeerConditionContexts())){
                    result.headCondition.setSharding(true);
                }
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
        Assert.isTrue(shardingTable != null, "sharding table name is null");
        Result result = getResult(orgConditionContext, orgTableName);
        result.shardingCondition.shardingTableName(shardingTable);
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

    private Collection<ConditionContext> shardingCondition(List<String> shardingTableList,
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
