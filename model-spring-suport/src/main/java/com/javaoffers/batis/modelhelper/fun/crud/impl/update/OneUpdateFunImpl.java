package com.javaoffers.batis.modelhelper.fun.crud.impl.update;

import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateColValueCondition;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;
import com.javaoffers.batis.modelhelper.fun.crud.update.OneUpdateFun;

public class OneUpdateFunImpl<M, C extends GetterFun<M, Object>, V> implements OneUpdateFun<M,C,V> {

    /**
     * 是否忽略null值，如果是null则不进行更新. true: update null.
     */
    private boolean isUpdateNull = true;

    private Class<M> mClass;

    private LinkedConditions<Condition> conditions = new LinkedConditions<>();

    @Override
    public WhereModifyFun<M, V> where() {
        return null;
    }

    @Override
    public OneUpdateFun<M, C, V> col(C col, V value) {
        if(isUpdateNull(value)){
            this.conditions.add(new UpdateColValueCondition(col,value));
        }
        return this;
    }

    @Override
    public OneUpdateFun<M, C, V> col(boolean condition, C col, V value) {
        if(condition){
            col(col,value);
        }
        return this;
    }

    public OneUpdateFunImpl(LinkedConditions<Condition> conditions,Class<M> mClass, boolean isUpdateNull) {
        this.mClass = mClass;
        this.conditions = conditions;
        this.isUpdateNull = isUpdateNull;
    }

    /**
     * true : update null
     * @param value
     * @return
     */
    private boolean isUpdateNull(V value){
        if(!isUpdateNull){
            return value != null;
        }
        return true;
    }
}
