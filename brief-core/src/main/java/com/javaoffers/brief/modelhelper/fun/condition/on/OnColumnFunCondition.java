package com.javaoffers.brief.modelhelper.fun.condition.on;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.where.WhereOnCondition;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: 在left join 场景下：描述字段之间的关系，例如：
 *               select xx from table1 left join table2 on table1.f = table2.f ...
 * @Auther: create by cmj on 2022/5/4 18:23
 */
public class OnColumnFunCondition extends WhereOnCondition implements OnCondition {

    private String colName1; //table1 字段

    private String colName2; //table2 字段

    private ConditionTag tag;
    @Override
    public ConditionTag getConditionTag() {
        return tag;
    }

    @Override
    public String getSql() {
        return " "+ colName1 +" "+ tag.getTag() + " "+colName2+" ";
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }

    public OnColumnFunCondition(String colName1, String colName2, ConditionTag tag) {
        super();
        this.colName1 = colName1.split(" ")[0];
        this.colName2 = colName2.split(" ")[0];
        this.tag = tag;
    }


    public OnColumnFunCondition(GetterFun colName1, GetterFun colName2, ConditionTag tag) {
        super();
        this.colName1 = TableHelper.getColNameNotAs(colName1);
        this.colName2 = TableHelper.getColNameNotAs(colName2);
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "OnColumnFunCondition{" +
                "colName1='" + colName1 + '\'' +
                ", colName2='" + colName2 + '\'' +
                ", tag=" + tag +
                '}';
    }
}
