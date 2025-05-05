package com.javaoffers.brief.modelhelper.sharding.context;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcessor;
import com.javaoffers.brief.modelhelper.context.ConditionInterceptor;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionContext;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereCondition;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingDeriveInfo;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingProcessor;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingStrategy;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingStrategyMark;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

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
        conditionInterceptor.add(new ConditionInterceptorImpl());
    }

    static class ConditionInterceptorImpl implements ConditionInterceptor {

        @Override
        public void process(ConditionContext conditionContext, Condition condition) {

            List<? extends Condition> conditions = conditionContext.getConditions();
            //处理查询派生condition
            if (condition instanceof WhereCondition ) {
                WhereCondition whereCondition = (WhereCondition) condition;
                HeadCondition headCondition = (HeadCondition)conditions.get(0);
                Class modelClass = headCondition.getModelClass();
                TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
                DeriveInfo deriveColName = tableInfo.getDeriveColName(ShardingStrategyMark.SHARDING_TABLE_STRATEGY);
                if(deriveColName == null){
                    return;
                }
                ShardingDeriveInfo shardingDeriveInfo = (ShardingDeriveInfo) deriveColName;
                String colName = shardingDeriveInfo.getColName();
                if(!colName.equalsIgnoreCase(whereCondition.getColName())){
                    return;
                }
                ConditionTag conditionTag = condition.getConditionTag();
                switch (conditionTag) {
                    case EQ:
                        ShardingProcessor shardingProcessor = shardingDeriveInfo.getShardingProcessor();
                        shardingProcessor.processEq(conditionContext, condition);
                        break;
                    default:
                        break;
                }


            }
        }
    }
}
