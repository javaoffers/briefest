package com.javaoffers.batis.modelhelper.fun.condition;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;

import java.util.List;

/**
 * @Description: 左右括号
 * @Auther: create by cmj on 2022/6/19 02:11
 */
public class OrderCondition extends WhereOnCondition<String> implements IgnoreAndOrCondition {

    private ConditionTag tag;

    private List<String> cs;

    private boolean ascOrDesc = true;// true asc, false desc

    public OrderCondition(ConditionTag tag, List<String> cs, boolean ascOrDesc) {
        this.tag = tag;
        this.cs = cs;
        this.ascOrDesc = ascOrDesc;
    }

    @Override
    public String getSql() {
        String order = " asc ";
        if(!ascOrDesc){
            order = " desc ";
        }

        return tag + String.join("," , cs) + order;
    }

    @Override
    public ConditionTag getTag() {
        return tag;
    }
}
