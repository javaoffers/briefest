package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.CategoryTag;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Enter as a string as field name
 * @Auther: create by cmj on 2022/5/2 02:25
 */

public  class HavingGroupCondition<V> extends WhereOnCondition {

    private String colName;

    private ConditionTag tag;

    private V value;

    private AggTag aggTag;

    private Map<String,Object> params = new HashMap<>();

    public HavingGroupCondition() {}

    /**
     * 获取 字段名称
     * @return
     */
    public String getColName() {
        return this.colName;
    }

    /**
     * 返回条件
     * @return
     */
    public  ConditionTag getConditionTag(){
        return tag;
    }

    @Override
    public String getSql() {
        long idx = getNextLong();
        params.put(idx+"", value);
        if (aggTag != null) {
            return aggTag.name() +"("+ colName +") "+ tag.getTag() + " "+"#{"+idx+"}";
        }
        return  colName +" "+ tag.getTag() + " "+"#{"+idx+"}";
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    public HavingGroupCondition(AggTag aggTag, GetterFun colName, V value, ConditionTag tag) {
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        this.colName = TableHelper.getColNameNotAs(colName);
        this.tag = tag;
        this.aggTag = aggTag;
        this.value = value;
    }

    public HavingGroupCondition(AggTag aggTag, GetterFun[] colNames, V value, ConditionTag tag) {
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        StringBuilder cls = new StringBuilder();
        int i =0;
        for(GetterFun colName : colNames){
            if(i != 0){
                cls.append(",");
            }
            i = i+1;
            cls.append(TableHelper.getColNameNotAs(colName));
        }
        this.colName = cls.toString();
        this.tag = tag;
        this.aggTag = aggTag;
        this.value = value;
    }

}
