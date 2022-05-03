package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.CategoryTag;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.springframework.util.Assert;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:25
 */
public  class WhereOnCondition<V> implements Condition {

    private String colName;
    private V value;

    private ConditionTag tag;

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

    public WhereOnCondition(GetterFun colName, V value, ConditionTag tag) {
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        this.colName = TableHelper.getColName(colName);
        this.value = value;
        this.tag = tag;
    }
}
