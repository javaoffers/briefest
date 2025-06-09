package com.javaoffers.brief.modelhelper.fun;

import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.OrderWordCondition;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.ModelInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * head condition
 * @author mingJie
 */
public class HeadCondition implements Condition {

    private DataSource dataSource;

    private AtomicLong nextTag = new AtomicLong(0);

    private Class modelClass;

    private LimitWordCondition limitWordCondition;

    private List<OrderWordCondition> orderWordConditionList;

    private boolean shardingTable = false;

    public HeadCondition(DataSource dataSource, Class modelClass) {
        this.dataSource = dataSource;
        this.modelClass = modelClass;
    }

    @Override
    public ConditionTag getConditionTag() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSql() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getParams() {
        throw new UnsupportedOperationException();
    }

    public String getNextTag(){
        return nextTag.getAndIncrement() + "";
    }

    public long getNextLong(){
        return nextTag.getAndIncrement();
    }

    public HeadCondition clone(){
        return new HeadCondition(this.dataSource, this.modelClass);
    }

    public DataSource getDataSource(){
       return this.dataSource;
    }

    public Class getModelClass() {
        return modelClass;
    }

    public LimitWordCondition getLimitWordCondition() {
        return limitWordCondition;
    }

    public void setLimitWordCondition(LimitWordCondition limitWordCondition) {
        this.limitWordCondition = limitWordCondition;
    }
    public void addOrderWordCondition(OrderWordCondition orderWordCondition) {
        if(this.orderWordConditionList != null){
           this.orderWordConditionList = new ArrayList<OrderWordCondition>();
        }
        this.orderWordConditionList.add(orderWordCondition);
    }

    public boolean isSharding() {
        return shardingTable;
    }

    public void setSharding(boolean openSharding) {
        this.shardingTable = openSharding;
    }
}
