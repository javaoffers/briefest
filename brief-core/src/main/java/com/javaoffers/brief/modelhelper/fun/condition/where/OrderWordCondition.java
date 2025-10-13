package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.IgnoreAndOrWordCondition;

import java.util.List;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/19 02:11
 */
public class OrderWordCondition extends WhereOnCondition<String> implements IgnoreAndOrWordCondition {

    private ConditionTag tag;

    private List<String> cs;

    private boolean ascOrDesc = true;// true asc, false desc

    private String sql;

    private List<GetterFun> getterFuns; // order fun

    public OrderWordCondition(ConditionTag tag, List<GetterFun> getterFuns, List<String> cs, boolean ascOrDesc) {
        this.tag = tag;
        this.getterFuns = getterFuns;
        this.cs = cs;
        this.ascOrDesc = ascOrDesc;
        this.cleanAndOrTag();
    }

    @Override
    public String getSql() {
        if(sql == null){
            String order = " asc ";
            if(!ascOrDesc){
                order = " desc ";
            }
            this.sql = tag.getTag() + String.join(order + "," , cs) + order;
        }
        return sql;
    }

    @Override
    public ConditionTag getTag() {
        return tag;
    }

    public void asChild(){
        this.tag = ConditionTag.COMMA;
    }

}
