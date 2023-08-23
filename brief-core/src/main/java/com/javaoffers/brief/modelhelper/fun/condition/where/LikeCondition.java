package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;

/**
 * @author mingJie
 */
public class LikeCondition extends WhereOnCondition {
    private AggTag aggTag;
    @Override
    public String getSql() {
        String colNameTag = getNextTag();
        Object value = String.valueOf(getValue());
        ConditionTag tag = getTag();
        switch (tag){
            case LIKE_LEFT:
                value = "%"+value;
                break;
            case LIKE_RIGHT:
                value = value + "%";
                break;
            case LIKE:
                value = "%" + value + "%";
                break;
        }
        getParams().put(colNameTag+"", value);
        if(aggTag == null){
            return getColName() +" "+ tag.getTag() + " "+"#{"+colNameTag+"}";
        }
        return aggTag.name() + "("+getColName() +") "+ tag.getTag() + " "+"#{"+colNameTag+"}";
    }

    public LikeCondition(AggTag aggTag, GetterFun colName, Object value, ConditionTag tag) {
        super(colName, value, tag);
        this.aggTag = aggTag;
    }

    public LikeCondition(GetterFun colName, Object value, ConditionTag tag) {
        super(colName, value, tag);
    }


}
