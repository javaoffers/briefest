package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 在left join t2 中描述 t2的属性的取值关系。例如
 *               select xx from t1 left join t2 on t2.is_del = 1;
 * @Auther: create by cmj on 2022/5/8 21:20
 */
public class OnValueFunCondition extends WhereOnCondition implements OnCondition{

    private String colName2; //table2 字段
    private Object value;
    private Map<String, Object> param = new HashMap<String,Object>();
    private ConditionTag tag;
    @Override
    public ConditionTag getConditionTag() {
        return tag;
    }

    @Override
    public String getSql() {
        long idx = getNextLong();
        param.put(idx+"",value);
        return " " + colName2 +" "+tag.getTag()+" "+ "#{"+idx+"} ";
    }

    /**
     * 注意：该方法一定要在getSql之后调用
     * @return
     */
    @Override
    public Map<String, Object> getParams() {
        return param;
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
