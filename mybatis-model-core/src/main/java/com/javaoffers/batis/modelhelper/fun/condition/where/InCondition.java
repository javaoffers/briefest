package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.CategoryTag;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.where.WhereOnCondition;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:25
 */
public  class InCondition<V> extends WhereOnCondition implements Condition {

    private String colName;
    private List<V> value;

    private ConditionTag tag;
    private Map<String,Object> param = new HashMap<>();

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

    public List<V> getColValue() {
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
        StringBuilder sql = new StringBuilder(colName);
        sql.append(tag.getTag());
        sql.append(" (");
        for(int i=0; value!=null && i<value.size(); i++){
            long idx = getNextLong();
            getParams().put(idx+"", value.get(i));
            sql.append("#{");
            sql.append(idx);
            sql.append("}");
            if(i+1 != value.size()){
                sql.append(",");
            }
        }
        sql.append(") ");
        return sql.toString();
    }

    @Override
    public Map<String, Object> getParams() {
        return param;
    }

    public InCondition(GetterFun colName, Object[] value, ConditionTag tag) {
        super(colName,value,tag);
        Assert.isTrue(tag.getCategoryTag() == CategoryTag.WHERE_ON);
        this.colName = TableHelper.getColNameNotAs(colName);
        this.value = new ArrayList<>();
        for(Object v : value){
            if(v instanceof Collection){
                this.value.addAll(((Collection) v));
            }else if(v.getClass().isArray()){
                for(Object vv : (Object[])v){
                    this.value.add((V) vv);
                }
            }else{
                this.value.add((V) v);
            }
        }
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "WhereOnCondition{" +
                "colName='" + colName + '\'' +
                ", value=" + value.toString() +
                ", tag=" + tag +
                '}';
    }
}
