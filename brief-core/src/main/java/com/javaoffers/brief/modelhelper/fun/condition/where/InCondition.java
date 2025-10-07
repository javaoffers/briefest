package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.CategoryTag;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:25
 */
public  class InCondition<V> extends WhereOnCondition {

    private String colName;
    private List<V> value;

    private ConditionTag tag;
    private Map<String,Object> param = new HashMap<>();
    private String sql;

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
        if(sql == null){
            StringBuilder sqlAppender = new StringBuilder(colName);
            sqlAppender.append(tag.getTag());
            sqlAppender.append(" (");
            int i=0;
            if(value!=null && i<value.size()){
                for(; i<value.size(); i++){
                    long idx = getNextLong();
                    getParams().put(idx+"", value.get(i));
                    sqlAppender.append("#{");
                    sqlAppender.append(idx);
                    sqlAppender.append("}");
                    if(i+1 != value.size()){
                        sqlAppender.append(",");
                    }
                }
            }else{
                // in (null) 永远是false(正确的语法应该是is (not) null).
                // 当集合为空时用in (null) 来表示where条件为false. 避免 in () 这种语法错误.
                sqlAppender.append(" null ");
            }

            sqlAppender.append(") ");
            sql = sqlAppender.toString();
        }
        return sql;
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

    @Override
    public List<V> getValue() {
        return value;
    }
}
