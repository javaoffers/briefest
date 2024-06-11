package com.javaoffers.brief.modelhelper.fun.condition.update;

import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.ColValueCondition;

public class UpdateColValueCondition extends ColValueCondition {

    public UpdateColValueCondition(GetterFun colNameGetterFun, Object value) {
        super(colNameGetterFun, value);
    }

    @Override
    public String getSql() {
        return getExpressionColName() + " = #{" + this.getColName() +"} ";
    }

}
