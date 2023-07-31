package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.CategoryTag;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.Assert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:25
 */
public  class WhereOnCondition<V> implements WhereCondition {

    private Set<String> colNames = new HashSet<>();

    private String colName;

    private V value;

    private ConditionTag tag;

    private Map<String,Object> params = new HashMap<>();

    private HeadCondition headCondition;

    //Logical relationship with the previous condition: and , or , ' '
    private String andOrTag = " and ";

    public WhereOnCondition() {}

    public Set<String> getColNames() {
        return colNames;
    }

    public void setColNames(Set<String> colNames) {
        this.colNames = colNames;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public ConditionTag getTag() {
        return tag;
    }

    public void setTag(ConditionTag tag) {
        this.tag = tag;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public HeadCondition getHeadCondition() {
        return headCondition;
    }

    /**
     * 获取 字段名称
     * @return
     */
    public String getColName() {
        return this.colName;
    }

    /**
     * 获取 字段值
     * @return
     */

    public V getColValue() {
        return value;
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
        String colNameTag = getNextTag();
        params.put(colNameTag+"", value);
        return colName +" "+ tag.getTag() + " "+"#{"+colNameTag+"}";
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    public WhereOnCondition(GetterFun colName, V value, ConditionTag tag) {
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        this.colName = TableHelper.getColNameNotAs(colName);
        this.value = value;
        this.tag = tag;
        colNames.add(this.colName);
    }

    public WhereOnCondition(GetterFun[] colNames, V value, ConditionTag tag) {
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        StringBuilder cls = new StringBuilder();
        int i =0;
        for(GetterFun colName : colNames){
            if(i != 0){
                cls.append(", ");
            }
            i = i+1;
            String colNameStr = TableHelper.getColNameNotAs(colName);
            this.colNames.add(colNameStr);
            cls.append(colNameStr);
        }
        this.colName = cls.toString();
        this.value = value;
        this.tag = tag;
    }

    public WhereOnCondition(String[] colNames, V value, ConditionTag tag) {
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        StringBuilder cls = new StringBuilder();
        for(String colName : colNames){
            if(cls.length() != 0){
                cls.append(", ");
            }
            cls.append(colName);
            this.colNames.add(colName);
        }
        this.colName = cls.toString();
        this.value = value;
        this.tag = tag;
    }

    public void setHeadCondition(HeadCondition headCondition){
        this.headCondition = headCondition;
    }

    public void setAndOrTag(String andOrTag){
        this.andOrTag = andOrTag;
    }

    public String getAndOrTag(){
        return this.andOrTag;
    }

    public long getNextLong(){
        Assert.isTrue(this.headCondition != null , "head condition is null ");
        return this.headCondition.getNextLong();
    }

    public String getNextTag() {
        return getNextLong() + "";
    }

    public void cleanAndOrTag(){
        this.andOrTag = "";
    }
    @Override
    public String toString() {
        return "WhereOnCondition{" +
                "colName='" + colName + '\'' +
                ", value=" + value +
                ", tag=" + tag +
                '}';
    }
}
