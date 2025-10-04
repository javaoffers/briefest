package com.javaoffers.brief.modelhelper.sharding.context;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcessor;
import com.javaoffers.brief.modelhelper.context.ConditionInterceptor;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.HeadEnum;
import com.javaoffers.brief.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.IgnoreAndOrWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereCondition;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingDeriveInfo;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingStrategyMark;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingTableProcessor;
import com.javaoffers.brief.modelhelper.utils.ModelFieldInfoPosition;
import com.javaoffers.brief.modelhelper.utils.ModelInfo;
import com.javaoffers.brief.modelhelper.utils.SqlColInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/1
 */
public class ConditionBriefContextPostProcessor implements BriefContextPostProcessor {

    @Override
    public void postProcess(BriefContext briefContext) {
        List<ConditionInterceptor> conditionInterceptor = briefContext.getConditionInterceptor();
        conditionInterceptor.add(new ShardingConditionInterceptorImpl());
    }

    static class ShardingConditionInterceptorImpl implements ConditionInterceptor {
        //分表处理器
        ShardingTableProcessor shardingTableProcessor = new ShardingTableProcessor();

        @Override
        public boolean process(ConditionContext conditionContext, Condition condition) {

            List<? extends Condition> conditions = conditionContext.getConditions();
            if(conditions.isEmpty()){
                return true;
            }

            //sharding order和limit
            if(condition instanceof OrderWordCondition){
                HeadCondition headCondition = (HeadCondition)conditionContext.getConditions().get(0);
                if(headCondition.isSharding()){
                    //添加order
                    List<OrderWordCondition> orderWordConditionList = (List<OrderWordCondition>)headCondition.getConditionMap().get(HeadEnum.ORDERS);
                    if(orderWordConditionList == null){
                        orderWordConditionList = new ArrayList<>();
                        headCondition.getConditionMap().put(HeadEnum.ORDERS, orderWordConditionList);
                    }
                    orderWordConditionList.add((OrderWordCondition)condition);
                }
            } else if(condition instanceof LimitWordCondition){
                HeadCondition headCondition = (HeadCondition)conditionContext.getConditions().get(0);
                if(headCondition.isSharding()){
                    //添加limit
                    LimitWordCondition limitWordCondition = (LimitWordCondition)condition;
                    headCondition.getConditionMap().put(HeadEnum.LIMIT, new LimitWordCondition(limitWordCondition.pageNum, limitWordCondition.pageSize));
                }
            }

            //处理limit条件
            HeadCondition headCondition = (HeadCondition)conditions.get(0);
            if(headCondition.isSharding() && condition instanceof LimitWordCondition
                    && !conditionContext.getPeerConditionContexts().isEmpty()) {
                LimitWordCondition limitWordCondition = (LimitWordCondition)condition;
                limitWordCondition.limit(1, limitWordCondition.pageNum * limitWordCondition.pageSize);
            }

            //派生的context不支持sharding
            if(!conditionContext.isOrgContext() || condition instanceof IgnoreAndOrWordCondition){
                return true;
            }

            //处理查询派生condition, 处理 select/delete/update for where
            if (condition instanceof WhereCondition ) {
                WhereCondition whereCondition = (WhereCondition) condition;
                Class modelClass = headCondition.getModelClass();
                TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
                DeriveInfo deriveColName = tableInfo.getDeriveColName(ShardingStrategyMark.SHARDING_TABLE_STRATEGY);
                if(deriveColName == null){
                    return true;
                }
                ShardingDeriveInfo shardingDeriveInfo = (ShardingDeriveInfo) deriveColName;
                String colName = shardingDeriveInfo.getColName();
                String colNameWithWhere = whereCondition.getColName();
                int c = colNameWithWhere.indexOf(".") + 1;
                if(!colName.equalsIgnoreCase(colNameWithWhere.substring(c, colNameWithWhere.length()))){
                    return true;
                }
                ShardingStrategyContext context = new ShardingStrategyContext();
                context.setConditionContext(conditionContext);
                context.setCondition(whereCondition);
                context.setShardingTableStrategy(shardingDeriveInfo.getShardingTableStrategy());
                context.setOrgTableName(tableInfo.getTableName());
                context.setColName(colName);
                shardingTableProcessor.processWhere(context);
            }else if(condition instanceof ColValueCondition && !(condition instanceof UpdateColValueCondition)){
                //处理insert
                ColValueCondition colValueCondition = (ColValueCondition) condition;
                SqlColInfo sqlColInfo = colValueCondition.getSqlColInfo();
                TableInfo tableInfo = sqlColInfo.getTableInfo();
                DeriveInfo deriveColName = tableInfo.getDeriveColName(ShardingStrategyMark.SHARDING_TABLE_STRATEGY);
                if(deriveColName == null){
                    return true;
                }
                ShardingDeriveInfo shardingDeriveInfo = (ShardingDeriveInfo) deriveColName;
                String colName = shardingDeriveInfo.getColName();
                if(!colName.equalsIgnoreCase(colValueCondition.getColName())){
                    return true;
                }
                ShardingStrategyContext context = new ShardingStrategyContext();
                context.setConditionContext(conditionContext);
                context.setCondition(colValueCondition);
                context.setShardingTableStrategy(shardingDeriveInfo.getShardingTableStrategy());
                context.setOrgTableName(tableInfo.getTableName());
                context.setColName(colName);
                shardingTableProcessor.processInsert(context);

            } else if(condition instanceof InsertAllColValueCondition){
                //处理insert all
                InsertAllColValueCondition insertAllColValueCondition = (InsertAllColValueCondition) condition;
                Object model = insertAllColValueCondition.getModel();
                TableInfo tableInfo = insertAllColValueCondition.getTableInfo();
                ModelInfo modelInfo = insertAllColValueCondition.getModelInfo();
                DeriveInfo deriveColName = tableInfo.getDeriveColName(ShardingStrategyMark.SHARDING_TABLE_STRATEGY);
                if(deriveColName == null){
                    return true;
                }
                String colName = deriveColName.getColName();
                ModelFieldInfoPosition oneCol = modelInfo.getOneCol(colName);
                Object getterValue = oneCol.getModelFieldInfo().getGetter().getter(model);
                if(getterValue == null){
                    return true;
                }
                ShardingDeriveInfo shardingDeriveInfo = (ShardingDeriveInfo) deriveColName;
                ShardingStrategyContext context = new ShardingStrategyContext();
                context.setConditionContext(conditionContext);
                context.setCondition(insertAllColValueCondition);
                context.setShardingTableStrategy(shardingDeriveInfo.getShardingTableStrategy());
                context.setOrgTableName(tableInfo.getTableName());
                context.setColName(colName);
                shardingTableProcessor.processInsertALL(context, condition);
                return false;
            }
            return true;
        }

    }
}
