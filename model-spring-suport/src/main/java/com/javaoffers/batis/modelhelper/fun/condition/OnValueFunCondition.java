package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.CategoryTag;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.springframework.util.Assert;

/**
 * @Description: 在left join t2 中描述 t2的属性的取值关系。例如
 *               select xx from t1 left join t2 on t2.is_del = 1;
 * @Auther: create by cmj on 2022/5/8 21:20
 */
public class OnValueFunCondition implements Condition {

    private String colName2; //table2 字段
    private Object value;

    private ConditionTag tag;
    @Override
    public ConditionTag getConditionTag() {
        return tag;
    }

    public OnValueFunCondition(String colName2, Object value, ConditionTag tag) {
        this.colName2 = colName2;
        this.value = value;
        this.tag = tag;
    }

    public OnValueFunCondition(GetterFun colName, Object value, ConditionTag tag) {
        this.colName2 = TableHelper.getColName(colName).split(" ")[0];
        this.value = value;
        this.tag = tag;
    }

    public String getColName2() {
        return colName2;
    }

    public Object getValue() {
        return value;
    }

    public ConditionTag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "OnValueFunCondition{" +
                "colName2='" + colName2 + '\'' +
                ", value=" + value +
                ", tag=" + tag +
                '}';
    }
}
