package com.javaoffers.batis.modelhelper.fun.condition.update;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.batis.modelhelper.utils.TableHelper;

public class UpdateColValueCondition extends ColValueCondition {

    public UpdateColValueCondition(GetterFun colNameGetterFun, Object value) {
        super(colNameGetterFun, value);
    }

    @Override
    public String getSql() {
        return getColName() + " = #{" + getColName() +"} ";
    }

}
