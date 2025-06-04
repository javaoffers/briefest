package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.CategoryTag;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.BetweenCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereOnCondition;
import com.javaoffers.brief.modelhelper.utils.Lists;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/6/3
 */
public class ShardingParams<T> {
    private Condition condition;
    private String tableName;
    private String columnName;
    private List<T> valueList = Lists.newArrayList();


    public ShardingParams(Condition condition, String tableName,
                          String columnName) {
        this.condition = condition;
        this.tableName = tableName;
        this.columnName = columnName;
        if(this.condition instanceof WhereOnCondition){
            Object value = ((WhereOnCondition) this.condition).getValue();
            if(value.getClass().isArray()){
                int length = Array.getLength(value);
                for (int i = 0; i < length; i++) {
                    this.valueList.add((T) Array.get(value, i));
                }
            }else if(value instanceof Collection){
                this.valueList.addAll((Collection<? extends T>) value);
            }else{
                this.valueList.add((T) value);
            }

            if(this.condition instanceof BetweenCondition){
                BetweenCondition<T> betweenCondition = (BetweenCondition) condition;
                this.valueList.add(betweenCondition.getEnd());
            }
        }else if (this.condition instanceof ColValueCondition){
            ColValueCondition colValueCondition = (ColValueCondition) condition;
            this.valueList.add((T) colValueCondition.getValue());
        }

    }

    public String getTableName() {
        return tableName;
    }

    public T getValueOne() {
        return valueList.get(0);
    }

    public List<T> getValueList() {
        return valueList;
    }

    public void setValueList(List<T> valueList) {
        this.valueList = valueList;
    }

    public ConditionTag getConditionTag() {
        return this.condition.getConditionTag();
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toString() {
        return "ShardingParams{" +
                "condition=" + condition +
                ", tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", valueList=" + valueList +
                '}';
    }
}
