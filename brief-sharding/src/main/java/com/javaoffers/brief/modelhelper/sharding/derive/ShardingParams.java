package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.CategoryTag;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.BetweenCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereOnCondition;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/6/3
 */
public class ShardingParams<T> {
    private Condition condition;
    private String tableName;
    private String columnName;
    private T valueOne;
    //between
    private T valueTwo;

    public ShardingParams(Condition condition, String tableName,
                          String columnName) {
        this.condition = condition;
        this.tableName = tableName;
        this.columnName = columnName;
        if(this.condition instanceof WhereOnCondition){
            this.valueOne = ((WhereOnCondition<T>) this.condition).getValue();
            if(this.condition instanceof BetweenCondition){
                BetweenCondition<T> betweenCondition = (BetweenCondition) condition;
                this.valueTwo = betweenCondition.getEnd();
            }
        }else if (this.condition instanceof ColValueCondition){
            ColValueCondition colValueCondition = (ColValueCondition) condition;
            this.valueOne = (T) colValueCondition.getValue();
        }

    }

    public String getTableName() {
        return tableName;
    }

    public T getValueOne() {
        return valueOne;
    }

    public T getValueTwo() {
        return valueTwo;
    }

    public ConditionTag getConditionTag() {
        return this.condition.getConditionTag();
    }

    public String getColumnName() {
        return columnName;
    }
}
