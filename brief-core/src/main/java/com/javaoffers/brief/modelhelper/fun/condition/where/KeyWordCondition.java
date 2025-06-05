package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.IgnoreAndOrWordCondition;

/**
 * @Description: 支持关键字，比如group by, having, order by .. 另外还去掉了and / or 标签
 * @Auther: create by cmj on 2022/6/5 20:02
 */
public class KeyWordCondition extends WhereOnCondition implements IgnoreAndOrWordCondition {

    GetterFun[] getterFuns;
    private ConditionTag tag;
    private String sql;

    public KeyWordCondition(GetterFun[] colName, ConditionTag tag) {
        super(colName, null, ConditionTag.VIRTUAL);
        this.getterFuns = colName;
        this.tag = tag;
        cleanAndOrTag();
    }

    public KeyWordCondition(String[] colName, ConditionTag tag) {
        super(colName,null,ConditionTag.VIRTUAL);
        this.tag = tag;
        cleanAndOrTag();
    }

    public KeyWordCondition(String[] colName, Object o, ConditionTag tag) {
        super(colName,o,ConditionTag.VIRTUAL);
        this.tag = tag;
        cleanAndOrTag();
    }
    public KeyWordCondition(GetterFun[] colName, Object o, ConditionTag tag) {
        super(colName,o,ConditionTag.VIRTUAL);
        this.tag = tag;
        cleanAndOrTag();
    }

    @Override
    public ConditionTag getTag() {
        return this.tag;
    }

    @Override
    public String getSql() {
        if(sql == null){
            sql = tag.getTag() + super.getColName()+" ";
        }
       return sql;
    }
}
