package com.javaoffers.brief.modelhelper.fun.crud.impl.update;

import com.javaoffers.brief.modelhelper.core.LinkedConditions;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateAllColValueCondition;
import com.javaoffers.brief.modelhelper.fun.condition.update.UpdateSetCondition;
import com.javaoffers.brief.modelhelper.fun.crud.update.OneUpdateFun;
import com.javaoffers.brief.modelhelper.fun.crud.update.PrepareWhereModifyFun;
import com.javaoffers.brief.modelhelper.fun.crud.update.SmartUpdateFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import javax.sql.DataSource;

/**
 * @author create by cmj on 2022-06-29
 */
public class SmartUpdateFunImpl<M, C extends GetterFun<M, Object>, V> implements SmartUpdateFun<M,C,V> {

    private Class<M> mClass;

    private String tableName;
    /**
     * Store the query field
     */
    private LinkedConditions<Condition> conditions = new LinkedConditions<>();

    /**
     * Whether to ignore null values, if it is null then do not update. true: update null.
     */
    private boolean isUpdateNull = true;

    private PrepareWhereModifyFunImpl<M,C,V> prepareWhereModifyFun ;

    private OneUpdateFunImpl<M,C,V> oneUpdateFun;

    public SmartUpdateFunImpl(Class<M> mClass, boolean isUpdateNull, DataSource dataSource) {
        this.mClass = mClass;
        this.isUpdateNull = isUpdateNull;
        this.tableName = TableHelper.getTableName(mClass);
        this.conditions.add(new HeadCondition(dataSource,mClass));
        this.conditions.add(new UpdateSetCondition(mClass));
        this.prepareWhereModifyFun = new PrepareWhereModifyFunImpl<M,C,V>(conditions, mClass);
        this.oneUpdateFun = new OneUpdateFunImpl<M,C,V>(conditions,mClass,isUpdateNull);
    }

    SmartUpdateFunImpl(Class<M> mClass,LinkedConditions<Condition> conditions, boolean isUpdateNull) {
        this.mClass = mClass;
        this.isUpdateNull = isUpdateNull;
        this.conditions = conditions;
        this.tableName = TableHelper.getTableName(mClass);
        this.conditions.add(((HeadCondition)this.conditions.peekFirst()).clone());//为了重新设置原子递增.
        this.conditions.add(new UpdateSetCondition(mClass));
        this.prepareWhereModifyFun = new PrepareWhereModifyFunImpl<M,C,V>(conditions, mClass);
        this.oneUpdateFun = new OneUpdateFunImpl<M,C,V>(conditions,mClass,isUpdateNull);
    }

    @Override
    public OneUpdateFun<M, C, V> col(C col, V value) {
        this.oneUpdateFun.col(col,value);
        return this.oneUpdateFun;
    }

    @Override
    public OneUpdateFun<M, C, V> col(boolean condition, C col, V value) {
        this.oneUpdateFun.col(condition,col,value);
        return this.oneUpdateFun;
    }

    @Override
    public PrepareWhereModifyFun<M, C, V> colAll(M model) {
        this.conditions.add(new UpdateAllColValueCondition(isUpdateNull, mClass, model));
        return  this.prepareWhereModifyFun;
    }

    @Override
    public PrepareWhereModifyFun<M, C, V> colAll(boolean condition, M model) {
        if(condition){
            colAll(model);
        }
        return this.prepareWhereModifyFun;
    }
}
