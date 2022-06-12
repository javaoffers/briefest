package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.CategoryTag;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:25
 */
@Data
public  class WhereOnCondition<V> implements Condition {

    private String colName;

    private V value;

    private ConditionTag tag;

    private Map<String,Object> params = new HashMap<>();

    public WhereOnCondition() {}

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
        long idx = getNextLong();
        params.put(idx+"", value);
        return colName +" "+ tag.getTag() + " "+"#{"+idx+"}";
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    public WhereOnCondition(GetterFun colName, V value, ConditionTag tag) {
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        this.colName = TableHelper.getColName(colName).split(" ")[0];
        this.value = value;
        this.tag = tag;
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
            cls.append(TableHelper.getColName(colName).split(" ")[0]);
        }
        this.colName = cls.toString();
        this.value = value;
        this.tag = tag;
    }

    public WhereOnCondition(String[] colNames, V value, ConditionTag tag) {
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        StringBuilder cls = new StringBuilder();
        cls.append(colNames);
        this.colName = cls.toString();
        this.value = value;
        this.tag = tag;
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
