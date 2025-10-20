package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.core.CrudSQLStatement;
import com.javaoffers.brief.modelhelper.core.SmartSQLInfo;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.HeadEnum;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfo;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfoPosition;
import com.javaoffers.brief.modelhelper.utils.ModelInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Consumer;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/9/29
 */
public class ShardingQueryParse {

    /**
     * 进入此方法，就表示开始执行分片查询解析.
     * @param chain chain
     * @param moreSQLInfo sql info
     * @return result
     */
    public static Object shardingQuery(JqlExecutorChain chain, SmartSQLInfo moreSQLInfo){
        JqlMetaInfo jqlMetaInfo = chain.getJqlMetaInfo();
        switch (jqlMetaInfo.getOperate()){
            case QUERY:
                return doQuery(chain, moreSQLInfo);
            case STREAM:
                return doStream(chain, moreSQLInfo);
            default:
                throw new UnsupportedOperationException("Sharding operations are not supported "+ jqlMetaInfo.getOperate());

        }
    }

    private static Object doStream(JqlExecutorChain chain, SmartSQLInfo moreSQLInfo) {
        JqlMetaInfo jqlMetaInfo = chain.getJqlMetaInfo();
        Consumer consumer = jqlMetaInfo.getConsumer();
        jqlMetaInfo.setConsumer(null);
        List list = doQuery(chain, moreSQLInfo);
        list.forEach(consumer);
        return list.size();
    }

    private static List doQuery(JqlExecutorChain chain, SmartSQLInfo moreSQLInfo) {
        HeadCondition headCondition = moreSQLInfo.getHeadCondition();
        Map<HeadEnum, Object> conditionMap = headCondition.getConditionMap();
        LimitWordCondition limitWordCondition = (LimitWordCondition)conditionMap.get(HeadEnum.LIMIT);
        List<OrderWordCondition> orderWordConditionList = (List<OrderWordCondition>)conditionMap.get(HeadEnum.ORDERS);
        //TODO parse order
        List<ShardingOrder> shardingOrderList = parseShardingOrder(orderWordConditionList);
        List<CrudSQLStatement> sqlStatements = moreSQLInfo.getSqlStatements();
        JqlMetaInfo jqlMetaInfo = chain.getJqlMetaInfo();
        TableInfo tableInfo = chain.getTableInfo();
        ModelInfo<?> modelInfo = TableHelper.getModelInfo(tableInfo.getModelClass());
        List<ModelFieldInfoPosition> uniqueCol = modelInfo.getUniqueCol(new ArrayList<>(tableInfo.getPrimaryColNames().keySet()));
        //这里先按照主键排序，后续再支持order by
        PriorityQueue<Object> list = new PriorityQueue<>((a,b)->{

            //排序.
            for (ShardingOrder shardingOrder : shardingOrderList) {
                int c = 0;
                if((c=shardingOrder.compareTo(a,b)) != 0){
                    return c;
                }
            }

            for (int i = 0; i < uniqueCol.size(); i++) {
                ModelFieldInfo modelFieldInfo = uniqueCol.get(i).getModelFieldInfo();
                Object primaryKeyA = modelFieldInfo.getGetter().getter(a);
                Object primaryKeyB = modelFieldInfo.getGetter().getter(b);
                if (primaryKeyA == null) {
                    return -1;
                }
                if(primaryKeyB == null){
                    return 1;
                }
                if(primaryKeyA instanceof Comparable){
                    int c = ((Comparable)primaryKeyA).compareTo(primaryKeyB);
                    if (c != 0){
                        return c;
                    }
                }
            }
            return 0;
        });

        //执行并合并
        for (CrudSQLStatement sqlStatement : sqlStatements) {
            SmartSQLInfo smartSQLInfo = new SmartSQLInfo();
            smartSQLInfo.addSqlInfo(sqlStatement);
            smartSQLInfo.setHeadCondition(headCondition);
            jqlMetaInfo.setSqlStatement(smartSQLInfo);
            merge(limitWordCondition, list, (List)chain.doChain());
        }

        //分页
        return limit(limitWordCondition, list);
    }

    private static List<ShardingOrder> parseShardingOrder(List<OrderWordCondition> orderWordConditionList) {
        List<ShardingOrder> shardingOrderList = new ArrayList<>();
        if(orderWordConditionList == null){
            return shardingOrderList;
        }
        for (OrderWordCondition orderWordCondition : orderWordConditionList) {
            List<GetterFun> getterFunList = orderWordCondition.getGetterFunList();
            for (GetterFun getterFun : getterFunList) {
                ShardingOrder shardingOrder = new ShardingOrder(orderWordCondition.asc(), getterFun);
                shardingOrderList.add(shardingOrder);
            }
        }
        return shardingOrderList;
    }

    //分页
    private static List limit(LimitWordCondition limitWordCondition, PriorityQueue<Object> list) {

        if(limitWordCondition == null){
            ArrayList<Object> result = new ArrayList<>(list.size());
            for (int i = 0; !list.isEmpty(); i++) {
                Object poll = list.poll();
                result.add(poll);
            }
            return result;
        }
        ArrayList<Object> result = new ArrayList<>();
        int startIndex = limitWordCondition.startIndex();
        if(list.size()>startIndex){
            for (int i = 0; !list.isEmpty(); i++) {
                Object poll = list.poll();
                if(i>=startIndex){
                    result.add(poll);
                }
            }
        }
        return result;
    }

    /**
     * merge result
     * @param list list
     * @param results res
     */
    private static void merge(LimitWordCondition limitWordCondition, PriorityQueue<Object> list, List results) {
        list.addAll(results);
        if(limitWordCondition == null){
            return;
        }
        results.clear();//help gc
        int totalSize = limitWordCondition.pageNum() * limitWordCondition.pageSize();
        if (list.size() > totalSize) {
            ArrayList<Object> tmpList = new ArrayList<>(totalSize);
            for(;totalSize>0;){
                Object poll = list.poll();
                tmpList.add(poll);
                totalSize--;
            }
            list.clear();
            list.addAll(tmpList);
        }
    }
}
